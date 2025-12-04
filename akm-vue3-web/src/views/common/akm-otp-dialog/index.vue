<!--
otp验证
扫盲篇： https://juejin.cn/post/6946395179444076557
-->
<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <div class="akm-otp-dialog">
      <div class="sub-title">请打开Google Authenticator应用查看验证码</div>
      <el-input
        v-model="otpCode"
        placeholder="请输入验证码"
        :prefix-icon="Unlock"
        clearable
      ></el-input>
      <div class="button-how" @click="clickHow">如何获取验证码？</div>
      <div v-show="showHow" class="how-warp">
        <p>
          1.iPhone手机， 在App Store中搜索 Google
          Authenticator；android手机，在应用市场中搜索“谷歌身份验证器”，或搜索 Google
          Authenticator。
        </p>
        <p>
          2.使用数盾Google Authenticator应用扫描下方二维码
          <br />
          <span id="qrCode" class="img"></span>
        </p>
        <p>3.扫描成功即可获得验证码，下次进入Google Authenticator应用即可查看验证码</p>
      </div>
    </div>
  </akm-dialog>
</template>

<script>
import QRCode from 'qrcodejs2'
import { Unlock } from '@element-plus/icons-vue'

export default {
  name: 'AkmOtpDialog',
  components: {
    Unlock,
  },
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        confirmLoading: false,
        title: '请输入验证码',
        width: 500,
      },
      otpCode: '',
      btnCode: '',
      showHow: false,
      qrCode: '',
    }
  },
  methods: {
    // 打开dialog
    open(btnCode) {
      this.btnCode = btnCode
      this.dialogConfig.visible = true
    },
    // 关闭dialog
    close() {
      this.dialogConfig.visible = false
    },
    // dialog确定
    confirm() {
      if (!this.otpCode) {
        this.$helper.errorMessage('请输入验证码')
        return
      }
      if (!/^\d{6}$/.test(this.otpCode)) {
        this.$helper.errorMessage('验证码必须为6为纯数字')
        return
      }
      this.dialogConfig.confirmLoading = true
      this.$http
        .post('/share/public/biz/otp/op/verificationOtpCode', {
          otpCode: this.otpCode,
          btnCode: this.btnCode,
        })
        .then(() => {
          this.$helper.successMessage('验证通过，10分钟内有效')
          this.otpCode = ''
          this.$emit('otpSuccess')
          this.close()
        })
        .finally(() => {
          this.dialogConfig.confirmLoading = false
        })
    },
    clickHow() {
      if (this.qrCode) {
        this.showHow = !this.showHow
      } else {
        this.dialogConfig.loading = true
        this.$http
          .post('/share/public/biz/otp/view/getQrCodeData')
          .then(res => {
            this.showHow = true
            this.qrCode = new QRCode(document.getElementById('qrCode'), {
              text: res,
              width: 128,
              height: 128,
            })
          })
          .finally(() => {
            this.dialogConfig.loading = false
          })
      }
    },
  },
}
</script>

<style scoped lang="scss">
.akm-otp-dialog {
  .sub-title {
    font-size: 14px;
    color: #666;
    margin-bottom: 0.5em;
  }
  .button-how {
    color: blue;
    text-decoration: underline;
    cursor: pointer;
    margin-top: 0.5em;
  }
  .how-warp {
    p {
      font-size: 15px;
      margin: 10px 0;
      font-weight: bold;
    }
    .img {
      display: inline-block;
      margin-left: 1em;
      border: 1px solid #dcdfe6;
    }
  }
}
</style>
