<template>
  <span v-margin-left>
    <el-button type="primary" @click="openSelectUserDialog">选择用户</el-button>
    <akm-table v-if="tableConfig.data.length" :config="tableConfig">
      <el-table-column class-name="op-buttons" label="操作">
        <template v-slot="{ row }">
          <akm-warn-btn @confirm="del(row)">删除</akm-warn-btn>
        </template>
      </el-table-column>
    </akm-table>
    <akm-select-user-dialog
      ref="selectUserDialog"
      @confirm="confirmSelectUserDialog"
    ></akm-select-user-dialog>
  </span>
</template>

<script>
import AkmSelectUserDialog from '@/views/common/akm-select-user-dialog/index.vue'
export default {
  name: 'TheSelectUser',
  components: { AkmSelectUserDialog },
  data() {
    return {
      tableConfig: {
        data: [],
        loading: false,
        columns: [
          { prop: 'username', label: '用户名' },
          { prop: 'realname', label: '姓名' },
          { prop: 'age', label: '年龄' },
        ],
      },
    }
  },
  methods: {
    openSelectUserDialog() {
      const selectedIds = this.tableConfig.data.map(i => i.id)
      this.$refs.selectUserDialog.open(selectedIds)
    },
    confirmSelectUserDialog(selectedList) {
      this.$helper.successMessage(`成功新增${selectedList.length}条数据`)
      this.tableConfig.data = [...this.tableConfig.data, ...selectedList]
      this.$refs.selectUserDialog.clear()
      this.$refs.selectUserDialog.close()
    },
    del(row) {
      this.tableConfig.data = this.tableConfig.data.filter(item => item !== row)
      this.$helper.successMessage()
    },
  },
}
</script>

<style scoped></style>
