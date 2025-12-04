<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <div style="display: flex">
      <el-input
        v-model="query.searchContent"
        placeholder="可关键词（用户名、姓名）模糊搜索；可多个姓名逗号分割搜索"
        :prefix-icon="SearchIcon"
        clearable
        @change="fetchTableData"
        @keydown.enter="fetchTableData"
      ></el-input>
      <el-button type="primary" style="margin-left: 4px" @click="fetchTableData">搜 索</el-button>
    </div>
    <akm-table
      ref="table"
      :config="tableConfig"
      @pageNumChange="onPageNumChange"
      @pageSizeChange="onPageSizeChange"
    ></akm-table>
  </akm-dialog>
</template>

<script>
import { Search as SearchIcon } from '@element-plus/icons-vue'

let DefaultQuery = { enable: 1, searchContent: '' }
export default {
  name: 'AkmSelectUserDialog',
  components: {
    SearchIcon,
  },
  props: {
    singleSelect: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      dialogParamStr: '', // 用来记录dialog open参数，用于判断参数是否改变
      // 查询参数
      query: { ...DefaultQuery },
      dialogConfig: {
        visible: false,
        loading: false,
        title: '选择人员',
      },
      tableDisableIds: [], // 需要禁用的id数组(原来已经选中的数据id列表)
      tableConfig: {
        id: 'TheSelectUserDialogTableId',
        data: [],
        loading: false,
        multipleSelect: !this.singleSelect,
        singleSelect: this.singleSelect,
        checkOnRowClick: true,
        height: 400,
        pagination: true,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        columns: [
          { prop: 'tenantName', label: '所属租户', showOverflowTooltip: true },
          { prop: 'username', label: '用户名' },
          { prop: 'realname', label: '姓名' },
          { prop: 'roleNames', label: '拥有角色', showOverflowTooltip: true },
          {
            prop: 'createTime',
            label: '创建时间',
            width: 170,
            formatter: (row, column, cellValue) =>
              this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          },
        ],
      },
      tableSelectedList: [],
    }
  },
  methods: {
    // 打开dialog
    open(disableIds = [], query = {}) {
      if (this.dialogParamIsChange(disableIds, query)) {
        this.setQuery(query)
        this.tableDisableIds = disableIds
        this.fetchTableData()
      }
      this.dialogConfig.visible = true
    },
    // 关闭dialog
    close() {
      this.dialogConfig.visible = false
    },
    // dialog确定
    confirm() {
      let tableSelectedList = this.$refs.table.getSelected()
      if (tableSelectedList.length === 0) {
        this.$helper.warningMessage('请至少选择 1 条数据！')
        return
      }
      this.$emit('confirm', tableSelectedList)
      this.close()
    },
    // 清空dialog状态
    clear() {
      this.dialogParamStr = ''
      this.setQuery() // 清空查询条件
      this.tableConfig.pageNum = 1
      this.tableConfig.pageSize = 10
      this.$refs.table.clearSelected()
    },
    fetchTableData() {
      let config = this.tableConfig
      config.loading = true
      this.$http
        .post('/auth/sys/user/view/findPage', {
          pageNum: config.pageNum,
          pageSize: config.pageSize,
          condition: this.query,
        })
        .then(res => {
          res.list.forEach(item => {
            item._disabled = this.tableDisableIds.includes(item.id)
          })
          config.data = res.list
          config.total = res.total
        })
        .finally(() => {
          config.loading = false
        })
    },
    onPageNumChange(pageNum) {
      this.tableConfig.pageNum = pageNum
      this.fetchTableData()
    },
    onPageSizeChange(pageSize) {
      this.tableConfig.pageSize = pageSize
      this.fetchTableData()
    },
    // 设置查询参数
    setQuery(params) {
      this.query = { ...DefaultQuery, ...params }
    },
    dialogParamIsChange(disableIds, params) {
      let newParams = JSON.stringify(disableIds) + JSON.stringify(params)
      // dialogParamStr为空表示第一次打开dialog
      let isChange = !this.dialogParamStr || this.dialogParamStr !== newParams
      this.dialogParamStr = newParams
      return isChange
    },
  },
}
</script>
