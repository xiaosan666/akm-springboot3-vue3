<template>
  <el-button type="primary" :disabled="disable" @click="onClick">
    {{ buttonText }}
  </el-button>
</template>

<script>
export default {
  name: 'TheMsgButton',
  data() {
    return {
      seconds: 60, // 获取短信按钮禁用倒计时
      buttonText: '获取验证码',
      disable: false,
    }
  },
  mounted() {
    let seconds = this.$localStorage.get(this.$localStorage.keys.smsCountdown)
    if (seconds !== null) {
      this.countDown(Number(seconds))
    }
  },
  methods: {
    onClick() {
      this.countDown(this.seconds)
      this.$emit('click')
    },
    countDown(initSeconds) {
      let seconds = initSeconds
      this.disable = true
      this.buttonText = `验证码(${seconds})`
      let intervalNumber = setInterval(() => {
        this.buttonText = `验证码(${--seconds})`
        this.$localStorage.set(this.$localStorage.keys.smsCountdown, seconds)
        if (seconds === 0) {
          this.buttonText = '获取验证码'
          this.disable = false
          clearInterval(intervalNumber)
          this.$localStorage.remove(this.$localStorage.keys.smsCountdown)
        }
      }, 1000)
    },
  },
}
</script>
