import api from '@/providers/api'
import dict from '@/providers/dict'
import eventBus from '@/providers/eventBus'
import helper from '@/providers/helper'
import http from '@/providers/http'
import security from '@/providers/security'
import utils from '@/providers/utils'
import validate from '@/providers/validate'
import localStorage from '@/providers/localStorage'
import sessionStorage from '@/providers/sessionStorage'
import { usePermissionStore } from '@/stores/permission'

/**
 * 初始化 Store
 */
export function initProviders(app) {
  app.config.globalProperties.$api = api
  app.config.globalProperties.$dict = dict
  app.config.globalProperties.$eventBus = eventBus
  app.config.globalProperties.$helper = helper
  app.config.globalProperties.$http = http
  app.config.globalProperties.$security = security
  app.config.globalProperties.$utils = utils
  app.config.globalProperties.$validate = validate
  app.config.globalProperties.$localStorage = localStorage
  app.config.globalProperties.$sessionStorage = sessionStorage

  /**
   * 判断有无code权限，一般用于控制按钮显示隐藏，也可以使用指令v-has代替
   * @param code 权限编码
   * @returns {boolean}
   */
  app.config.globalProperties.has = function (code) {
    if (!code) {
      return false
    }
    const permissionStore = usePermissionStore()
    return permissionStore.permissionMenu.buttonCodeList.includes(code)
  }
}
