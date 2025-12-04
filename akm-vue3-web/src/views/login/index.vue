<template>
  <div class="index-page">
    <div class="form-warp">
      <div class="title">
        {{ showLoginForm ? '登录' : '修改密码' }}
      </div>
      <the-login-form v-if="showLoginForm" @switch-form="switchFormHandle"></the-login-form>
      <the-recover-password-form
        v-if="!showLoginForm"
        @switch-form="switchFormHandle"
      ></the-recover-password-form>
    </div>
  </div>
</template>

<script>
import TheLoginForm from './TheLoginForm.vue'
import TheRecoverPasswordForm from './TheRecoverPasswordForm.vue'

export default {
  components: {
    TheLoginForm,
    TheRecoverPasswordForm,
  },
  data() {
    return {
      showLoginForm: true,
    }
  },
  methods: {
    switchFormHandle() {
      this.showLoginForm = !this.showLoginForm
    },
  },
}
</script>

<style lang="scss">
.form-warp {
  .el-input__inner {
    font-size: 15px;
  }
}
</style>

<style lang="scss" scoped>
.index-page {
  display: flex;
  height: 100%;
  justify-content: center;
  align-items: center;
  background-color: #efefef;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: url('@/assets/login/bg4.jpg') center / 100% 100% no-repeat;
    filter: blur(4px) opacity(0.8) brightness(0.8);
  }

  .form-warp {
    position: relative;
    z-index: 1;
    padding: 50px;
    border-radius: 10px;
    background-color: rgba(255, 255, 255, 0.95);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 400px;

    .title {
      border-bottom: 1px solid var(--el-border-color);
      font-size: 20px;
      padding-bottom: 12px;
      margin-bottom: 20px;
      color: var(--el-text-color-primary);
    }
  }
}
</style>
