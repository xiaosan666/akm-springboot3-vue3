<template>
  <div>
    <div class="page-user-center">
      <h3 class="akm-section-title">账号信息</h3>
      <p class="row">
        <i>姓名：</i>
        {{ userInfo.realname }}
      </p>
      <p class="row">
        <i>手机号码：</i>
        {{ userInfo.username }}
        <span v-margin-left>
          <el-button type="primary" @click="openChangePhoneDialog">更换手机号码</el-button>
          <el-button type="primary" @click="openChangePwdDialog">修改密码</el-button>
        </span>
      </p>
      <akm-table v-margin-top :config="tableConfig"></akm-table>
      <the-change-phone ref="changePhoneDialog"></the-change-phone>
      <the-change-pwd ref="changePwdDialog"></the-change-pwd>
    </div>
    <!-- 客服热线 -->
    <the-customer-service></the-customer-service>
  </div>
</template>

<script>
import { useUserStore } from '@/stores/user'
import { usePermissionStore } from '@/stores/permission'
import TheChangePhone from './TheChangePhone.vue'
import TheChangePwd from './TheChangePwd.vue'
import TheCustomerService from './TheCustomerService.vue'

export default {
  name: 'UserCenter',
  components: {
    TheCustomerService,
    TheChangePhone,
    TheChangePwd,
  },
  data() {
    return {
      tableConfig: {
        data: [],
        columns: [
          { prop: 'name', label: '角色名称' },
          { prop: 'remark', label: '备注' },
        ],
      },
    }
  },
  computed: {
    currentRoleId() {
      const permissionStore = usePermissionStore()
      return permissionStore.currentRoleId
    },
    userInfo() {
      const userStore = useUserStore()
      return userStore.userInfo
    },
  },
  watch: {
    userInfo: {
      handler(newUserInfo) {
        let roleList = newUserInfo.roleList
        if (roleList && roleList.length > 0) {
          this.tableConfig.data = roleList
        }
      },
      immediate: true,
    },
  },
  methods: {
    openChangePhoneDialog() {
      this.$refs.changePhoneDialog.open()
    },
    openChangePwdDialog() {
      this.$refs.changePwdDialog.open()
    },
  },
}
</script>

<style lang="scss" scoped>
.page-user-center {
  padding: 30px 50px;
  .row {
    display: flex;
    align-items: center;
    font-size: 18px;
    i {
      font-style: normal;
      font-size: 16px;
      width: 90px;
      padding: 2px;
    }
  }
}
</style>
