<template>
  <akm-dialog :config="dialogConfig" @cancel="cancel" @confirm="confirm">
    <akm-select-org-tree ref="akmSelectOrgTree"></akm-select-org-tree>
  </akm-dialog>
</template>
<script>
import AkmSelectOrgTree from '@/views/common/akm-select-org-tree/index.vue'
export default {
  name: 'AkmSelectOrgTreeDialog',
  components: { AkmSelectOrgTree },
  props: {
    // 是否单选
    singleSelect: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '选择单位/部门',
      },
    }
  },
  created() {},
  methods: {
    // 打开dialog
    open(checkedIds = []) {
      this.dialogConfig.visible = true
      this.$nextTick(() => {
        this.$refs.akmSelectOrgTree.setCheckedKeys(checkedIds)
        this.$refs.akmSelectOrgTree.setExpandedKeys(checkedIds)
      })
    },
    // 关闭dialog
    close() {
      this.dialogConfig.visible = false
    },
    // dialog确定
    confirm() {
      let orgList = this.$refs.akmSelectOrgTree.getCheckedData()
      this.$emit('confirm', orgList)
      this.close()
    },
    cancel() {
      this.$emit('cancel')
      this.dialogConfig.visible = false
    },
  },
}
</script>
