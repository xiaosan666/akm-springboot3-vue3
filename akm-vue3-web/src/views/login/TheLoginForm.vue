<template>
  <div class="login-form" @keydown.enter="submit">
    <el-form ref="form" :model="form" status-icon :rules="rules">
      <el-form-item prop="username">
        <el-input v-model.trim="form.username" placeholder="请输入账号" clearable>
          <template v-slot:prepend>
            <el-icon><User /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入密码"
          autocomplete="off"
          show-password
          onpaste="return false"
          oncopy="return false"
          oncut="return false"
          clearable
        >
          <template v-slot:prepend>
            <el-icon><Unlock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item class="captcha-wrap" prop="code">
        <el-input v-model="form.code" placeholder="验证码" clearable>
          <template v-slot:prepend>
            <el-icon><PictureIcon /></el-icon>
          </template>
          <template v-slot:append>
            <div v-loading="isCodeLoading" class="captcha" @click="getCaptcha">
              <img :src="captchaSrc" title="点击刷新" alt="点击刷新" />
            </div>
          </template>
        </el-input>
      </el-form-item>
      <el-row class="remember-me-warp">
        <el-col :span="12">
          <el-checkbox v-model="rememberMe" @change="rememberMeHandle">记住用户名</el-checkbox>
        </el-col>
        <el-col :span="12">
          <div v-text-right>
            <el-link @click="switchFormHandle">忘记密码？</el-link>
          </div>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" class="submit-button" :loading="loading" @click="submit">
          登 录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user'
import { usePermissionStore } from '@/stores/permission'
import { User, Unlock, Picture as PictureIcon } from '@element-plus/icons-vue'
export default {
  name: 'TheLoginForm',
  emits: ['switch-form'],
  components: {
    User,
    Unlock,
    PictureIcon,
  },
  data() {
    return {
      loading: false,
      isCodeLoading: false,
      rememberMe: false,
      captchaSrc: '', // 验证码动态url
      form: {
        username: '18612341234',
        password: 'Akm@3723',
        clientType: import.meta.env.VITE_BASE_CLIENT_TYPE,
        code: '',
        codeKey: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'change' },
          {
            validator: this.$validate.elementValidator(
              this.$validate.regex.username,
              '只能是字母或数字，4到16位字符'
            ),
            trigger: 'blur',
          },
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'change' }],
      },
    }
  },
  created() {
    // 监听登录是否需要更新密码事件
    this.$eventBus.on(this.$eventBus.keys.updatePassword, () => {
      this.switchFormHandle()
    })
    this.getCaptcha()
    this.setUsername()
  },
  methods: {
    switchFormHandle() {
      this.$emit('switch-form')
    },
    setUsername() {
      let username = this.$localStorage.get(this.$localStorage.keys.rememberMe)
      if (username) {
        this.rememberMe = true
      }
      this.form.username = this.form.username || username
    },
    rememberMeHandle() {
      if (this.rememberMe) {
        this.$localStorage.set(this.$localStorage.keys.rememberMe, this.form.username)
      } else {
        this.$localStorage.remove(this.$localStorage.keys.rememberMe)
      }
    },
    async getCaptcha() {
      this.isCodeLoading = true
      try {
        const res = await this.$api.fetchCaptcha()
        this.captchaSrc = res.imgBase64
        this.form.codeKey = res.codeKey
      } finally {
        this.isCodeLoading = false
      }
    },
    async submit() {
      try {
        const valid = await this.$refs.form.validate()
        if (valid) {
          this.loading = true
          const userStore = useUserStore()
          userStore.clearUserInfo()
          const permissionStore = usePermissionStore()
          permissionStore.clearPermissionMenu()

          // 替换掉所有空格
          this.form.code = this.form.code.replace(/\s+/g, '')

          // 登陆参数属于敏感数据，参数加密处理
          let data = this.$utils.clone(this.form)
          data.password = this.$security.sha256(data.password)

          const token = await this.$http.post('/auth/open/login', data)
          await userStore.loginSuccess(token)
        }
      } catch (error) {
        this.$helper.errorMessage(error.message || '登录失败')
      } finally {
        this.loading = false
        this.getCaptcha()
        this.rememberMeHandle()
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.login-form {
  .submit-button {
    width: 100%;
    font-size: 16px;
  }
  .remember-me-warp {
    margin-bottom: 16px;
  }
}
</style>
<style lang="scss">
.captcha-wrap {
  margin-bottom: 20px;
  .el-input__inner {
    font-size: 18px;
    letter-spacing: 2px;
  }
  .el-input-group__append {
    padding: 0 2px;
    background: #fff;
  }
  .captcha {
    width: 70px;
    height: 30px;
    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
