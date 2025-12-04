<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <el-form ref="form" class="akm-form" label-width="100px">
      <el-form-item label="角色名称：">
        <el-input v-model="roleName" disabled></el-input>
      </el-form-item>
      <el-form-item label="权限范围：">
        <el-select v-model="dataScopeOrg">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-card v-show="dataScopeOrg === '20'">
      <akm-select-org-tree ref="akmSelectOrgTree"></akm-select-org-tree>
    </el-card>
  </akm-dialog>
</template>

<script>
import AkmSelectOrgTree from '@/views/common/akm-select-org-tree/index.vue'
export default {
  name: 'TheRoleDataScopeDialog',
  components: { AkmSelectOrgTree },
  data() {
    return {
      firstOpen: true, // 标记首次打开才去加载数据
      dialogConfig: {
        visible: false,
        loading: false,
        title: '分配权限',
        width: 500,
      },
      dataScopeOrg: '',
      roleName: '',
      options: [],

      role: {},
    }
  },
  methods: {
    open(role) {
      if (this.firstOpen) {
        this.firstOpen = false
        this.fetchDictData()
      }
      this.role = role
      this.dataScopeOrg = this.role.dataScopeOrg
      this.roleName = this.role.name
      this.dialogConfig.visible = true
      let orgIds = this.role.orgIds
      this.$nextTick(() => {
        let checkedIds = this.dataScopeOrg === '20' && orgIds ? orgIds.split(',') : []
        this.$refs.akmSelectOrgTree.setExpandedKeys(checkedIds)
        this.$refs.akmSelectOrgTree.setCheckedKeys(checkedIds)
      })
    },
    fetchDictData() {
      // 初始化页面需要的字典值
      this.$dict.getListByTypes(['data_scope_org']).then(res => {
        this.options = res
      })
    },
    confirm() {
      let orgIdList = []
      if (this.dataScopeOrg === '20') {
        orgIdList = this.$refs.akmSelectOrgTree.getCheckedKeys()
        if (orgIdList.length === 0) {
          return this.$message.warning('请选择分配数据！')
        }
      }
      this.dialogConfig.loading = true
      this.$http
        .post(`/auth/sys/role/op/updateOrgDataScopeByRoleId`, {
          dataScopeOrg: this.dataScopeOrg,
          orgIdList: orgIdList,
          roleId: this.role.id,
        })
        .then(() => {
          this.$helper.successMessage()
          this.$emit('confirmDataScopeOrg')
          this.dialogConfig.visible = false
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
  },
}
</script>
