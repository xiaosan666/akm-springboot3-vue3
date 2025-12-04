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
        <el-button v-has="'sys_tenant_add'" type="primary" @click="add">新增</el-button>
      </template>
      <template #column-enable="{ row }">
        <el-tag v-if="row.enable === 1" type="primary">启用</el-tag>
        <el-tag v-else type="danger">已停用</el-tag>
      </template>
      <template #column-op="{ row }">
        <akm-warn-btn v-has="'sys_tenant_del'" @confirm="del(row.id)">删除</akm-warn-btn>
        <el-button v-has="'sys_tenant_edit'" type="primary" link @click="edit(row)">编辑</el-button>
        <el-button v-has="'sys_tenant_add'" type="primary" link @click="add(row)">
          复制新增
        </el-button>
      </template>
    </akm-table>

    <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
      <akm-form ref="form" :config="dialogFormConfig"></akm-form>
    </akm-dialog>
  </div>
</template>

<script>
export default {
  name: 'SysTenant',
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
          { type: 'input', prop: 'name', label: '租户名称' },
          { type: 'input', prop: 'code', label: '租户编码' },
          {
            type: 'dict',
            code: 'enable_status',
            prop: 'enable',
            label: '启用状态',
          },
        ],
      },

      selectedList: [],
      tableConfig: {
        uri: '/auth/sys/tenant/view/findPage',
        loading: false,
        autoComputedHeight: true,
        pagination: true,
        columns: [
          { prop: 'name', label: '租户名称' },
          { prop: 'code', label: '租户编码' },
          { prop: 'orders', label: '排序', width: 80 },
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
            prop: 'name',
            label: '租户名称',
          },
          {
            type: 'input',
            prop: 'code',
            label: '租户编码',
          },
          { type: 'inputNumber', prop: 'orders', label: '排序' },
          {
            type: 'radioDict',
            code: 'enable_status_op',
            prop: 'enable',
            label: '启用状态',
          },
          { type: 'textarea', prop: 'remark', label: '备注' },
        ],
        rules: {
          code: [{ required: true, message: '请输入租户编码编码', trigger: 'blur' }],
          name: [{ required: true, message: '请输入租户编码名称', trigger: 'blur' }],
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
            .post('/auth/sys/tenant/op/insertOrUpdate', data)
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
    del(id) {
      this.tableConfig.loading = true
      this.$http
        .post('/auth/sys/tenant/op/del', id)
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
