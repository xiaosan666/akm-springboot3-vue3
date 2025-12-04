<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <akm-form ref="form" :config="formConfig" :data="formData"></akm-form>
  </akm-dialog>
</template>
<script>
export default {
  name: 'TheUserAddEditDialog',
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 500,
      },
      formData: {
        id: '',
        username: '',
        realname: '',
        age: 18,
        birthday: '',
        idCard: '',
      },
      updateBeforeIdCard: '',
      formConfig: {
        items: [
          { prop: 'username', label: '用户名' },
          { prop: 'realname', label: '姓名' },
          {
            type: 'inputNumber',
            prop: 'age',
            label: '年龄',
            min: 0,
            max: 100,
          },
          {
            type: 'date',
            prop: 'birthday',
            label: '生日',
            valueFormat: 'yyyy/MM/dd',
          },
          { prop: 'idCard', label: '身份证号码' },
        ],
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            {
              validator: this.$validate.elementValidator(
                this.$validate.regex.username,
                '只能是字母或数字，4到16位字符'
              ),
              trigger: 'blur',
            },
          ],
          realname: [
            { required: true, message: '请输入真实姓名', trigger: 'blur' },
            {
              validator: this.$validate.elementValidator(
                this.$validate.regex.name,
                '只能是字母或中文，2到16位字符'
              ),
              trigger: 'blur',
            },
          ],
          idCard: [{ validator: this.validateIdCard, trigger: 'blur' }],
        },
      },
    }
  },
  methods: {
    open(row) {
      this.$helper.createCsrfToken()
      if (row && row.id) {
        this.updateBeforeIdCard = row.idCard
        this.formData = this.$utils.clone(row)
        this.dialogConfig.title = '编辑用户'
      } else {
        this.updateBeforeIdCard = ''
        this.formData.id = ''
        this.dialogConfig.title = '新增用户'
      }
      this.dialogConfig.visible = true
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.dialogConfig.loading = true
          this.$http
            .post('/auth/demo/user/open/op/insertOrUpdate', this.formData)
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
    // 校验身份证号码
    validateIdCard(rule, value, callback) {
      if (value === this.updateBeforeIdCard || this.$validate.regex.idCard.test(value)) {
        return callback()
      }
      return callback(new Error('请输入18位身份证号码'))
    },
  },
}
</script>

<style lang="scss" scoped></style>
