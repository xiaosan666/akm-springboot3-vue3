<template>
  <div>
    <akm-form
      ref="form"
      :data="formData"
      :config="formConfig"
      @query="query"
      @reset="reset"
    ></akm-form>

    <akm-table ref="table" :config="tableConfig" @selectedChange="onSelectedChange">
      <template #toolbar>
        <el-button
          v-has="'sys_dict_del'"
          type="danger"
          :disabled="!selectedList.length"
          @click="batchDel"
        >
          删除
        </el-button>
        <el-button v-has="'sys_dict_add'" type="primary" @click="add">新增</el-button>
      </template>
      <template #column-enable="{ row }">
        <el-tag v-if="row.enable === 1" type="primary">启用</el-tag>
        <el-tag v-else type="danger">已停用</el-tag>
      </template>
      <template #column-op="{ row }">
        <akm-warn-btn v-has="'sys_dict_del'" @confirm="del([row.id])">删除</akm-warn-btn>
        <el-button v-has="'sys_dict_edit'" type="primary" link @click="edit(row)">编辑</el-button>
        <el-button v-has="'sys_dict_add'" type="primary" link @click="add(row)">复制新增</el-button>
      </template>
    </akm-table>

    <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
      <akm-form ref="form" :config="dialogFormConfig"></akm-form>
    </akm-dialog>
  </div>
</template>

<script>
export default {
  name: 'SysDict',
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
          { type: 'input', prop: 'type', label: '字典类型编码' },
          { type: 'input', prop: 'label', label: 'label' },
          { type: 'input', prop: 'value', label: 'value' },
          { type: 'input', prop: 'code', label: 'code' },
          {
            type: 'dict',
            code: 'enable_status',
            prop: 'enable',
            label: '启用状态',
          },
          { type: 'input', prop: 'remark', label: '备注' },
        ],
      },

      selectedList: [],
      tableConfig: {
        uri: '/auth/sys/dict/view/findPage',
        loading: false,
        autoComputedHeight: true,
        multipleSelect: true,
        pagination: true,
        pageSize: 50,
        columns: [
          { prop: 'type', label: '字典类型编码' },
          { prop: 'label', label: '名称（label）' },
          { prop: 'value', label: '值（value）' },
          { prop: 'code', label: '值（code）', showOverflowTooltip: true },
          { prop: 'orders', label: '排序（倒序）', width: 110 },
          {
            type: 'slot',
            prop: 'enable',
            label: '启用状态',
            width: 100,
          },
          { prop: 'remark', label: '备注', showOverflowTooltip: true },
          { type: 'slot', prop: 'op', label: '操作' },
        ],
      },

      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 500,
      },
      dialogFormConfig: {
        labelWidth: 150,
        data: {
          type: '',
          label: '',
          value: '',
          code: '',
          orders: 100,
          enable: 1,
          remark: '',
        },
        items: [
          {
            type: 'input',
            prop: 'type',
            label: '字典类型编码',
            placeholder: '形如：sex',
          },
          {
            type: 'input',
            prop: 'label',
            label: '名称（label）',
            placeholder: '形如：男',
          },
          {
            type: 'input',
            prop: 'value',
            label: '字典值（value）',
            placeholder: '形如：1',
          },
          {
            type: 'input',
            prop: 'code',
            label: '字典值（code）',
            placeholder: '形如：man',
          },
          { type: 'inputNumber', prop: 'orders', label: '排序（倒序）' },
          {
            type: 'radioDict',
            code: 'enable_status_op',
            prop: 'enable',
            label: '启用状态',
          },
          { type: 'textarea', prop: 'remark', label: '备注' },
        ],
        rules: {
          type: [{ required: true, message: '请输入字典类型编码', trigger: 'blur' }],
          label: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
          value: [
            {
              required: true,
              message: '请输入字典字典值（value）',
              trigger: 'blur',
            },
          ],
          code: [
            {
              required: true,
              message: '请输入字典字典值（code）',
              trigger: 'blur',
            },
          ],
        },
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
    add(row) {
      this.$helper.createCsrfToken()
      if (row && row.id) {
        this.dialogFormConfig.data = this.$utils.clone(row)
      }
      this.dialogFormConfig.data.id = ''
      this.dialogConfig.title = '新增'
      this.dialogConfig.visible = true
    },
    edit(row) {
      this.$helper.createCsrfToken()
      if (row && row.id) {
        this.dialogFormConfig.data = this.$utils.clone(row)
      }
      this.dialogConfig.title = this.dialogFormConfig.data.id ? '编辑' : '新增'
      this.dialogConfig.visible = true
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.dialogConfig.loading = true
          let data = this.dialogFormConfig.data
          this.$http
            .post('/auth/sys/dict/op/insertOrUpdate', data)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$refs.form.resetFields()
              this.query()
              this.dialogConfig.visible = false
            })
            .catch(() => {
              this.$helper.createCsrfToken()
            })
            .finally(() => {
              this.dialogConfig.loading = false
            })
        }
      })
    },
    batchDel() {
      let list = this.selectedList
      this.$helper
        .confirm(`即将删除${list.length}条记录，确定删除吗？`)
        .then(() => {
          this.del(list.map(item => item.id))
        })
        .catch(() => {})
    },
    del(ids) {
      this.tableConfig.loading = true
      this.$http
        .post('/auth/sys/dict/op/batchDel', ids)
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
