<template>
  <!-- 修改密码 -->
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <el-form
      ref="form"
      :model="form"
      status-icon
      :rules="rules"
      label-width="140px"
      class="change-pwd-form"
    >
      <el-form-item prop="code" label="短信验证码：">
        <div class="msg-input-warp">
          <el-input v-model="form.code" placeholder="手机短信验证码" clearable></el-input>
          <the-msg-button @click="getMsg"></the-msg-button>
        </div>
      </el-form-item>
      <el-form-item prop="newPassword" label="新密码：">
        <el-input
          v-model="form.newPassword"
          type="password"
          placeholder="输入新密码"
          autocomplete="off"
          show-password
          onpaste="return false"
          oncopy="return false"
          oncut="return false"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword" label="再次输入新密码：">
        <el-input
          v-model="form.confirmPassword"
          type="password"
          placeholder="重复输入新密码"
          autocomplete="off"
          show-password
          onpaste="return false"
          oncopy="return false"
          oncut="return false"
          clearable
        ></el-input>
      </el-form-item>
    </el-form>
  </akm-dialog>
</template>

<script>
import { useUserStore } from '@/stores/user'
import TheMsgButton from '@/views/common/TheMsgButton.vue'

export default {
  name: 'TheChangePwd',
  components: { TheMsgButton },
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '修改密码',
        width: 500,
      },
      isCodeLoading: false,

      form: {
        code: '',
        newPassword: '',
        confirmPassword: '',
      },
      rules: {
        code: [
          {
            required: true,
            message: '请输入手机短信验证码',
            trigger: 'change',
          },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'change' },
          {
            validator: this.$validate.elementValidator(
              this.$validate.regex.password,
              '密码8-20位，需同时包含数字、字母以及特殊字符（英文状态）!@#$%^&*()'
            ),
            trigger: 'blur',
          },
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'change' },
          { validator: this.pwdAgainCheck, trigger: 'blur' },
        ],
      },
    }
  },
  computed: {
    userInfo() {
      const userStore = useUserStore()
      return userStore.userInfo
    },
  },
  methods: {
    // 重复密码验证
    pwdAgainCheck(rule, value, callback) {
      if (this.form.newPassword !== this.form.confirmPassword) {
        return callback(new Error('两次密码输入不一致！'))
      }
      return callback()
    },
    open() {
      this.dialogConfig.visible = true
    },
    async getMsg() {
      try {
        await this.$confirm(
          `短信验证码即将发送至${this.userInfo.username}手机号码上，是否继续？`,
          '确认信息',
          {
            confirmButtonText: '我要继续',
            cancelButtonText: '放弃修改',
          }
        )

        await this.$api.getMsg()
        this.$helper.successMessage('短信验证码发送成功，如未收到请检查手机号码或重新发送！')
      } catch (error) {
        console.error('发送短信验证码失败:', error)
      }
    },
    async confirm() {
      try {
        const valid = await this.$refs.form.validate()
        if (valid) {
          this.dialogConfig.loading = true
          await this.$http.post('/user/password/update', this.form)
          await this.$helper.alert('密码修改成功，请重新登录')

          const userStore = useUserStore()
          await userStore.logout()
        }
      } catch (error) {
        console.error('修改密码失败:', error)
      } finally {
        this.dialogConfig.loading = false
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.msg-input-warp {
  display: flex;
  button {
    margin-left: 12px;
  }
}
</style>

<style lang="scss">
.change-pwd-form {
  .el-form-item {
    margin-bottom: 30px;
  }
}
</style>
