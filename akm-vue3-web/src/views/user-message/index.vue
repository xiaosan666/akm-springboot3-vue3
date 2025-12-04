<template>
  <el-container class="user-message-page">
    <el-aside width="200px">
      <el-menu :default-active="defaultActiveUrl" @select="handleSelect">
        <el-menu-item
          v-for="(item, index) in menuList"
          :key="index"
          :index="item.url"
          class="menu-item"
        >
          <span>
            <i :class="item.icon"></i>
            <span slot="title">
              {{ item.name }}
            </span>
          </span>
          <i class="el-icon-arrow-right el-icon--right"></i>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <keep-alive>
        <router-view />
      </keep-alive>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: 'UserMessage',
  components: {},
  data() {
    return {
      menuList: [
        {
          name: '消息通知',
          url: '/message/myMessage',
          icon: 'el-icon-message',
        },
        {
          name: '系统公告',
          url: '/message/myNotice',
          icon: 'el-icon-bell',
        },
      ],
      defaultActiveUrl: '/message/myMessage',
    }
  },
  created() {
    let routePath = this.$route.path
    if (this.defaultActiveUrl !== routePath) {
      this.defaultActiveUrl = routePath
    }
  },
  mounted() {},
  methods: {
    handleSelect(url) {
      if (url === this.$route.path) {
        return
      }
      this.$router.push(url)
    },
  },
}
</script>

<style lang="scss" scoped>
.user-message-page {
  height: 100%;
  .menu-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
