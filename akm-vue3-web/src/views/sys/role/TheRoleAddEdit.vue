<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <akm-form ref="form" :config="formConfig"></akm-form>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheRoleAddEdit',
  data() {
    return {
      firstOpen: true, // 标记首次打开才去加载数据
      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 500,
      },
      formConfig: {
        data: {
          id: '',
          name: '',
          code: '',
          remark: '',
          tenantId: '',
          orders: 100,
          enable: 1,
        },
        items: [
          {
            type: 'input',
            prop: 'name',
            label: '角色名称',
          },
          { type: 'input', prop: 'code', label: '角色编码' },
          {
            type: 'select',
            prop: 'tenantId',
            label: '所属租户',
            options: [],
          },
          { type: 'inputNumber', prop: 'orders', label: '排序（倒序）' },
          { type: 'textarea', prop: 'remark', label: '备注' },
          {
            type: 'radioDict',
            code: 'enable_status_op',
            prop: 'enable',
            label: '启用状态',
          },
        ],
        rules: {
          name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
          code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
          tenantId: [{ required: true, message: '请选择所属租户', trigger: 'change' }],
        },
      },
    }
  },
  methods: {
    open(row) {
      if (this.firstOpen) {
        this.fetchAllTenant()
        this.firstOpen = false
      }
      this.$helper.createCsrfToken()
      if (row && row.id) {
        this.formConfig.data = this.$utils.clone(row)
        this.dialogConfig.title = '编辑'
      } else {
        this.formConfig.data.id = ''
        this.dialogConfig.title = '新增'
      }
      this.dialogConfig.visible = true
    },
    fetchAllTenant() {
      this.$http.post('/auth/sys/tenant/view/findAll', { enabled: 1 }).then(res => {
        this.formConfig.items.find(item => item.prop === 'tenantId').options = res.map(item => {
          return {
            name: item.name,
            value: item.id,
          }
        })
      })
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.dialogConfig.loading = true
          let data = this.formConfig.data
          this.$http
            .post('/auth/sys/role/op/insertOrUpdate', data)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$emit('saveSuccess')
              this.$refs.form.resetFields()
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
  },
}
</script>
