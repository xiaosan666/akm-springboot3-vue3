<template>
  <!-- 修改手机号码 -->
  <akm-dialog :config="dialogConfig">
    <el-form ref="form" label-width="150px" :model="form" :rules="rules">
      <el-form-item v-if="step === 1" prop="oldPhoneCode" label="旧手机短信验证码：">
        <div class="msg-input-warp">
          <el-input v-model="form.oldPhoneCode" placeholder="短信验证码" clearable></el-input>
          <the-msg-button @click="getMsg"></the-msg-button>
        </div>
      </el-form-item>

      <el-form-item v-if="step === 2" label="新手机号码：" prop="newPhone">
        <div class="msg-input-warp">
          <el-input v-model="form.newPhone"></el-input>
          <the-msg-button @click="getMsgByPhone(form.newPhone)"></the-msg-button>
        </div>
      </el-form-item>

      <el-form-item v-if="step === 2" prop="newPhoneCode" label="新手机短信验证码：">
        <el-input v-model="form.newPhoneCode" placeholder="新手机号短信验证码" clearable></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="dialogConfig.visible = false">取 消</el-button>
      <el-button v-if="step === 1" type="success" @click="nextStep">下 一 步</el-button>
      <el-button v-if="step === 2" type="success" @click="--step">上 一 步</el-button>
      <el-button v-if="step === 2" type="primary" @click="confirm">确 定</el-button>
    </template>
  </akm-dialog>
</template>

<script>
import { useUserStore } from '@/stores/user'
import TheMsgButton from '@/views/common/TheMsgButton.vue'

export default {
  name: 'TheChangePhone',
  components: { TheMsgButton },
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '修改手机号码',
        width: 500,
      },

      form: {
        oldPhoneCode: '', // 旧手机号验证码
        newPhone: '', // 新手机号
        newPhoneCode: '', // 新手机号验证码
      },
      rules: {
        oldPhoneCode: [
          {
            required: true,
            message: '请输入当前账号绑定的手机号验证码',
            trigger: 'change',
          },
        ],
        newPhone: [
          {
            required: true,
            message: '请输入新手机号码',
            trigger: ['change', 'blur'],
          },
          {
            validator: this.$validate.elementValidator(
              this.$validate.regex.phone,
              '手机号码不正确'
            ),
            trigger: 'blur',
          },
        ],
        newPhoneCode: [
          {
            required: true,
            message: '请输入新手机号短信验证码',
            trigger: 'change',
          },
        ],
      },
      step: 1, // 步骤 ，1是验证旧手机号， 2是修改新手机号
    }
  },
  computed: {
    userInfo() {
      const userStore = useUserStore()
      return userStore.userInfo
    },
  },
  methods: {
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
    async getMsgByPhone(phone) {
      let errorMessage = []
      // 对部分表单字段进行校验
      this.$refs.form.validateField(['newPhone'], msg => {
        if (msg) {
          errorMessage.push(msg)
        }
      })
      if (errorMessage.length !== 0) {
        return
      }

      try {
        await this.$confirm(`短信验证码即将发送至${phone}手机号码上，是否继续？`, '确认信息', {
          confirmButtonText: '我要继续',
          cancelButtonText: '放弃修改',
        })

        await this.$api.getMsgByPhone(phone)
        this.$helper.successMessage('短信验证码发送成功，如未收到请检查手机号码或重新发送！')
      } catch (error) {
        console.error('发送短信验证码失败:', error)
      }
    },
    nextStep() {
      let errorMessage = []
      // 对部分表单字段进行校验
      this.$refs.form.validateField(['oldPhoneCode'], msg => {
        if (msg) {
          errorMessage.push(msg)
        }
      })
      if (errorMessage.length !== 0) {
        return
      }
      ++this.step
    },
    async confirm() {
      try {
        const valid = await this.$refs.form.validate()
        if (valid) {
          this.dialogConfig.loading = true
          await this.$http.post('/user/phone/change', this.form)
          await this.$helper.alert('手机号码修改成功，请重新登录')

          const userStore = useUserStore()
          await userStore.logout()
        }
      } catch (error) {
        console.error('修改手机号码失败:', error)
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
