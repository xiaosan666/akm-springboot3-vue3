<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <akm-form ref="form" :config="formConfig" :data="formData">
      <template #form-parentId>
        <el-cascader
          ref="cascader"
          v-model="formData.parentId"
          :options="orgOptions"
          :props="{
            emitPath: false,
            checkStrictly: true,
            value: 'id',
            label: 'name',
          }"
          style="width: 100%"
        ></el-cascader>
      </template>
    </akm-form>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheOrgAddEditDialog',
  props: {
    orgOptions: {
      type: Array,
      default() {
        return []
      },
    },
    dataList: {
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
        width: 600,
      },
      formData: {
        id: '',
        parentId: '0',
        orgCode: '',
        name: '',
        level: '',
        levelName: '',
        orgFullName: '',
        orgFullId: '',
        remark: '',
        orders: 100,
        enable: 1,
      },
      formConfig: {
        labelWidth: 130,
        items: [
          { type: 'input', prop: 'name', label: '组织机构名称' },
          { type: 'input', prop: 'orgCode', label: '编码' },
          { type: 'input', prop: 'level', label: '等级' },
          { type: 'slot', prop: 'parentId', label: '上级' },
          { type: 'inputNumber', prop: 'orders', label: '排序' },
          {
            type: 'radioDict',
            code: 'enable_status_op',
            prop: 'enable',
            label: '启用状态',
          },
          { type: 'textarea', prop: 'remark', label: '备注' },
          {
            type: 'textarea',
            prop: 'orgFullName',
            label: '全路径',
            disabled: true,
          },
        ],
        rules: {
          name: [
            {
              required: true,
              message: '请输入组织机构名称',
              trigger: 'change',
            },
          ],
          parentId: [{ required: true, message: '请选择上级菜单', trigger: 'change' }],
        },
      },
    }
  },
  watch: {
    'formData.parentId'() {
      this.computedFullName()
    },
    'formData.name'() {
      this.computedFullName()
    },
  },
  methods: {
    computedFullName() {
      if (this.formData.parentId === '0') {
        this.formData.orgFullName = this.formData.name
        return
      }
      let parentOrg = this.dataList.find(it => it.id === this.formData.parentId)
      if (!parentOrg) {
        return ''
      }
      this.formData.orgFullName = parentOrg.orgFullName + '/' + this.formData.name
    },
    add(row, isCopyAdd) {
      this.$helper.createCsrfToken()
      if (row && row.id) {
        // 复制新增
        if (isCopyAdd) {
          this.formData = this.$utils.clone(row)
        } else {
          // 新增子节点
          this.formData.parentId = row.id
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
            .post('/auth/sys/org/op/insertOrUpdate', this.formData)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$emit('saveSuccess')
              this.formData.type = 2 // 还原type默认值
              // 待type watch事件执行完成重置表单，否则字段默认值重置有无
              this.$nextTick(() => {
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
  },
}
</script>
