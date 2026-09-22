<template>
  <el-sub-menu
    v-if="menu.type === 1"
    :index="menu.id"
    :data="menu"
    :class="{ 'akm-menu-active': isActive }"
  >
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

  <el-menu-item
    v-else
    :index="menu.id"
    :data="menu"
    :class="{ 'akm-menu-active': isActive }"
    @click="select(menu)"
  >
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
  computed: {
    isActive() {
      const permissionStore = usePermissionStore()
      return permissionStore.activeMenu?.id === this.menu.id
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

<style lang="scss" scoped>
/**
 * 自定义高亮：当 menu.id === activeMenuId 时动态添加 akm-menu-active 类
 * 不依赖 Element Plus 内部 is-active 状态，避免 :default-active 不响应外部变化导致不高亮
 *
 * 说明：class 加在 el-menu-item / el-sub-menu 组件标签上，会落到其根元素；
 * 子组件根元素会继承本组件的 scoped 属性（data-v-xxx），直接写 .akm-menu-active 即可命中。
 * .el-sub-menu__title 是 el-sub-menu 内部元素（非根元素），需用 :deep() 穿透。
 */
.akm-menu-active {
  background: var(--el-color-primary) !important;
  color: #fff !important;

  :deep(.el-sub-menu__title) {
    background: var(--el-color-primary) !important;
    color: #fff !important;
    i {
      color: #fff !important;
    }
  }
}
</style>
