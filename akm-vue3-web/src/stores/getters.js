import localStorage from '@/providers/localStorage.js'
// getters
const getters = {
  userInfo: state => state.userInfo,
  userAvatarUrl: state => state.userAvatarUrl,
  hasConfig: state => state.hasConfig,
  config: state => {
    if (window.CONFIG) {
      return window.CONFIG
    }
    let config = localStorage.get(
      localStorage.keys.reqConfig,
      import.meta.env.VITE_STORAGE_SHARE_AES_KEY
    )
    if (config && config.publicKey) {
      state.config = config
      state.hasConfig = true
      window.CONFIG = config
    }
    return config || {}
  },
  token: state => {
    if (window.TOKEN) {
      return window.TOKEN
    }
    let token =
      localStorage.get(localStorage.keys.loginToken, import.meta.env.VITE_STORAGE_SHARE_AES_KEY) ||
      ''
    state.token = token
    window.TOKEN = token
    return token
  },
  permissionCodeList: state => state.permissionCodeList,
  swaggerApiData: state => state.SwaggerApi.tree,
}
export default getters
