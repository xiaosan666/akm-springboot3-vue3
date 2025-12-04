<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <el-transfer
      v-model="roleIds"
      class="user-role-transfer"
      :titles="['待选择角色列表 ', '已选择角色列表 ']"
      filterable
      :data="data"
      :props="{
        key: 'id',
        label: 'name',
      }"
    ></el-transfer>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheUserUpdateRole',
  data() {
    return {
      userId: '', // 待分配角色的用户id
      tenantId: '', // 用户所属租户, 只允许分配相同租户的角色
      roleIds: [], // 用户最新分配的角色id
      oldRoleIds: [], // 暂存用户本来就有的角色id
      data: [],
      dialogConfig: {
        visible: false,
        loading: false,
        title: '分配角色',
        width: 840,
      },
    }
  },
  methods: {
    open(userId, tenantId) {
      this.userId = userId
      this.tenantId = tenantId
      this.fetchAllRole(tenantId)
      // 获取userId已分配角色列表
      this.dialogConfig.visible = true
      this.dialogConfig.loading = true
      this.$http
        .post('/auth/sys/role/view/findRoleByUser', userId)
        .then(res => {
          let roleIds = res.map(role => role.id)
          this.roleIds = roleIds
          this.oldRoleIds = roleIds
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
    fetchAllRole() {
      this.$http
        .post('/auth/sys/role/view/findAll', {
          enabled: 1,
          tenantId: this.tenantId,
        })
        .then(res => {
          if (res.length === 0) {
            this.$helper.warningMessage('当前租户没有角色，请前往角色管理页面添加')
          } else {
            res.forEach(item => {
              item.name = item.name + '（' + item.tenantName + '）'
            })
          }
          this.data = res
        })
    },
    filterRoleMethod(query, item) {
      return item.name.indexOf(query) > -1
    },
    confirm() {
      if (this.$utils.assertArraysEqual(this.oldRoleIds, this.roleIds)) {
        this.$helper.warningMessage('角色未变更！')
        return
      }
      this.dialogConfig.loading = true
      this.$http
        .post('/auth/sys/user/op/updateRoleByUserId', {
          userId: this.userId,
          roleIdList: this.roleIds,
        })
        .then(() => {
          this.$helper.successMessage()
          this.$emit('saveSuccess')
          this.dialogConfig.visible = false
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
  },
}
</script>

<style lang="scss">
.user-role-transfer {
  .el-transfer-panel {
    width: 300px;
  }
}
</style>
