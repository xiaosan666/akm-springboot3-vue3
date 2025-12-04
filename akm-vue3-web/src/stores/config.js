import { defineStore } from 'pinia'
import api from '@/providers/api'
import localStorage from '@/providers/localStorage'

export const useConfigStore = defineStore('config', {
  state: () => ({
    hasConfig: false,
    activeProfile: '', // 服务端激活的环境dev、test、prod
    publicKey: '', // 对应publicKey1
    privateKey: '', // 对应privateKey2
    enabledEncrypt: true, // 是否启用加解密
    encryptExcludeUris: [], // 加解密白名单
    enabledSign: true, // 是否启用签名
    signExcludeUris: [], // 签名白名单
    timeDiff: 0, // 客户端时间与服务器时间差（毫秒数）
    enabled4aLogin: false, // 是否启用4a用户信息登录
    akm4aServerUrl: '', // akm-4a服务地址
  }),
  getters: {
    currentConfig: state => {
      if (window.CONFIG) {
        return window.CONFIG
      }
      const savedConfig = localStorage.get(
        localStorage.keys.reqConfig,
        import.meta.env.VITE_BASE_CLIENT_TYPE
      )
      if (savedConfig && savedConfig.publicKey) {
        state.config = savedConfig
        state.hasConfig = true
        window.CONFIG = savedConfig
      }
      return savedConfig || {}
    },
  },
  actions: {
    async getAndSetSysConfig() {
      const configData = await api.getSysConfig()
      this.activeProfile = configData.activeProfile
      this.publicKey = configData.publicKey
      this.privateKey = configData.privateKey
      this.enabledEncrypt = configData.enabledEncrypt
      this.encryptExcludeUris = configData.encryptExcludeUris
      this.enabledSign = configData.enabledSign
      this.signExcludeUris = configData.signExcludeUris
      this.hasConfig = true
      window.CONFIG = configData
      localStorage.set(
        localStorage.keys.reqConfig,
        configData,
        import.meta.env.VITE_BASE_CLIENT_TYPE
      )
      return configData
    },
  },
  persist: true, // 开启持久化
})
