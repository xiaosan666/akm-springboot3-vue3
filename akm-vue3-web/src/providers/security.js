import Security from 'akm-security'

/**
 * npm install --save akm-security
 */
export default {
  /**
   * 加密工具方法
   */
  aesEncrypt: Security.aesEncrypt, // AES 加密
  aesDecrypt: Security.aesDecrypt, // AES 解密
  rsaEncrypt: Security.rsaEncrypt, // RSA 公钥加密
  rsaDecrypt: Security.rsaDecrypt, // RSA 私钥解密
  base64Encode: Security.base64Encode, // 字符串base64编码
  base64Decode: Security.base64Decode, // 字符串base64解码
  md5: Security.md5, // 消息摘要算法 md5（不安全慎用，使用SHA256代替）
  sha256: Security.sha256, // 消息摘要算法 SHA256
  hmacSHA256: Security.hmacSHA256, // 消息摘要算法 HmacSHA256

  /**
   * 框架业务相关工具方法
   */
  encryptRequestData: Security.encryptRequestData, // 加密请求参数
  decryptResponseData: Security.decryptResponseData, // 解密响应结果
  createRequestSign: Security.createRequestSign, // 创建请求签名
  decryptConfig: Security.decryptConfig, // 解密请求配置
  isExcludeUri: Security.isExcludeUri, // 路径匹配，判断uri是否白名单
}
