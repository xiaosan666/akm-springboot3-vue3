<template>
  <div class="akm-header-warp">
    <div class="title">统一后台管理系统</div>
    <div class="akm-header-right-warp">
      <the-header-message></the-header-message>
      <el-dropdown trigger="hover" class="name-role" @command="logout">
        <span class="el-dropdown-link">
          <el-icon><UserFilled /></el-icon>
          {{ userInfo.realname }}
          <el-icon><ArrowDown /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user'
import TheHeaderMessage from './TheHeaderMessage.vue'
import { UserFilled, ArrowDown } from '@element-plus/icons-vue'

export default {
  name: 'TheHeader',
  components: {
    TheHeaderMessage,
    UserFilled,
    ArrowDown,
  },
  computed: {
    userInfo() {
      const userStore = useUserStore()
      console.log(userStore.userInfo.realname)
      return userStore.userInfo
    },
  },
  methods: {
    async logout() {
      const userStore = useUserStore()
      await userStore.logout()
    },
  },
}
</script>

<style lang="scss" scoped>
.akm-header-warp {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 30px;
  .title {
    font-size: 24px;
    font-weight: bold;
  }
  .akm-header-right-warp {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .name-role {
    color: #fff;
    cursor: pointer;
    margin-bottom: 10px;
  }
}
</style>
