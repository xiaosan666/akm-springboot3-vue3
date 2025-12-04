import { ElMessage, ElMessageBox } from 'element-plus'
import { usePermissionStore } from '@/stores/permission'
import router from '@/router'
import api from '@/providers/api'
import sessionStorage from '@/providers/sessionStorage'
import { useUserStore } from '@/stores/user'

/**
 * helper.js:与业务或框架有关的工具方法
 * utils.js:与业务无关的工具方法
 */

const successMessage = (message = '操作成功') => {
  showMessage(message, 'success')
}
const errorMessage = (message = '操作失败') => {
  showMessage(message, 'error')
}
const warningMessage = (message = '') => {
  showMessage(message, 'warning')
}
/**
 * 同样的消息2秒内只弹出一次
 */
const messageHistoryMap = {}
const showMessage = (message = '', type = 'warning') => {
  let lastTime = messageHistoryMap[message]
  if (lastTime && Date.now() - lastTime < 2000) {
    return
  }
  messageHistoryMap[message] = Date.now()
  ElMessage({
    message,
    showClose: true,
    type,
  })
}

const confirm = (
  message,
  title = '提示',
  confirmButtonText = '确定',
  cancelButtonText = '取消'
) => {
  return ElMessageBox.confirm(message, title, {
    confirmButtonText,
    cancelButtonText,
    type: 'warning',
  })
}

let alertIsExist = false // 判断alert已经存在则不再弹出
const alert = (message, title = '提示') => {
  return new Promise((resolve, reject) => {
    if (!alertIsExist) {
      alertIsExist = true
      ElMessageBox.alert(message, title, {
        confirmButtonText: '确定',
        type: 'warning',
        callback: () => {
          alertIsExist = false
          resolve()
        },
      })
    } else {
      reject(new Error())
    }
  })
}

/**
 * 页面内容区域显示loading
 */
const showLoading = () => {
  const permission = usePermissionStore()
  permission.setPageMainLoading(true)
}

const hideLoading = () => {
  const permission = usePermissionStore()
  permission.setPageMainLoading(false)
}

/**
 * 生成表格数据序号列
 * @param tableList 表格数据对象数组
 * @param pageNum 页码
 * @param pageSize 每页大小
 * @returns {*} 带_index属性的对象数组
 */
const addTableIndexColumn = (tableList, pageNum = 0, pageSize = 0) => {
  if (!tableList || tableList.length === 0) {
    return tableList
  }
  tableList.forEach((item, index) => {
    item._index = index + 1 + (pageNum - 1) * pageSize
  })
  return tableList
}

/**
 * 计算表格高度，让滚动条出现在表格内部，而不是页面外层
 */
const computedTableHeight = (
  tableRef = 'akmTable',
  warpRef = 'akmMain',
  pageHeightChange = false
) => {
  let table = document.getElementById(tableRef)
  if (!table) {
    window.DEBUG && alert('使用自动计算表格高度，请给表格添加id属性')
    return
  }
  if (pageHeightChange) {
    let tableBody = table.getElementsByClassName('el-table__body-wrapper')[0]
    table.style.maxHeight = 'none'
    tableBody.style.maxHeight = 'none'
  }
  let main = document.getElementById(warpRef)
  let scrollNumber = main.scrollHeight - main.offsetHeight
  if (scrollNumber !== 0) {
    let tableHeight = table.scrollHeight - scrollNumber
    // 若表格高度计算后小于200，则不使用自动计算的高度
    if (tableHeight < 200) {
      return
    }
    return Math.floor(tableHeight) // Math.floor修复高度为小数出现滚动条
  }
}

/**
 * 返回上一页
 * 用于三级页面返回二级页面，setClickBackButtonValue通知保持二级页面缓存
 */
const routerBackCachedView = () => {
  // store.commit('setClickBackButtonValue', true)
  router.back()
}

/**
 * 防止表单重复提交
 * 前端在请求新增或编辑接口前，需提前调用该接口获得CSRF Token
 * 在请求新增或编辑接口时，框架会把CSRF Token放到请求头(`csrf-token`)中，才能成功调用接口
 * CSRF Token生成后30分钟有效，且只能使用一次；
 * CSRF Token在创建后2秒内使用，请求无效
 */
const createCsrfToken = () => {
  api.getCsrfToken().then(token => {
    const userStore = useUserStore()
    userStore.setCsrfToken(token)
  })
}

/**
 * 路由传参一般使用$router.query或$router.params，由于$router.query会把参数暴露在url上，所以避免使用
 * 使用$router.params传参，当页面刷新时参数会丢失，所以调用该方法缓存参数，从而达到刷新页面参数不丢失的效果
 传递参数：
  this.$router.push({
    name: 'ExceptionLogView',
    params: { id: row.id },
  })
 获取参数：
  const params = this.$helper.getAndCacheRouteParams(this.$route)
 原来获取参数方式，刷新页面会丢失参数：
  const params =  this.$route.params
 */
const getAndCacheRouteParams = route => {
  const cacheKey = sessionStorage.keys.routeParams + route.path
  const params = route.params
  if (Object.keys(params).length === 0) {
    return sessionStorage.get(cacheKey) || {}
  }
  sessionStorage.set(cacheKey, params)
  return params
}

export default {
  successMessage, // 显示成功类消息
  errorMessage, // 显示错误类消息
  warningMessage, //  显示警告类消息
  confirm, // 警告弹出框，有确定和取消按钮
  alert, // 只有一个确定按钮的弹出框
  showLoading, // 整个页面显示loading
  hideLoading, // 关闭showLoading
  addTableIndexColumn, // 给data数据添加 _index 列
  computedTableHeight, // 计算表格高度，让滚动条出现在表格内部，而不是页面外层
  routerBackCachedView, // 返回上一页，上个页面将被 keep-alive
  createCsrfToken, // 创建csrf token，并把token缓存到vuex，等待请求使用
  getAndCacheRouteParams, // 缓存$router.params参数，当页面刷新时参数不会丢失
}
