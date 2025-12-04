<template>
  <div class="run-log-wrapper">
    <div v-loading="loading" class="actions-container">
      <el-checkbox v-model="autoRefreshEnabled" style="margin-right: 15px">自动刷新</el-checkbox>
      <template v-if="autoRefreshEnabled">
        <el-input-number
          v-model="refreshInterval"
          :min="1"
          :step="1"
          size="small"
          controls-position="right"
          style="width: 100px; margin-right: 5px"
        ></el-input-number>
        <span style="margin-right: 15px">秒 ({{ countdown }}s)</span>
      </template>

      <el-form :inline="true" size="small" style="display: inline-block; margin-bottom: -18px">
        <el-form-item label="行数">
          <el-input-number
            v-model="maxLine"
            :min="100"
            :max="2000"
            :step="100"
            size="small"
            controls-position="right"
            style="width: 120px"
          ></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" icon="Refresh" @click="queryLog">
            刷新
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div ref="logContainer" class="log-container" v-html="log"></div>
  </div>
</template>

<script>
export default {
  name: 'RunLog',
  data() {
    return {
      loading: false,
      maxLine: 500,
      logFileName: '',
      log: '',
      autoRefreshEnabled: false,
      refreshInterval: 5, // in seconds
      countdown: 5,
      timer: null,
    }
  },
  watch: {
    autoRefreshEnabled(isEnabled) {
      if (isEnabled) {
        this.startAutoRefresh()
      } else {
        this.stopAutoRefresh()
      }
    },
    refreshInterval(newVal) {
      this.countdown = newVal
      if (this.autoRefreshEnabled) {
        this.stopAutoRefresh()
        this.startAutoRefresh()
      }
    },
  },
  created() {
    this.logFileName = this.$route.meta.logFileName || 'all_log.log'
    this.queryLog()
  },
  beforeUnmount() {
    this.stopAutoRefresh()
  },
  methods: {
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.logContainer
        if (container) {
          container.scrollTop = container.scrollHeight
        }
      })
    },
    queryLog() {
      this.loading = true
      // Manually refreshing should reset the countdown
      if (this.timer) {
        this.countdown = this.refreshInterval
      }
      this.$http
        .post('/auth/sys/log/view/run_log', {
          maxLine: this.maxLine,
          logFileName: this.logFileName,
        })
        .then(res => {
          let html = []
          res.forEach(it => {
            html.push(this.formatLog(it))
          })
          this.log = html.join('<br/>')
          this.scrollToBottom()
        })
        .finally(() => {
          this.loading = false
        })
    },
    formatLog(line) {
      const match = line.match(/^\[([^\]]+)\]\s+(\S+)\s+(\S+)\s+([^-]+)-\s+(.*)$/)
      if (!match) {
        return `<span class="log-content">${line}</span>`
      }

      const [, time, thread, level, clazz, content] = match

      let formattedContent = ''
      if (/select|update|delete|insert/i.test(content)) {
        formattedContent = `<span class="log-sql">${content}</span>`
      } else {
        formattedContent = `<span class="log-content">${content}</span>`
      }
      return `<span class="log-time">[${time}]</span> <span class="log-thread">${thread}</span> <span class="log-level log-level-${level.toLowerCase()}">${level}</span> <span class="log-class">${clazz}-</span> ${formattedContent}`
    },
    startAutoRefresh() {
      if (this.timer) return
      this.countdown = this.refreshInterval
      this.timer = setInterval(() => {
        if (this.countdown > 1) {
          this.countdown--
        } else {
          this.queryLog()
          this.countdown = this.refreshInterval
        }
      }, 1000)
    },
    stopAutoRefresh() {
      clearInterval(this.timer)
      this.timer = null
    },
  },
}
</script>

<style scoped>
.run-log-wrapper {
  /* No longer need relative positioning or padding */
}

.actions-container {
  position: fixed; /* Changed from absolute to fixed */
  top: 90px; /* Adjust based on your app's header height */
  right: 40px; /* Adjust for better spacing */
  padding: 10px;
  display: flex;
  align-items: center;
  z-index: 10;
  background: #f5f7fa; /* Add a background to distinguish from content */
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.log-container {
  background: #23272e;
  color: #e0e0e0;
  font-family: 'Fira Mono', 'Consolas', 'Menlo', monospace;
  font-size: 14px;
  padding: 16px;
  border-radius: 8px;
  height: calc(100vh - 100px); /* Adjust height to fit your layout better */
  white-space: pre-wrap;
  overflow-x: auto;
  overflow-y: scroll;
}
</style>

<style>
/* Global styles for v-html content */
.log-time {
  color: #8ec07c;
}
.log-thread {
  color: #b8bb26;
}
.log-level {
  font-weight: bold;
}
.log-level-info {
  color: #83a598;
}
.log-level-error {
  color: #fb4934;
}
.log-level-warn {
  color: #fabd2f;
}
.log-level-debug {
  color: #b8bb26;
}
.log-class {
  color: #d3869b;
}
.log-sql {
  color: #fabd2f;
  font-style: italic;
}
.log-content {
  color: #e0e0e0;
}
</style>
