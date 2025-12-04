<template>
  <div>
    <akm-form
      ref="form"
      :data="formData"
      :config="formConfig"
      @query="query"
      @reset="reset"
      @handleEvent="query"
    ></akm-form>

    <akm-table ref="table" :config="tableConfig">
      <template #toolbar>
        <el-button v-has="'sys_user_add'" type="primary" @click="addEdit">新增</el-button>
      </template>
      <template #column-enable="{ row }">
        <el-tag v-if="row.enable === 1" type="primary">启用</el-tag>
        <el-tag v-else type="danger">已停用</el-tag>
      </template>
      <template #column-op="{ row }">
        <el-button v-has="'sys_user_assign_role'" type="primary" link @click="updateRole(row)">
          分配角色
        </el-button>
        <el-button
          v-has="'sys_user_reset_password'"
          type="primary"
          link
          @click="resetPassword(row)"
        >
          重置密码
        </el-button>
        <el-dropdown>
          <el-button
            v-has="[
              'sys_user_role_play',
              'sys_user_reset_lock',
              'sys_user_edit',
              'sys_user_del',
              'sys_user_view_menu',
            ]"
            type="primary"
            link
          >
            更多菜单
            <el-icon><ArrowDown /></el-icon>
          </el-button>
          <template v-slot:dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click.prevent="viewMenu(row)">
                <el-button v-has="'sys_user_view_menu'" type="primary" link>查看用户权限</el-button>
              </el-dropdown-item>
              <el-dropdown-item @click.prevent="rolePlay(row)">
                <el-button v-has="'sys_user_role_play'" type="primary" link>扮演该用户</el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-tooltip content="用户登录密码错误5次将被锁定" placement="top-start">
                  <el-button
                    v-has="'sys_user_reset_lock'"
                    type="primary"
                    link
                    @click="resetLock(row)"
                  >
                    重置锁定状态
                  </el-button>
                </el-tooltip>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-tooltip content="用户长时间未登录账号过期" placement="top-start">
                  <el-button
                    v-has="'sys_user_reset_expired'"
                    type="primary"
                    link
                    @click="resetExpired(row)"
                  >
                    重置过期状态
                  </el-button>
                </el-tooltip>
              </el-dropdown-item>
              <el-dropdown-item @click.prevent="addEdit(row)">
                <el-button v-has="'sys_user_edit'" type="primary" link>编辑</el-button>
              </el-dropdown-item>
              <el-dropdown-item @click.prevent="del(row)">
                <el-button v-has="'sys_user_del'" type="primary" link>
                  <span style="color: #f56c6c">删除</span>
                </el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </akm-table>

    <the-user-add-edit ref="theUserAddEdit" @saveSuccess="query"></the-user-add-edit>

    <the-reset-password ref="theResetPassword"></the-reset-password>

    <the-user-update-role ref="theUserUpdateRole" @saveSuccess="query"></the-user-update-role>

    <the-user-menu-tree ref="theUseMenuTree"></the-user-menu-tree>
  </div>
</template>

<script>
import TheUserAddEdit from '@/views/sys/user/TheUserAddEdit.vue'
import TheResetPassword from '@/views/sys/user/TheResetPassword.vue'
import TheUserUpdateRole from '@/views/sys/user/TheUserUpdateRole.vue'
import TheUserMenuTree from '@/views/sys/user/TheUserMenuTree.vue'
import { useUserStore } from '@/stores/user'
import { usePermissionStore } from '@/stores/permission'
import { ArrowDown } from '@element-plus/icons-vue'

export default {
  name: 'SysUser',
  components: {
    TheUserMenuTree,
    TheUserUpdateRole,
    TheResetPassword,
    TheUserAddEdit,
    ArrowDown,
  },
  data() {
    return {
      formData: {
        tenantId: '',
        username: '',
        realname: '',
        orgName: '',
        roleNames: '',
        enable: '',
      },
      formConfig: {
        inline: true,
        showQueryInline: true,
        showBorder: true,
        items: [
          {
            type: 'select',
            prop: 'tenantId',
            label: '所属租户',
            options: [],
            event: 'change',
          },
          { type: 'input', prop: 'username', label: '用户名' },
          { type: 'input', prop: 'realname', label: '姓名' },
          { type: 'input', prop: 'orgName', label: '归属组织' },
          { type: 'input', prop: 'roleNames', label: '角色名称' },
          {
            type: 'dict',
            code: 'enable_status_op',
            prop: 'enable',
            label: '启用状态',
          },
        ],
      },

      tableConfig: {
        uri: '/auth/sys/user/view/findPage',
        autoComputedHeight: true,
        pagination: true,
        columns: [
          { prop: 'tenantName', label: '所属租户', showOverflowTooltip: true },
          { prop: 'username', label: '用户名' },
          { prop: 'realname', label: '姓名' },
          { prop: 'roleNames', label: '拥有角色', showOverflowTooltip: true },
          { prop: 'orgName', label: '归属组织', showOverflowTooltip: true },
          {
            type: 'slot',
            prop: 'enable',
            label: '启用状态',
            width: 100,
          },
          // {
          //   prop: 'expiredTime',
          //   label: '账号过期时间',
          //   width: 170,
          //   formatter: (row, column, cellValue) =>
          //     this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          // },
          {
            prop: 'lastPasswordChangeTime',
            label: '上次密码修改时间',
            width: 170,
            formatter: (row, column, cellValue) =>
              this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          },
          {
            prop: 'createTime',
            label: '创建时间',
            width: 170,
            formatter: (row, column, cellValue) =>
              this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          },
          { type: 'slot', prop: 'op', label: '操作' },
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
  mounted() {
    this.initQuery()
  },
  methods: {
    initQuery() {
      this.$api.fetchTenantList().then(list => {
        if (list.length > 0) {
          this.formConfig.items.find(item => item.prop === 'tenantId').options = list
          this.formData.tenantId = this.userInfo.tenantId
        }
        this.query()
      })
    },
    query() {
      this.tableConfig.pageNum = 1
      this.$refs.table.fetchTableData(this.formData)
    },
    reset() {
      this.$refs.form.resetFields()
      this.formData.tenantId = this.userInfo.tenantId
      this.query()
    },
    del(row) {
      this.$helper.confirm('确定删除【 ' + row.realname + ' 】？').then(() => {
        this.$http.post('/auth/sys/user/op/del', row.id).then(() => {
          this.$helper.successMessage('删除成功')
          this.query()
        })
      })
    },
    addEdit(row) {
      this.$refs.theUserAddEdit.open(row)
    },
    resetPassword(row) {
      this.$refs.theResetPassword.open(row.id)
    },
    viewMenu(row) {
      this.$refs.theUseMenuTree.open(row.id)
    },
    resetLock(row) {
      this.$http.post('/auth/sys/user/op/resetLock', row.username).then(() => {
        this.$helper.successMessage('重置锁定状态成功')
      })
    },
    resetExpired(row) {
      this.$http.post('/auth/sys/user/op/resetExpired', row.username).then(() => {
        this.$helper.successMessage('重置账号过期状态成功')
      })
    },
    filterRoleMethod(query, item) {
      return item.name.indexOf(query) > -1
    },
    updateRole(row) {
      this.$refs.theUserUpdateRole.open(row.id, row.tenantId)
    },
    rolePlay(row) {
      this.$helper.confirm('确定使用【 ' + row.realname + ' 】的身份访问系统？').then(() => {
        this.$http
          .post('/auth/op/rolePlay', {
            userId: row.id,
            clientType: import.meta.env.VITE_BASE_CLIENT_TYPE,
          })
          .then(token => {
            const userStore = useUserStore()
            const permissionStore = usePermissionStore()
            userStore.clearUserInfo()
            permissionStore.clearPermissionMenu()
            userStore.loginSuccess(token)
            // 发布页面刷新事件
            this.$eventBus.emit(this.$eventBus.keys.pageRefresh)
          })
      })
    },
  },
}
</script>
