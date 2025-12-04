import Security from '@/providers/security'
import { useConfigStore } from '@/stores/config'
/**
 * 请求帮助类（请求参数加解密，创建请求签名）
 */
export default {
  /**
   * 创建请求签名
   */
  addSignHeaders(request) {
    const config = useConfigStore()
    if (config.enabledSign === false) {
      return
    }
    let uri = request.url.split('?')[0]
    // 是否白名单
    if (Security.isExcludeUri(config.signExcludeUris, uri)) {
      return
    }
    // 文件上传，以空字符串来计算签名
    let signData = Security.createRequestSign(
      uri,
      this.isMultipart(request) ? '' : request.data,
      config.timeDiff
    )
    request.headers = {
      ...request.headers,
      ...signData,
    }
  },
  /**
   * 加密请求参数
   */
  encryptData(request) {
    const config = useConfigStore()
    if (config.enabledEncrypt === false || this.isMultipart(request)) {
      return
    }
    let uri = request.url.split('?')[0]
    // 是否白名单
    if (Security.isExcludeUri(config.encryptExcludeUris, uri)) {
      return
    }
    let encryptResult = Security.encryptRequestData(request.data, config.publicKey)
    request.data = encryptResult.data // 加密后的data
    request.headers.k = encryptResult.k
  },
  /**
   * 解密响应结果
   */
  decryptData(response) {
    const config = useConfigStore()
    let result = response.data
    // 若响应头k不为空，则认为需要解密(某些接口不需要加密，所以不会有k)
    if (response.headers.k) {
      result.data = Security.decryptResponseData(result.data, response.headers.k, config.privateKey)
    }
    return result
  },
  // 判断请求是否是文件上传
  isMultipart(request) {
    return Object.values(request.headers).some(
      item => typeof item === 'string' && item.startsWith('multipart/')
    )
  },
}
