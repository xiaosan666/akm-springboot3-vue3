import Security from '@/providers/security'
const cachePrefix = import.meta.env.VITE_STORAGE_PREFIX_KEY

const keys = {
  loginToken: 'localStorage:loginToken',
  reqConfig: 'localStorage:reqConfig',
  rememberMe: 'localStorage:rememberMe',
  smsCountdown: 'localStorage:smsCountdown',
}

const set = (key, value, aesKey = import.meta.env.VITE_STORAGE_AES_KEY) => {
  window.localStorage.setItem(cachePrefix + key, Security.aesEncrypt(JSON.stringify(value), aesKey))
}

const get = (key, aesKey = import.meta.env.VITE_STORAGE_AES_KEY) => {
  const jsonString = window.localStorage.getItem(cachePrefix + key)
  if (!jsonString) {
    return null
  }
  let str = Security.aesDecrypt(jsonString, aesKey)
  return str ? JSON.parse(str) : null
}

const remove = key => {
  window.localStorage.removeItem(cachePrefix + key)
}

const clear = () => {
  window.localStorage.clear()
}

export default {
  keys,
  set,
  get,
  remove,
  clear,
}
