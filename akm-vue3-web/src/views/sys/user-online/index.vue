<template>
  <div>
    <el-input
      v-model="searchContent"
      clearable
      placeholder="可关键字（用户名、姓名）模糊搜索"
      prefix-icon="el-icon-search"
      style="width: 280px"
      @clear="filterData"
      @keydown.enter.native="filterData"
    ></el-input>
    <el-button style="margin-left: 4px" type="primary" @click="filterData">搜 索</el-button>

    <akm-table ref="table" :config="tableConfig">
      <template #column-op="{ row }">
        <el-button type="primary" link @click="forcedOffline(row)">强制下线</el-button>
      </template>
    </akm-table>
  </div>
</template>

<script>
export default {
  name: 'OnlineUser',
  data() {
    return {
      searchContent: '',
      dataList: [],
      tableConfig: {
        rowKey: 'token',
        data: [],
        autoComputedHeight: true,
        pagination: false,
        columns: [
          { prop: 'username', label: '用户名' },
          { prop: 'realname', label: '姓名' },
          { prop: 'roleNames', label: '拥有角色', showOverflowTooltip: true },
          { type: 'slot', prop: 'op', label: '操作' },
        ],
      },
    }
  },
  created() {
    this.query()
  },
  methods: {
    query() {
      this.$http.post('/auth/op/onlineUserList').then(userList => {
        userList.forEach(user => {
          user.roleNames = user.roleList.map(it => it.name).join(',')
        })
        this.tableConfig.data = userList
      })
    },
    reset() {
      this.$refs.form.resetFields()
      this.formData.tenantId = this.userInfo.tenantId
      this.query()
    },
    filterData() {
      if (!this.searchContent) {
        this.dataList.length && (this.tableConfig.data = this.dataList)
        return
      }
      this.dataList = this.$utils.clone(this.tableConfig.data)
      this.tableConfig.data = this.dataList.filter(item => {
        return (
          (item.username && item.username.includes(this.searchContent)) ||
          (item.realname && item.realname.includes(this.searchContent)) ||
          (item.roleNames && item.roleNames.includes(this.searchContent))
        )
      })
    },
    forcedOffline(row) {
      this.$helper.confirm('确定强制【 ' + row.realname + ' 】下线吗？').then(() => {
        this.$http.post('/auth/op/forcedOffline', row.token).then(() => {
          this.$helper.successMessage('删除成功')
          this.query()
        })
      })
    },
  },
}
</script>
