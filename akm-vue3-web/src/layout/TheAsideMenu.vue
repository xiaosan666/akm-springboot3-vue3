<template>
  <el-scrollbar style="height: 100%">
    <el-menu class="akm-layout-menu" active-text-color="#fff" :default-active="activeMenuId">
      <the-aside-menu-item v-for="menu in menus" :key="menu.id" :menu="menu"></the-aside-menu-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { usePermissionStore } from '@/stores/permission'
import TheAsideMenuItem from './TheAsideMenuItem.vue'

export default {
  name: 'TheAsideMenu',
  components: {
    TheAsideMenuItem,
  },
  props: {
    menus: {
      type: Array,
      default() {
        return []
      },
    },
  },
  computed: {
    menuList() {
      const permissionStore = usePermissionStore()
      return permissionStore.permissionMenu.menuList
    },
    activeMenuId() {
      const permissionStore = usePermissionStore()
      return permissionStore.activeMenu?.id || ''
    },
  },
  watch: {
    // 当路由变化，判断路由对应的菜单是否和当前选中菜单一致，如从A菜单跳转到B菜单，则需要选中B菜单
    $route(route) {
      let menu = this.menuList.find(item => item.uri === route.path)
      if (menu && menu.id !== this.activeMenuId) {
        const permissionStore = usePermissionStore()
        permissionStore.setActiveMenu(menu)
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.akm-layout-menu {
  border-right: none;
  :deep(.el-sub-menu__title) {
    background-color: #001529;
    color: #ddd;
    height: 50px;
    line-height: 50px;
    &:hover {
      color: #fff;
      background: rgba(0, 21, 41, 0.9);
      i {
        color: #fff;
      }
    }
  }
  :deep(.el-submenu.is-active) {
    .el-submenu__title {
      color: #fff;
      i {
        color: #fff;
      }
    }
    .el-menu-item {
      background: #000c17;
    }
    .el-menu-item.is-active {
      background: var(--el-color-primary);
    }
  }
  :deep(.el-menu-item-group__title) {
    background: #001529;
    color: #aaa;
  }
  :deep(.el-menu-item) {
    background: #001529;
    color: #ddd;
    height: 44px;
    line-height: 44px;
    &:hover {
      color: #fff;
      background: rgba(0, 21, 41, 0.9);
    }
  }
  :deep(.el-menu-item.is-disabled) {
    color: #aaa;
    opacity: 1;
    background: #001529 !important;
    &:hover {
      color: #aaa;
    }
  }
}
</style>
