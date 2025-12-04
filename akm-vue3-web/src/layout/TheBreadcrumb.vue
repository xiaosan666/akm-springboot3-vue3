<template>
  <el-breadcrumb v-if="showBreadcrumb" separator="/">
    <el-breadcrumb-item :to="{ path: '/user/center' }">个人中心</el-breadcrumb-item>
    <el-breadcrumb-item v-for="(item, index) in breadList" :key="index">
      <router-link v-if="item.path" :to="{ path: item.path }">
        {{ item.name }}
      </router-link>
      <span v-if="!item.path">{{ item.name }}</span>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
import { usePermissionStore } from '@/stores/permission'

export default {
  name: 'TheBreadcrumb',
  data() {
    return {
      showBreadcrumb: true,
    }
  },
  computed: {
    breadList() {
      const permissionStore = usePermissionStore()
      return permissionStore.breadcrumbList
    },
    activeMenuId() {
      const permissionStore = usePermissionStore()
      return permissionStore.activeMenu?.id || ''
    },
    menuList() {
      const permissionStore = usePermissionStore()
      return permissionStore.permissionMenu.menuList
    },
  },
  watch: {
    $route(route) {
      // 个人中心清空菜单选中
      if (route.name === 'UserCenter') {
        const permissionStore = usePermissionStore()
        permissionStore.setBreadcrumbList([])
        permissionStore.setActiveMenu(null)
      } else {
        this.getBreadcrumb()
      }
    },
  },
  methods: {
    getBreadcrumb() {
      let breadList = []
      let currentMeta = this.$route.meta || {}
      // 根据meta判断是否需要显示面包屑
      this.showBreadcrumb = !(currentMeta && currentMeta.breadcrumb === false)
      if (this.showBreadcrumb) {
        // 获取当前菜单及所有父级菜单，用于生成面包屑
        let menus = this.getParentMenus(this.activeMenuId, [])
        breadList = menus.reverse().map(item => {
          return { path: item.uri || '', name: item.name || '' }
        })
        // 添加三级页面路由
        if (!this.isMenu(this.$route.path)) {
          breadList.push({
            path: this.$route.path,
            name: currentMeta.title || '',
          })
        }
      }
      const permissionStore = usePermissionStore()
      permissionStore.setBreadcrumbList(breadList)
    },
    // 根据id递归获取所有菜单
    getParentMenus(id, menus) {
      let menu = this.menuList.filter(item => item.id === id).pop()
      if (menu) {
        menus.push(menu)
        this.getParentMenus(menu.parentId, menus)
      }
      return menus
    },
    isMenu(path) {
      return this.menuList.some(item => item.uri === path)
    },
  },
}
</script>

<style lang="scss" scoped>
.el-breadcrumb {
  padding: 12px 0;
}
</style>
