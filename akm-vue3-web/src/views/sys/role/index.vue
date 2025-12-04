<template>
  <div>
    <akm-form
      ref="form"
      :config="formConfig"
      :data="formData"
      @handleEvent="query"
      @query="query"
      @reset="reset"
    ></akm-form>
    <akm-table :config="tableConfig" @selectedChange="onSelectedChange" @row-click="onRowClick">
      <template #toolbar>
        <el-button
          v-has="'sys_role_del'"
          :disabled="!selectedList.length"
          type="danger"
          @click="batchDel"
        >
          删除
        </el-button>
        <el-button v-has="'sys_role_add'" type="primary" @click="addEdit">新增</el-button>
      </template>
      <template #column-enable="{ row }">
        <el-tag v-if="row.enable === 1" type="primary">启用</el-tag>
        <el-tag v-else type="danger">已停用</el-tag>
      </template>
      <template #column-op="{ row }">
        <akm-warn-btn v-has="'sys_role_del'" @confirm="del([row.id])">删除</akm-warn-btn>
        <el-button v-has="'sys_role_edit'" type="primary" link @click="addEdit(row)">
          编辑
        </el-button>
        <el-button v-has="'sys_role_assign_menu'" type="primary" link @click="setMenu(row)">
          功能权限
        </el-button>
        <el-button v-has="'sys_role_data_scope'" type="primary" link @click="setDataScope(row)">
          数据权限
        </el-button>
      </template>
    </akm-table>
    <the-role-add-edit ref="theRoleAddEdit" @saveSuccess="fetchTableData"></the-role-add-edit>
    <the-role-Menu-tree-dialog
      ref="menuTreeDialog"
      @confirm="fetchTableData"
    ></the-role-Menu-tree-dialog>
    <the-role-data-scope-dialog
      ref="theRoleDataScopeDialog"
      @confirmDataScopeOrg="fetchTableData"
    ></the-role-data-scope-dialog>
  </div>
</template>

<script>
import TheRoleAddEdit from '@/views/sys/role/TheRoleAddEdit.vue'
import TheRoleMenuTreeDialog from '@/views/sys/role/TheRoleMenuTreeDialog.vue'
import TheRoleDataScopeDialog from '@/views/sys/role/TheRoleDataScopeDialog.vue'
import { useUserStore } from '@/stores/user'

export default {
  name: 'SysRole',
  components: {
    TheRoleDataScopeDialog,
    TheRoleMenuTreeDialog,
    TheRoleAddEdit,
  },
  data() {
    return {
      currentFormData: null,
      selectedList: [],

      formData: {
        tenantId: '',
        name: '',
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
          { type: 'input', prop: 'name', label: '角色名称' },
          { type: 'input', prop: 'code', label: '角色编码' },
          {
            type: 'dict',
            code: 'enable_status',
            prop: 'enable',
            label: '启用状态',
          },
        ],
      },

      tableConfig: {
        data: [],
        loading: false,
        multipleSelect: true,
        pagination: false,
        columns: [
          { prop: 'tenantName', label: '所属租户' },
          { prop: 'name', label: '角色名称' },
          { prop: 'code', label: '角色编码' },
          { prop: 'dataScopeOrgName', label: '数据权限' },
          { prop: 'remark', label: '备注', showOverflowTooltip: true },
          { prop: 'orders', label: '排序（倒序）', width: 110 },
          {
            type: 'slot',
            prop: 'enable',
            label: '启用状态',
            width: 100,
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
  created() {
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
      this.fetchTableData()
    },
    reset() {
      this.$refs.form.resetFields()
      this.formData.tenantId = this.userInfo.tenantId
      this.query()
    },
    fetchTableData() {
      this.tableConfig.loading = true
      this.$http
        .post('/auth/sys/role/view/findAll', this.formData)
        .then(list => {
          this.tableConfig.data = list
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
    onSelectedChange(selectedList) {
      this.selectedList = selectedList
    },
    addEdit(row) {
      this.$refs.theRoleAddEdit.open(row)
    },
    batchDel() {
      let list = this.selectedList
      this.$helper
        .confirm(`即将删除${list.length}条记录，确定删除吗？`)
        .then(() => {
          this.del(list.map(item => item.id))
        })
        .catch(() => {})
    },
    del(ids) {
      this.tableConfig.loading = true
      this.$http
        .post('/auth/sys/role/op/batchDel', ids)
        .then(() => {
          this.$helper.successMessage('删除成功')
          this.fetchTableData()
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
    onRowClick(row) {
      if (this.currentFormData) {
        this.currentFormData = this.$utils.clone(row)
      }
    },
    setMenu(row) {
      this.$refs.menuTreeDialog.open(row)
    },
    setDataScope(row) {
      this.$refs.theRoleDataScopeDialog.open(row)
    },
  },
}
</script>

<style lang="scss" scoped></style>
