<template>
  <RouterView id="app" v-if="isRouterAlive" />
</template>

<script>
// import eventBus from '@/providers/eventBus'
export default {
  name: 'App',
  data() {
    return {
      isRouterAlive: true,
    }
  },
  created() {
    // 监听页面刷新事件
    this.$eventBus.on(this.$eventBus.keys.pageRefresh, () => {
      this.refresh()
    })
  },
  methods: {
    refresh() {
      this.isRouterAlive = false
      this.$nextTick(() => {
        this.isRouterAlive = true
      })
    },
  },
}
</script>
