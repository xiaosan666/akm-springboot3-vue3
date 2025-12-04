<template>
  <div class="recover-password-form">
    <el-form ref="formRef" :model="form" status-icon :rules="rules">
      <el-form-item prop="phone">
        <el-input v-model.trim="form.phone" placeholder="请输入手机号码" clearable>
          <template #prefix>
            <el-icon><Phone /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item class="captcha-wrap" prop="captcha">
        <el-input v-model="form.captcha" placeholder="图形验证码" clearable>
          <template #prefix>
            <el-icon><PictureFilled /></el-icon>
          </template>
          <template #append>
            <div v-loading="isCodeLoading" class="captcha" @click="getCaptcha">
              <img :src="captchaSrc" title="点击刷新" alt="点击刷新" />
            </div>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <div class="msg-input-warp">
          <el-input v-model="form.code" placeholder="手机短信验证码" clearable></el-input>
          <the-msg-button @click="getMsg"></the-msg-button>
        </div>
      </el-form-item>
      <el-form-item prop="newPassword">
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
      <el-form-item prop="confirmPassword">
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
      <el-form-item>
        <el-button type="primary" class="submit-button" :loading="loading" @click="submit">
          确认修改密码
        </el-button>
      </el-form-item>
      <div class="text-right">
        <el-link @click="switchFormHandle">返回登录页</el-link>
      </div>
    </el-form>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { Phone, PictureFilled } from '@element-plus/icons-vue'
import TheMsgButton from '@/views/common/TheMsgButton.vue'

export default {
  name: 'TheRecoverPasswordForm',
  components: {
    TheMsgButton,
    Phone,
    PictureFilled,
  },
  setup(props, { emit }) {
    const formRef = ref(null)
    const loading = ref(false)
    const isCodeLoading = ref(false)
    const captchaSrc = ref('')

    const form = reactive({
      phone: '',
      captcha: '',
      code: '',
      newPassword: '',
      confirmPassword: '',
      clientType: import.meta.env.VITE_BASE_CLIENT_TYPE,
      codeKey: '',
    })

    const pwdAgainCheck = (rule, value, callback) => {
      if (form.newPassword !== form.confirmPassword) {
        return callback(new Error('两次密码输入不一致！'))
      }
      return callback()
    }

    const rules = {
      phone: [
        { required: true, message: '请输入手机号码', trigger: 'change' },
        {
          validator: this.$validate.elementValidator(this.$validate.regex.phone, '手机号码不正确'),
          trigger: 'blur',
        },
      ],
      captcha: [{ required: true, message: '请输入图形验证码', trigger: 'change' }],
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
        { validator: pwdAgainCheck, trigger: 'blur' },
      ],
    }

    const switchFormHandle = () => {
      emit('switch-form')
    }

    const getCaptcha = () => {
      isCodeLoading.value = true
      this.$http
        .get('/auth/open/captcha')
        .then(res => {
          captchaSrc.value = res.imgBase64
          form.codeKey = res.codeKey
        })
        .finally(() => {
          isCodeLoading.value = false
        })
    }

    const getMsg = () => {
      const errorMessage = []
      // 对部分表单字段进行校验
      formRef.value?.this.$validateField(['phone', 'captcha'], msg => {
        msg && errorMessage.push(msg)
      })
      if (errorMessage.length !== 0) {
        this.$helper.errorMessage(errorMessage[0])
        return
      }
      // todo 发送获取短信验证码请求
      this.$.successMessage('短信验证码发送成功，如未收到请检查手机号码或重新发送！')
    }

    const submit = () => {
      formRef.value?.this.$validate(valid => {
        if (valid) {
          loading.value = true
          getCaptcha()
          //  todo 发送修改密码请求
          // http.post('', form)
          //   .then(() => {
          //     helper.alert('密码修改成功，请重新登录').then(() => {
          //       switchFormHandle()
          //     })
          //   })
          //   .finally(() => (loading.value = false))
        }
      })
    }

    onMounted(() => {
      getCaptcha()
    })

    return {
      formRef,
      form,
      rules,
      loading,
      isCodeLoading,
      captchaSrc,
      switchFormHandle,
      getCaptcha,
      getMsg,
      submit,
    }
  },
}
</script>

<style lang="scss" scoped>
.recover-password-form {
  .msg-input-warp {
    display: flex;
    button {
      margin-left: 12px;
    }
  }
  .submit-button {
    width: 100%;
    font-size: 16px;
  }
  .text-right {
    text-align: right;
  }
}

.captcha-wrap {
  :deep(.el-input-group__append) {
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
