/**
 * http
 */
import baseRequest from '@/providers/network/baseRequest'
import swaggerRequest from '@/providers/network/swaggerRequest'
import sessionStorage from '@/providers/sessionStorage'

const get = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'GET',
      url,
      params: data,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      ...config,
    },
    requestInstanceName
  )
}

const del = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'DELETE',
      url,
      data: data,
      headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      ...config,
    },
    requestInstanceName
  )
}

const post = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'POST',
      url,
      data,
      headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      ...config,
    },
    requestInstanceName
  )
}

const postData = (url, data, config, requestInstanceName) => {
  if (data && typeof data === 'object') {
    let arr = []
    Object.keys(data).forEach(key => {
      arr.push(key + '=' + data[key])
    })
    // eslint-disable-next-line no-param-reassign
    data = arr.join('&')
  }
  return doRequest(
    {
      method: 'POST',
      url,
      data,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      ...config,
    },
    requestInstanceName
  )
}

const postQueryString = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'POST',
      url,
      params: data,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      ...config,
    },
    requestInstanceName
  )
}

const postFormData = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'POST',
      url,
      data,
      headers: { 'Content-Type': 'multipart/form-data' },
      ...config,
    },
    requestInstanceName
  )
}

const postResponseBlob = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'POST',
      url,
      data,
      headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      responseType: 'blob',
      ...config,
    },
    requestInstanceName
  )
}

const put = (url, data, config, requestInstanceName) => {
  return doRequest(
    {
      method: 'PUT',
      url,
      data,
      headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      ...config,
    },
    requestInstanceName
  )
}

const doRequest = (config, requestInstanceName) => {
  let requestInstance = baseRequest
  if (requestInstanceName === 'swagger') {
    requestInstance = swaggerRequest
  }
  let cacheKey =
    sessionStorage.keys.requestResult +
    config.url +
    config.method +
    JSON.stringify(config.data) +
    JSON.stringify(config.params)
  // 如果需要缓存，先尝试从sessionStorage中取数据
  if (config.cacheData && config.clearCache !== true) {
    let cacheData = sessionStorage.get(cacheKey)
    if (cacheData) {
      const cacheTimeout = config.cacheTimeout ?? null
      // 当cacheTimeout为空或-1时，不判断超时
      if (cacheTimeout === null || cacheTimeout === -1) {
        return Promise.resolve(cacheData.data)
      } else {
        const now = Date.now()
        const isTimeout = now - cacheData.timestamp > cacheTimeout * 1000
        if (!isTimeout) {
          return Promise.resolve(cacheData.data)
        }
        // 超时则继续请求
      }
    }
  }
  return (
    requestInstance
      // eslint-disable-next-line no-invalid-this
      .call(this, config)
      .then(res => {
        // 如果需要缓存，保存数据到sessionStorage中
        if (config.cacheData) {
          sessionStorage.set(cacheKey, { data: res, timestamp: Date.now() })
        }
        return Promise.resolve(res)
      })
      .catch(err => Promise.reject(err))
  )
}
export default {
  get,
  del,
  post, // Content-Type：application/json
  postData, // Content-Type：application/x-www-form-urlencoded
  postQueryString, // Content-Type：application/x-www-form-urlencoded， 参数为url拼接，类似get
  postFormData, // Content-Type：multipart/form-data，一般用于文件上传
  postResponseBlob, // 类似post()请求，但响应结果为blob，一般用于文件下载
  put,
}
