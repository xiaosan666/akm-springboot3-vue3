import { defineStore } from 'pinia'
import api from '@/providers/api'
import utils from '@/providers/utils'
import menuDemo from './menuDemo'

export const usePermissionStore = defineStore('permission', {
  state: () => ({
    permissionMenu: {
      menuList: [],
      buttonList: [],
      buttonCodeList: [],
    },
    activeMenu: {},
    activeChildrenMenu: {},
    breadcrumbList: [],
    pageMainLoading: false,
  }),
  actions: {
    async getLoginUserMenu() {
      let list = await api.getLoginUserMenu()
      list = menuDemo.concat(list)
      const menuList = []
      const buttonList = []
      list.forEach(item => {
        if (item.type === 1 || item.type === 2) {
          menuList.push(item)
        } else if (item.type === 3) {
          buttonList.push(item)
        }
      })
      this.permissionMenu = {
        menuList,
        buttonList,
        buttonCodeList: buttonList.map(item => item.code),
      }
      return list
    },
    setActiveMenu(menu) {
      this.activeMenu = menu
    },
    setActiveChildrenMenu(menu) {
      this.activeChildrenMenu = menu
    },
    setBreadcrumbList(list) {
      this.breadcrumbList = list
    },
    setPageMainLoading(loading) {
      this.pageMainLoading = loading
    },
    setPermissionMenu(permissionData) {
      this.permissionMenu = {
        menuList: permissionData.menuList || [],
        buttonList: permissionData.buttonList || [],
        buttonCodeList: permissionData.buttonCodeList || [],
      }
    },
    clearPermissionMenu() {
      this.permissionMenu = {
        menuList: [],
        buttonList: [],
        buttonCodeList: [],
      }
      this.activeMenu = {}
      this.activeChildrenMenu = {}
      this.breadcrumbList = []
    },
  },
  persist: true, // 开启持久化
})
