<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <akm-form ref="form" :config="formConfig" :data="formData">
      <template #form-parentId>
        <!--该组件选中的值是数组，所以通过change事件处理-->
        <el-cascader
          ref="cascader"
          v-model="cascaderParentId"
          :options="menuOptions"
          :props="{ checkStrictly: true, value: 'id', label: 'name' }"
          style="width: 100%"
          @change="onCascaderChange"
        ></el-cascader>
      </template>
    </akm-form>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheApiAddEditDialog',
  props: {
    menuOptions: {
      type: Array,
      default() {
        return []
      },
    },
  },
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 560,
      },
      formData: {
        id: '',
        type: 2, // 1目录，2uri
        parentId: 0,
        name: '',
        uri: '',
        orders: 100,
        remark: '',
      },
      formConfig: {
        items: [
          {
            type: 'radioButtonDict',
            code: 'api_type',
            prop: 'type',
            label: '类型',
          },
          { type: 'input', prop: 'name', label: '名称' },
          { type: 'slot', prop: 'parentId', label: '上级目录' },
          {
            type: 'input',
            prop: 'uri',
            label: '接口uri',
            placeholder: '支持通配符，如 /auth/sys/dict/op/**',
            hide: false,
          },
          { type: 'inputNumber', prop: 'orders', label: '排序' },
          { type: 'textarea', prop: 'remark', label: '备注' },
        ],
        rules: {
          type: [{ required: true, message: '请选择类型', trigger: 'change' }],
          name: [{ required: true, message: '请输入名称', trigger: 'change' }],
          uri: [{ required: true, message: '请输入接口地址', trigger: 'change' }],
          parentId: [{ required: true, message: '请选择上级菜单', trigger: 'change' }],
        },
      },

      cascaderParentId: '',
    }
  },
  watch: {
    'formData.type'(val) {
      if (val !== 1) {
        // 非目录类型，选中根目录则清空选中
        if (this.formData.parentId === '0') {
          this.cascaderParentId = ''
          this.formData.parentId = ''
        }
      }
      // 非目录类型，禁用根目录
      let root = this.menuOptions.find(item => item.id === '0')
      root.disabled = Number(val) !== 1
      // 目录类型隐藏地址输入框
      let uriItem = this.formConfig.items.find(item => item.prop === 'uri')
      uriItem.hide = Number(val) === 1
    },
  },
  methods: {
    add(row, isCopyAdd) {
      this.$helper.createCsrfToken()
      if (row && row.id) {
        // 复制新增
        if (isCopyAdd) {
          this.formData = this.$utils.clone(row)
          this.cascaderParentId = row.parentId
        } else {
          // 新增子节点
          this.cascaderParentId = row.id
          this.formData.parentId = row.id
          if (Number(row.type) === 1) {
            this.formData.type = String(Number(row.type) + 1)
          }
        }
      }
      this.formData.id = ''
      this.dialogConfig.title = '新增'
      this.dialogConfig.visible = true
    },
    edit(row) {
      this.$helper.createCsrfToken()
      let data = this.$utils.clone(row)
      delete data.children
      this.formData = data
      this.cascaderParentId = row.parentId
      this.dialogConfig.title = '编辑'
      this.dialogConfig.visible = true
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          // 目录类型，重置uri为空
          if (this.formData.type === 1) {
            this.formData.uri = ''
          }
          this.dialogConfig.loading = true
          this.$http
            .post('/auth/sys/api/op/insertOrUpdate', this.formData)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$emit('saveSuccess')
              this.formData.type = 2 // 还原type默认值
              // 待type watch事件执行完成重置表单，否则字段默认值重置有无
              this.$nextTick(() => {
                this.cascaderParentId = ''
                this.dialogConfig.visible = false
                this.$refs.form.resetFields()
              })
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
    onCascaderChange(valueArr) {
      this.formData.parentId = this.$utils.clone(valueArr).pop()
    },
  },
}
</script>
