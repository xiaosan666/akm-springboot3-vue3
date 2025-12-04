import Security from '@/providers/security'
const cachePrefix = import.meta.env.VITE_STORAGE_PREFIX_KEY

const keys = {
  requestResult: 'sessionStorage:requestResult',
  dictData: 'sessionStorage:dictData',
  routeParams: 'sessionStorage:routeParams',
}

const set = (key, value, aesKey = import.meta.env.VITE_STORAGE_AES_KEY) => {
  window.sessionStorage.setItem(
    cachePrefix + key,
    Security.aesEncrypt(JSON.stringify(value), aesKey)
  )
}

const get = (key, aesKey = import.meta.env.VITE_STORAGE_AES_KEY) => {
  const jsonString = window.sessionStorage.getItem(cachePrefix + key)
  if (!jsonString) {
    return null
  }
  let str = Security.aesDecrypt(jsonString, aesKey)
  return str ? JSON.parse(str) : null
}

const remove = key => {
  window.sessionStorage.removeItem(cachePrefix + key)
}

const clear = () => {
  window.sessionStorage.clear()
}

export default {
  keys,
  set,
  get,
  remove,
  clear,
}
