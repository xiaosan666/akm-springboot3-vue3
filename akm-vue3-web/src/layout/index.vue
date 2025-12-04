<template>
  <el-container class="akm-layout-container">
    <el-header class="akm-header">
      <the-header></the-header>
    </el-header>
    <el-main class="response">
      <el-container class="response">
        <el-aside width="220px" class="aside">
          <the-aside-menu :menus="menuTree"></the-aside-menu>
        </el-aside>
        <el-main class="response">
          <el-container class="response">
            <el-header class="page-header">
              <the-breadcrumb></the-breadcrumb>
              <div v-if="showBackButton" class="back" @click="back">
                <el-icon><Back /></el-icon>
                返回上级页面
              </div>
            </el-header>
            <el-main id="akmMain" v-loading="pageMainLoading" class="response">
              <!--              <keep-alive :include="cachedViews">-->
              <!--                <router-view v-if="isRouterAlive" class="page-main" />-->
              <!--              </keep-alive>-->
              <router-view v-slot="{ Component }">
                <keep-alive :include="cachedViews">
                  <component :is="Component" class="page-main" />
                </keep-alive>
              </router-view>
            </el-main>
          </el-container>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import { useUserStore } from '@/stores/user'
import { usePermissionStore } from '@/stores/permission'
import { useCacheViewStore } from '@/stores/cacheView'
import TheHeader from './TheHeader.vue'
import TheAsideMenu from './TheAsideMenu.vue'
import TheBreadcrumb from './TheBreadcrumb.vue'
import { Back } from '@element-plus/icons-vue'

export default {
  name: 'TheLayout',
  components: {
    TheHeader,
    TheAsideMenu,
    TheBreadcrumb,
    Back,
  },
  data() {
    return {
      isRouterAlive: true,
    }
  },
  computed: {
    cachedViews() {
      const cacheViewStore = useCacheViewStore()
      return cacheViewStore.cachedViews
    },
    showBackButton() {
      const cacheViewStore = useCacheViewStore()
      return cacheViewStore.showBackButton
    },
    pageMainLoading() {
      const permissionStore = usePermissionStore()
      return permissionStore.pageMainLoading
    },
    menuTree() {
      const permissionStore = usePermissionStore()
      return this.$utils.listToTree(permissionStore.permissionMenu.menuList)
    },
  },
  async created() {
    // 监听页面主体区域刷新事件
    this.$eventBus.on(this.$eventBus.keys.mainRefresh, () => {
      this.refresh()
    })
    const userStore = useUserStore()
    // 获取用户信息
    await userStore.getLoginUserInfo()

    // 获取用户菜单
    const permissionStore = usePermissionStore()
    await permissionStore.getLoginUserMenu()
  },
  methods: {
    // 刷新main
    refresh() {
      this.isRouterAlive = false
      this.$nextTick(() => {
        this.isRouterAlive = true
      })
    },
    back() {
      this.$helper.routerBackCachedView()
    },
  },
}
</script>

<style lang="scss" scoped>
.response {
  height: 100%;
  padding: 0;
}

.back {
  font-size: 16px;
  color: var(--el-color-primary);
  cursor: pointer;
}
</style>

<style lang="scss">
.akm-layout-container {
  height: 100%;
  .akm-header {
    padding-left: 0;
    background-color: #001529;
    color: #fff;
    height: 60px;
    line-height: 60px;
    text-align: right;
    .el-button--text {
      color: #fff;
    }
    .name-role {
      color: #fff;
      cursor: pointer;
    }
  }
  .page-header {
    height: 36px !important;
    line-height: 36px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  #akmMain {
    margin: 0 12px 12px;
  }
  .page-main {
    padding: 12px;
    background: #fff;
    border: 1px solid var(--el-border-color);
    box-sizing: border-box;
  }
  .aside {
    background-color: #001529;
  }
}
</style>
