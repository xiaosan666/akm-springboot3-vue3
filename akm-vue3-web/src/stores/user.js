import { defineStore } from 'pinia'
import router from '@/router'
import api from '@/providers/api'
import localStorage from '@/providers/localStorage'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: '',
    csrfToken: '',
    userInfo: {
      tenantId: '',
      username: '',
      realname: '',
      avatar: '',
      roleList: [],
    },
    userAvatarUrl: '',
  }),
  getters: {
    isLoggedIn: state => !!state.token,
  },
  actions: {
    setToken(token) {
      this.token = token
      window.TOKEN = token
      localStorage.set(localStorage.keys.loginToken, token, import.meta.env.VITE_BASE_CLIENT_TYPE)
    },
    setUserInfo(payload) {
      this.userInfo = {
        tenantId: payload.tenantId,
        realname: payload.realname,
        username: payload.username,
        avatar: payload.avatar,
        roleList: payload.roleList,
      }
    },
    setUserAvatar(url) {
      this.userAvatarUrl = url
    },
    setCsrfToken(token) {
      this.csrfToken = token
    },
    async loginSuccess(token) {
      this.setToken(token)
      router.replace('/user/center')
    },
    async getLoginUserInfo() {
      const res = await api.getLoginUserInfo()
      this.setUserInfo(res)
      // 可选：设置头像
      // const url = await api.getFileUrl(res.avatar)
      // this.setUserAvatar(url)
      return res
    },
    clearUserInfo() {
      this.token = ''
      this.csrfToken = ''
      this.userInfo = {
        tenantId: '',
        username: '',
        realname: '',
        avatar: '',
        roleList: [],
      }
      this.userAvatarUrl = ''
    },
    async logout() {
      try {
        await api.logout()
      } finally {
        this.clearUserInfo()
        localStorage.set(localStorage.keys.loginToken, '')
        setTimeout(() => {
          router.replace(router.LOGIN_PATH)
        }, 200)
      }
    },
  },
  persist: true, // 开启持久化
})
