<template>
  <div>
    <el-badge :value="total" :max="99" class="message-badge" @click="toMessagePage">
      <el-icon><Message /></el-icon>
    </el-badge>
  </div>
</template>

<script>
import { Message } from '@element-plus/icons-vue'

export default {
  name: 'TheHeaderMessage',
  components: {
    Message,
  },
  data() {
    return {
      total: 0,
    }
  },
  created() {
    this.unReadMessageCount()

    // 监听消息已读事件，更新角标数量
    this.$eventBus.on(this.$eventBus.keys.unReadMessageCountChange, () => {
      this.total = --this.total
    })
  },
  methods: {
    async unReadMessageCount() {
      try {
        const res = await this.$http.post('/share/public/biz/message/my/unReadMessageCount')
        this.total = res
      } catch (error) {
        console.error('获取未读消息数量失败:', error)
      }
    },
    toMessagePage() {
      let url = '/message/myMessage'
      if (url === this.$route.path) {
        return
      }
      this.$router.push(url)
    },
  },
}
</script>

<style lang="scss" scoped>
.message-badge {
  cursor: pointer;
  margin-right: 30px;
  .el-icon {
    font-size: 20px;
  }
  :deep(.el-badge__content) {
    top: 10px !important;
  }
}
</style>
