import http from '@/providers/http'
import security from '@/providers/security'

/**
 * 获取系统配置
 */
const getSysConfig = () => {
  return http.post('/auth/open/config').then(res => {
    let config = security.decryptConfig(
      res.random, // 随机数
      res.timestamp, // 时间戳
      res.config // 加密的配置
    )
    if (config.enabledSign) {
      // 服务器时间与客户端时间差，签名时补上时间差，防止本地时间与服务器相差造成签名无效
      config.timeDiff = res.timestamp - Date.now()
    }
    return Promise.resolve(config)
  })
}

/**
 * 获取图形验证码
 */
const fetchCaptcha = () => {
  return http.post('/auth/open/captcha')
}

/**
 * 退出登录
 */
const logout = () => {
  return http.post('/auth/open/logout')
}

/**
 * 获取csrf token
 */
const getCsrfToken = () => {
  return http.post('/auth/public/getCsrfToken')
}

/**
 * 登录人获取自身信息
 */
const getLoginUserInfo = () => {
  return http.post('/auth/sys/user/public/getLoginUserInfo')
}

/**
 * 登录人获取自身菜单
 */
const getLoginUserMenu = () => {
  return http.post('/auth/sys/menu/public/findLoginUserMenu')
}

/**
 * 单文件上传
 * @param file 文件对象
 * @param pathPrefix 文件保存路径前缀
 * @return 返回文件相对路径(objectKey)
 */
const fileUpload = (file, pathPrefix = '') => {
  let formData = new FormData()
  formData.append('file', file)
  formData.append('pathPrefix', pathPrefix)
  return http.postFormData('/share/public/file/upload', formData)
}

/**
 * 文件下载，后台返回文件字节流，只适合下载小文件
 * @param objectKey
 * @return {Promise<Blob>}
 */
const fileDownload = objectKey => {
  return http.postResponseBlob('/share/public/file/getObject', objectKey)
}

const fileDownloadToZip = objectKey => {
  return http.postResponseBlob('/share/public/file/getObjectToZip', objectKey)
}

/**
 * 文件预览url（有效期30秒）
 * @param objectKey 文件相对路径
 */
const getFileUrl = objectKey => {
  if (!objectKey) {
    return Promise.resolve('')
  }
  return http.post('/share/public/file/getFileUrl', objectKey)
}

/**
 * 查询附件记录
 * @param recordType 附件业务类型
 * @param recordId 附件关联的业务id
 * @return {Promise<*>}
 */
const findAttachment = (recordType, recordId) => {
  return http.post('/share/public/biz/attachment/view/findAll', {
    recordType,
    recordId,
  })
}

/**
 * 批量新增附件
 * @param list
 * @return {Promise<*>}
 */
const batchInsertAttachment = list => {
  return http.post('/share/public/biz/attachment/op/batchInsert', list)
}

/**
 * 删除业务相关附件
 * @param recordType
 * @param recordId
 * @return {Promise<unknown>}
 */
const deleteAttachment = (recordType, recordId) => {
  return http.post('/share/public/biz/attachment/op/deleteAttachment', {
    recordType,
    recordId,
  })
}

/**
 * 获取启用的租户列表
 */
const fetchTenantList = () => {
  return http.post('/auth/sys/tenant/view/findAll', { enabled: 1 }).then(res => {
    let list = []
    if (res && res.length > 0) {
      list = res.map(item => {
        return {
          name: item.name,
          value: item.id,
        }
      })
    }
    return Promise.resolve(list)
  })
}

export default {
  getSysConfig,
  fetchCaptcha,
  getLoginUserInfo,
  getLoginUserMenu,
  logout,
  getCsrfToken,
  fileUpload,
  fileDownload,
  fileDownloadToZip,
  getFileUrl,
  findAttachment,
  batchInsertAttachment,
  deleteAttachment,
  fetchTenantList,
}
