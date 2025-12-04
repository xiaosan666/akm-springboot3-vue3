<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <akm-form ref="form" :config="formConfig"></akm-form>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheResetPassword',
  data() {
    return {
      userId: '',
      dialogConfig: {
        visible: false,
        loading: false,
        title: '重置密码',
        width: 600,
      },
      formConfig: {
        data: {
          password: '',
        },
        items: [
          {
            type: 'input',
            prop: 'password',
            label: '密码',
          },
        ],
        rules: {
          password: [
            { required: true, message: '请输入密码', trigger: 'change' },
            {
              validator: this.$validate.elementValidator(
                this.$validate.regex.password,
                '密码8-20位，需同时包含数字、字母以及特殊字符（英文状态）!@#$%^&*()'
              ),
              trigger: 'change',
            },
          ],
        },
      },
    }
  },
  methods: {
    open(userId) {
      this.userId = userId
      this.dialogConfig.visible = true
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.dialogConfig.loading = true
          let password = this.formConfig.data.password
          this.$http
            .post('/auth/sys/user/op/updatePassword', {
              id: this.userId,
              newPassword: this.$security.sha256(password),
              textPass: this.$security.base64Encode(password),
            })
            .then(() => {
              this.$helper.successMessage('密码重置成功')
              this.$emit('saveSuccess')
              this.$refs.form.resetFields()
              this.dialogConfig.visible = false
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

<style lang="scss" scoped></style>
