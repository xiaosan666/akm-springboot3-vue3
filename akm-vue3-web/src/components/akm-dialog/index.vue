<template>
  <div class="akm-dialog-component">
    <el-dialog
      :model-value="config.visible"
      :close-on-click-modal="false"
      :show-close="false"
      :width="width"
      :top="top"
      :draggable="draggable"
      :overflow="overflow"
      :fullscreen="isFullscreen"
      @update:model-value="handleVisibleChange"
      @closed="resetFullscreen"
    >
      <template #header>
        <div class="dialog-title" @dblclick="toggleFullscreen">
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
          <!-- 默认使用内置按钮；父组件传入 footer 插槽时以父组件内容为准 -->
          <slot name="footer">
            <el-button @click="cancel">
              {{ config.cancelButtonText || '取 消' }}
            </el-button>
            <el-button type="primary" :loading="config.confirmLoading" @click="confirm">
              {{ config.confirmButtonText || '确 定' }}
            </el-button>
          </slot>
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
    return {
      isFullscreen: false,
    }
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
    draggable() {
      return this.config.draggable !== false
    },
    overflow() {
      return this.config.overflow !== false
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
    toggleFullscreen() {
      this.isFullscreen = !this.isFullscreen
    },
    resetFullscreen() {
      this.isFullscreen = false
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
    padding: 16px;
    max-height: 600px;
    overflow: auto;
  }
  .dialog-footer {
    border-top: 1px solid var(--el-border-color);
    text-align: right;
    padding: 12px 16px 0;
  }

  /**
   * 说明：
   * el-dialog 内部渲染的 .el-dialog__header / .el-dialog__body 不带本组件的 data-v-xxx，
   * 直接写在 scoped 块里命中不了。使用 :deep() 让 Vue 编译器去掉作用域属性选择器，
   * 编译为 .akm-dialog-component[data-v-xxx] .el-dialog__header 等，可命中 Element Plus 内部 DOM。
   * el-dialog 默认 appendToBody=false（teleport 禁用），DOM 在 .akm-dialog-component 内。
   */
  :deep(.el-dialog__header) {
    background: #e1e1e1;
    /* .el-dialog 自带 16px padding，用负边距抵消，让灰底铺满弹窗顶部（对齐 vue2 版效果） */
    margin: calc(-1 * var(--el-dialog-padding-primary)) calc(-1 * var(--el-dialog-padding-primary))
      0;
    /* 补回 header 原有内边距，保证标题位置与改动前完全一致 */
    padding: var(--el-dialog-padding-primary);
  }
  :deep(.el-dialog__body) {
    padding: 0;
  }
}
</style>
