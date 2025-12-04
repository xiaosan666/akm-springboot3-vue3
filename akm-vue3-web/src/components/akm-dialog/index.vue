<template>
  <div class="akm-dialog-component">
    <el-dialog
      v-dialog-drag
      :model-value="config.visible"
      :close-on-click-modal="false"
      :show-close="false"
      :width="width"
      :top="top"
      @update:model-value="handleVisibleChange"
    >
      <template #header>
        <div class="dialog-title">
          <div class="title">{{ config.title }}</div>
          <div class="back" @click="back">
            <el-icon><Back /></el-icon>
            返回上级页面
          </div>
        </div>
      </template>
      <div v-loading="config.loading">
        <div class="dialog-body">
          <slot></slot>
        </div>
      </div>
      <template #footer>
        <div v-if="!(config.footer === false)" class="dialog-footer">
          <el-button @click="cancel">
            {{ config.cancelButtonText || '取 消' }}
          </el-button>
          <el-button type="primary" :loading="config.confirmLoading" @click="confirm">
            {{ config.confirmButtonText || '确 定' }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Back } from '@element-plus/icons-vue'

const MAX_WIDTH = '1000px'
export default {
  name: 'AkmDialog',
  components: {
    Back,
  },
  props: {
    config: {
      type: Object,
      required: true,
      default() {
        return {
          loading: false, // 是否显示loading
          visible: false, // 是否打开dialog
          title: '', // 标题
          width: MAX_WIDTH, // 初始化宽度
          top: '12vh',
          footer: true, // 是否显示footer
          cancelButtonText: '取 消',
          confirmButtonText: '确 定',
          confirmLoading: false, // 确定按钮loading，当footer为true有效
        }
      },
    },
  },
  data() {
    return {}
  },
  computed: {
    width() {
      let width = this.config.width || MAX_WIDTH
      return this.$utils.isNumber(width) ? width + 'px' : width
    },
    top() {
      let top = this.config.top || '12vh'
      return this.$utils.isNumber(top) ? top + 'vh' : top
    },
  },
  methods: {
    handleVisibleChange(visible) {
      this.config.visible = visible
    },
    back() {
      this.config.visible = false
      this.$emit('close')
    },
    cancel() {
      this.$emit('cancel')
    },
    confirm() {
      this.$emit('confirm')
    },
  },
}
</script>

<style lang="scss" scoped>
.akm-dialog-component {
  .dialog-title {
    display: flex;
    justify-content: space-between;
    .title {
      font-size: 18px;
    }
    .back {
      font-size: 16px;
      color: var(--el-color-primary);
      cursor: pointer;
    }
  }
  .dialog-body {
    padding: 20px;
    max-height: 600px;
    overflow: auto;
  }
  .dialog-footer {
    border-top: 1px solid var(--el-border-color);
    text-align: right;
    padding: 12px 20px;
  }
}
</style>

<style lang="scss">
.akm-dialog-component {
  :deep(.el-dialog__header) {
    background: #e1e1e1;
  }
  :deep(.el-dialog__body) {
    padding: 0;
  }
}
</style>
