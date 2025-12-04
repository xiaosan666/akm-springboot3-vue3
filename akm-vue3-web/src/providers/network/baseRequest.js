import axios from 'axios'
import router from '@/router'
import helper from '@/providers/helper'
import eventBus from '@/providers/eventBus'
import baseRequestHelper from '@/providers/network/baseRequestHelper'
import { useConfigStore } from '@/stores/config'
import { useUserStore } from '@/stores/user'

// http://www.axios-js.com/zh-cn/docs/
const baseRequest = axios.create({
  baseURL: import.meta.env.VITE_BASE_API,
  timeout: 40000,
  headers: { 'Content-Type': 'application/json;charset=UTF-8' },
  responseType: 'json',
  withCredentials: false,
  // 自定义请求数据转换，解决 axios 1.x 对字符串参数额外添加引号的问题
  transformRequest: [
    function (data, headers) {
      // 只对 application/json 类型的请求进行处理
      const contentType = headers['Content-Type'] || ''
      if (!contentType.includes('application/json')) {
        return data
      }
      // 如果是字符串，直接返回，不进行 JSON.stringify
      if (typeof data === 'string') {
        return data
      }
      // 对象和数组进行 JSON 序列化
      if (data && typeof data === 'object') {
        return JSON.stringify(data)
      }
      return data
    },
  ],
})

// 请求拦截器
baseRequest.interceptors.request.use(config => {
  // vue-axios发请求，默认不存在参数时不会加上content-type， 所以做如下处理
  if (!config.data) {
    config.data = {}
  }
  // 获取配置接口（第一个接口）不加密和签名
  if (config.url !== '/auth/open/config') {
    // 计算签名
    baseRequestHelper.addSignHeaders(config)
    // 保留加密前的数据，用于日志记录和调试查看原始参数
    window.LOG && (config.oldData = config.data)
    // 加密参数
    baseRequestHelper.encryptData(config)
  }

  const userStore = useUserStore()
  // 添加token
  let token = userStore.token
  token && (config.headers['Authorization'] = token)

  // 添加CSRF token
  let csrfToken = userStore.csrfToken
  csrfToken && (config.headers['csrf-token'] = csrfToken)

  config.headers['client-type'] = import.meta.env.VITE_BASE_CLIENT_TYPE

  return config
})

// 响应拦截器
baseRequest.interceptors.response.use(
  response => {
    let result = baseRequestHelper.decryptData(response)
    if (window.LOG) {
      console.group(response.config.url)
      console.log(response.config.oldData)
      response.config.oldData = null
      console.log(result)
      console.groupEnd()
    }
    if (result && result.code === 100200) {
      return result.data
    } else if (result instanceof Blob) {
      // 文件下载
      return new Promise((resolve, reject) => {
        // 判断文件下载异常情况，正常content-type形如：application/vnd.ms-excel
        let contentType = response.headers['content-type']
        if (contentType && contentType.includes('json')) {
          const reader = new FileReader()
          reader.onload = () => {
            // 正则表达式匹配msg的值
            const match = reader.result.match(/"msg":"(.*?)"/)
            if (match && match[1]) {
              helper.errorMessage(match[1])
              reject(match[1])
            } else {
              let errResult = JSON.parse(reader.result)
              handlerException(errResult)
              reject(errResult)
            }
          }
          reader.readAsText(result)
        } else {
          resolve(result)
        }
      })
    } else {
      handlerException(result)
      return Promise.reject(result)
    }
  },
  error => {
    let response = error.response
    if (!response) {
      helper.errorMessage('网络出错或请求超时')
      return Promise.reject(error)
    }
    let result = response.data
    handlerException(result)
    return Promise.reject(result)
  }
)

const handlerException = result => {
  if (!result) {
    helper.errorMessage('服务发生异常异常')
    return
  }
  if (result.code === 100401) {
    // // 重置配置状态，跳转到首页会重新获取
    // store.commit('setHasConfig', false)
    if (router.currentRoute.value === router.LOGIN_PATH) {
      helper.errorMessage('登陆失效，请重新登陆')
      eventBus.emit(eventBus.keys.pageRefresh) // 刷新页面
    } else {
      helper.alert('登陆失效，请重新登陆').then(() => {
        router.replace(router.LOGIN_PATH).then(() => {
          eventBus.emit(eventBus.keys.pageRefresh) // 刷新页面
        })
      })
    }
  } else if (result.code === 100453 || result.code === 100412 || result.code === 100551) {
    const configStore = useConfigStore()
    configStore.getAndSetSysConfig().then(() => {
      eventBus.emit(eventBus.keys.pageRefresh) // 刷新页面
      console.warn('请求出错，重新获取配置，并刷新页面', result)
    })
  } else if (result.code === 100455 || result.code === 100456) {
    helper.errorMessage(result.msg || '请修改密码后登陆')
    eventBus.emit(eventBus.keys.updatePassword) // 跳转至修改密码页面
  } else {
    let errorMsg = result.msg || result.message
    errorMsg && helper.errorMessage(errorMsg)
  }
}
export default baseRequest
