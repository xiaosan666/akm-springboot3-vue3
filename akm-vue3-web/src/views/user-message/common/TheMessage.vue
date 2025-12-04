<template>
  <div class="message-page">
    <div v-if="total === 0" class="no-data">暂无数据</div>
    <div v-if="total > 0" class="search-warp">
      <el-checkbox v-model="checked">仅显示未读</el-checkbox>
      <el-input
        v-model="query.searchContent"
        v-margin-left="30"
        clearable
        placeholder="可关键字（标题、内容）模糊搜索"
        prefix-icon="el-icon-search"
        style="width: 280px"
        @clear="fetchData"
        @keydown.enter.native="fetchData"
      ></el-input>
      <el-button style="margin-left: 4px" type="primary" @click="fetchData">搜 索</el-button>
    </div>
    <el-collapse v-if="total > 0" v-loading="loading" accordion>
      <el-collapse-item v-for="item in dataList" :key="item.id">
        <template #title>
          <div
            :class="['message-title-warp', { 'un-read': item.isRead !== 1 }]"
            @click="handleRead(item)"
          >
            <div>
              <span class="badge-warp">
                <el-badge v-if="item.isRead !== 1" is-dot></el-badge>
              </span>
              <span>{{ item.title }}</span>
            </div>
            <span class="time">
              {{ $utils.formatDate(item.createTime, 'yyyy-MM-dd HH:mm:ss') }}
            </span>
          </div>
        </template>
        <div class="message-content">
          {{ item.content }}
          <el-button v-if="item.bizUrl" type="primary" link @click="handleUrl(item)">
            查看详情
            <i class="el-icon-top-right el-icon--right"></i>
          </el-button>
          <akm-attachment-list
            :data="item.bizAttachments"
            :show-del-btn="false"
          ></akm-attachment-list>
        </div>
      </el-collapse-item>
    </el-collapse>
    <div v-if="total > pageSize" v-text-center v-margin-top="5">
      <el-pagination
        layout="prev, pager, next"
        :current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        @current-change="onPageNumChange"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import AkmAttachmentList from '@/views/common/akm-attachment-list/index.vue'
export default {
  name: 'TheMessage',
  components: { AkmAttachmentList },
  props: {
    messageType: {
      type: Number,
      default() {
        return 1
      },
    },
  },
  data() {
    return {
      checked: false,
      loading: false,

      query: {
        messageType: this.messageType,
        isRead: '',
        searchContent: '',
      },

      total: 0,
      dataList: [],
      pageNum: 1,
      pageSize: 20,
    }
  },
  watch: {
    checked(val) {
      this.query.isRead = val ? 0 : ''
      this.fetchData()
    },
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 分页查询我的未读消息
    fetchData() {
      this.loading = true
      this.$http
        .post('/share/public/biz/message/my/message', {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          condition: this.query,
        })
        .then(res => {
          this.total = res.total
          this.dataList = res.list
        })
        .finally(() => {
          this.loading = false
        })
    },
    onPageNumChange(pageNum) {
      this.pageNum = pageNum
      this.fetchData()
    },
    filterData() {},
    handleRead(data) {
      if (data.isRead === 1) {
        return
      }
      // 标记已读
      data.isRead = 1
      // 通知更新头部未读消息数量
      if (this.messageType === 1) {
        this.$eventBus.emit(this.$eventBus.keys.unReadMessageCountChange)
      }
      this.$http.post('/share/public/biz/message/my/read', data.id).then()
    },
    handleUrl(item) {
      this.$router.push(item.bizUrl)
    },
  },
}
</script>

<style scoped lang="scss">
.message-page {
  .search-warp {
    display: flex;
    align-items: center;
    margin-bottom: 5px;
  }
  .message-title-warp {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #999;
    .badge-warp {
      display: inline-block;
      width: 15px;
    }
    .time {
      padding-right: 20px;
    }
  }
  .un-read {
    color: #333;
  }
  .no-data {
    text-align: center;
    font-size: 20px;
    margin: 10px;
    color: #666;
  }
  .message-content {
    padding: 2px 15px;
    background: #f5f5f5;
  }
}
</style>
<style lang="scss">
.message-page {
  .el-collapse-item__wrap {
    background: #f5f5f5;
    border-radius: 5px;
  }
  .is-active {
  }
}
</style>
