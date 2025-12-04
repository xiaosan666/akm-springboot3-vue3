<!--告警按钮-->
<template>
  <el-button class="akm-no-padding" link>
    <el-popover v-model="visible" placement="top" width="160">
      <p>
        <el-icon class="akm-warning-icon-question"><QuestionFilled /></el-icon>
        确定
        <slot></slot>
        吗？
      </p>
      <div v-text-right v-no-margin>
        <el-button size="small" @click="visible = false">取消</el-button>
        <el-button type="primary" size="small" @click="onConfirm">确定</el-button>
      </div>
      <template v-slot:reference>
        <el-button class="akm-no-padding" link style="color: #f56c6c">
          <slot></slot>
        </el-button>
      </template>
    </el-popover>
  </el-button>
</template>
<script>
import { QuestionFilled } from '@element-plus/icons-vue'

export default {
  name: 'AkmWarnBtn',
  components: {
    QuestionFilled,
  },
  props: {
    data: {
      type: Object,
      default() {
        return {}
      },
    },
  },
  data() {
    return {
      visible: false,
    }
  },
  methods: {
    onConfirm() {
      this.visible = false
      this.$emit('confirm')
    },
  },
}
</script>
<style lang="scss">
.akm-warn-btn {
  padding: 0;
  .el-button--text {
    padding: 0;
  }
}
</style>
<style scoped>
.akm-warning-icon-question {
  color: #e6a23c;
  font-size: 16px;
  margin-right: 4px;
}
</style>
