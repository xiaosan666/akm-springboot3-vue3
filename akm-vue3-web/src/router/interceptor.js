/**
 * 路由拦截（页面缓存，鉴权）
 */

import router from './index'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { useUserStore, useCacheViewStore, usePermissionStore, useConfigStore } from '@/stores'
NProgress.configure({ showSpinner: false })

// 路由守卫
router.beforeEach(async (to, from, next) => {
  NProgress.start()

  // 当url存在参数log=1时，开启请求日志打印模式，一般用于接口加密后调试使用
  if (to.query.log) {
    window.LOG = to.query.log === '1'
  }

  await checkPermissions(to, from, next)
  // cacheView(to, from, next)
})

router.afterEach(() => {
  NProgress.done()
})

const checkPermissions = async (to, from, next) => {
  try {
    // 获取当前页面的URL
    let url = new URL(window.location.href)
    // url包含token，则尝试直接登录
    let token = url.searchParams.get('token')
    if (token) {
      // 删除URL中的token参数
      url.searchParams.delete('token')
      window.history.replaceState({}, document.title, url.toString())

      // 获取系统配置
      const configStore = useConfigStore()
      await configStore.getAndSetSysConfig()

      // 使用token登录
      const userStore = useUserStore()
      await userStore.loginSuccess(token)

      next('/')
      return
    }
    if (to.path === router.LOGIN_PATH) {
      // 获取系统配置
      const configStore = useConfigStore()
      await configStore.getAndSetSysConfig()
      next()
      return
    }

    const userStore = useUserStore()
    if (!userStore.token) {
      next(router.LOGIN_PATH)
      return
    }
    next()
  } catch (error) {
    console.error('路由权限检查失败:', error)
    next(router.LOGIN_PATH)
  }
}

/**
 * 判断是否需要缓存页面
 * 目标： 当从三级页面点击返回按钮到二级页面，二级页面需要保留原页面状态
 */
const cacheView = (to, from, next) => {
  const cacheViewStore = useCacheViewStore()

  // 点击返回按钮触发路由，则需要继续保留上级页面缓存
  if (cacheViewStore.clickBackButton) {
    // 重置点击返回按钮状态
    cacheViewStore.setClickBackButton(false)
    cacheViewStore.delCachedView(from.name)
  } else {
    // 其他方式触发路由，判断页面有没有进入过，若进入过则清空页面缓存，即没有进入过的页面会加入缓存
    if (cacheViewStore.cachedViews.includes(to.name)) {
      cacheViewStore.clearCachedView()
    }
  }

  // 延迟，待页面渲染完成后把页面加入到缓存
  setTimeout(() => {
    cacheViewStore.addCachedView(to.name)
  })

  // 控制是否显示"返回上级页面"按钮
  cacheViewStore.setShowBackButton(to.path !== '/' && !isMenu(to.path))
  next()
}

function isMenu(path) {
  const permissionStore = usePermissionStore()
  return permissionStore.permissionMenu.menuList.some(item => item.uri === path)
}

export default router
