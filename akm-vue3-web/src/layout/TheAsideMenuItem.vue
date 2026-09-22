<template>
  <el-sub-menu v-if="menu.type === 1" :index="menu.id" :data="menu">
    <template v-slot:title>
      <el-icon><Menu /></el-icon>
      <span>{{ menu.name }}</span>
    </template>
    <the-aside-menu-item
      v-for="subMenu in menu.children"
      :key="subMenu.id"
      :menu="subMenu"
      @selecte="select(subMenu)"
    ></the-aside-menu-item>
  </el-sub-menu>

  <el-menu-item v-else :index="menu.id" :data="menu" @click="select(menu)">
    <template v-slot:title>
      <!-- <el-icon><Position /></el-icon> -->
      {{ menu.name }}
    </template>
  </el-menu-item>
</template>
<script>
import { usePermissionStore } from '@/stores/permission'
export default {
  name: 'TheAsideMenuItem',
  props: {
    menu: {
      type: Object,
      required: true,
    },
  },
  methods: {
    select(menu) {
      const permissionStore = usePermissionStore()

      if (menu.uri.startsWith('http')) {
        permissionStore.setActiveMenu(menu)
        window.open(menu.uri)
        return
      }

      // 路由 meta.openInNewTab 为 true 时，在新浏览器标签页打开
      const { href, meta } = this.$router.resolve(menu.uri)
      if (meta.openInNewTab) {
        // 当前页未发生跳转，不改变左侧菜单高亮
        window.open(href, '_blank', 'noopener')
        return
      }

      permissionStore.setActiveMenu(menu)
      this.$router.push(menu.uri)
    },
  },
}
</script>

<style lang="scss" scoped></style>
