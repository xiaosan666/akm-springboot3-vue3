import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import sessionStorage from '@/providers/sessionStorage'
import dict from '@/providers/dict'

// 导入所有 store
import { useUserStore } from './user'
import { useConfigStore } from './config'
import { usePermissionStore } from './permission'
import { useCacheViewStore } from './cacheView'
import { useSwaggerApiStore } from './swaggerApi'

// 创建 pinia 实例
const pinia = createPinia()

// 添加持久化插件
pinia.use(piniaPluginPersistedstate)

// 全局清除所有 store 的方法
export function clearAllStores() {
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()
  const cacheViewStore = useCacheViewStore()

  userStore.clearUserInfo()
  permissionStore.clearPermissionMenu()
  cacheViewStore.clearCachedView()
  sessionStorage.clear()
  dict.clear()
}

export { useUserStore, useConfigStore, usePermissionStore, useCacheViewStore, useSwaggerApiStore }

export default pinia
