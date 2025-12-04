<template>
  <div>
    <akm-form
      ref="form"
      :config="formConfig"
      :data="formData"
      @query="query"
      @reset="reset"
    ></akm-form>

    <akm-table ref="table" :config="tableConfig" @selectedChange="onSelectedChange">
      <template #toolbar>
        <el-button v-has="'sys_message_add'" type="primary" @click="add">新增</el-button>
      </template>
      <template #column-op="{ row }">
        <el-button type="primary" link @click="view(row)">查看</el-button>
        <el-button v-has="'sys_message_add'" type="primary" link @click="add(row)">
          复制新增
        </el-button>
        <akm-warn-btn
          v-if="row.messageStatus === 0"
          v-has="'sys_message_del'"
          @confirm="del(row.id)"
        >
          删除
        </akm-warn-btn>
        <el-button
          v-if="row.messageStatus === 0"
          v-has="'sys_message_edit'"
          type="primary"
          link
          @click="edit(row)"
        >
          编辑
        </el-button>
      </template>
    </akm-table>
  </div>
</template>

<script>
export default {
  name: 'SysMessage',
  data() {
    return {
      formData: {
        type: '',
        label: '',
        value: '',
        code: '',
        enable: '',
        remark: '',
      },

      formConfig: {
        inline: true,
        showQueryInline: true,
        showBorder: true,
        items: [
          { type: 'input', prop: 'title', label: '标题' },
          { type: 'input', prop: 'content', label: '内容' },
          {
            type: 'dict',
            code: 'biz_message_type',
            prop: 'messageType',
            label: '消息分类',
          },
          {
            type: 'dict',
            code: 'biz_message_priority_type',
            prop: 'messagePriority',
            label: '消息优先级',
          },
          {
            type: 'dict',
            code: 'biz_message_range_type',
            prop: 'rangeType',
            label: '发送范围',
          },
          {
            type: 'dict',
            code: 'biz_message_status',
            prop: 'messageStatus',
            label: '消息状态',
          },
          { type: 'input', prop: 'bizRecordType', label: '业务场景分类' },
        ],
      },

      selectedList: [],
      tableConfig: {
        uri: '/share/public/biz/message/view/findPage',
        loading: false,
        autoComputedHeight: true,
        pagination: true,
        columns: [
          { prop: 'title', label: '标题', showOverflowTooltip: true },
          { prop: 'content', label: '内容', showOverflowTooltip: true },
          {
            prop: 'messageType',
            label: '消息分类',
            width: 150,
            formatter: (row, column, cellValue) =>
              this.$dict.translateNameByTypeAndValue('biz_message_type', cellValue),
          },
          {
            prop: 'messagePriority',
            label: '消息优先级',
            width: 150,
            formatter: (row, column, cellValue) =>
              this.$dict.translateNameByTypeAndValue('biz_message_priority_type', cellValue),
          },
          {
            prop: 'rangeType',
            label: '发送范围',
            width: 150,
            formatter: (row, column, cellValue) =>
              this.$dict.translateNameByTypeAndValue('biz_message_range_type', cellValue),
          },
          {
            prop: 'messageStatus',
            label: '消息状态',
            width: 150,
            formatter: (row, column, cellValue) =>
              this.$dict.translateNameByTypeAndValue('biz_message_status', cellValue),
          },
          { prop: 'bizRecordType', label: '业务场景分类', width: 200 },
          { type: 'slot', prop: 'op', label: '操作' },
        ],
      },
    }
  },
  mounted() {
    this.query()
  },
  methods: {
    query() {
      this.tableConfig.pageNum = 1
      this.$refs.table.fetchTableData(this.formData)
    },
    reset() {
      this.$refs.form.resetFields()
      this.query()
    },
    onSelectedChange(selectedList) {
      this.selectedList = selectedList
    },
    view(row) {
      this.$router.push({
        name: 'SysMessageView',
        params: {
          messageId: row.id,
          isView: true,
          isEdit: false,
        },
      })
    },
    add(row = {}) {
      this.$router.push({
        name: 'SysMessageAdd',
        params: {
          messageId: row.id || '',
          isView: false,
          isEdit: false,
        },
      })
    },
    edit(row) {
      this.$router.push({
        name: 'SysMessageEdit',
        params: {
          messageId: row.id,
          isView: false,
          isEdit: true,
        },
      })
    },
    del(id) {
      this.tableConfig.loading = true
      this.$http
        .post('/share/public/biz/message/op/batchDel', [id])
        .then(() => {
          this.$helper.successMessage('删除成功')
          this.query()
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
  },
}
</script>

<style lang="scss" scoped></style>
