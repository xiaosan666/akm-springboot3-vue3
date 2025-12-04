<template>
  <div>
    <akm-form
      ref="form"
      :config="formConfig"
      :data="formData"
      @query="query"
      @reset="reset"
    ></akm-form>

    <akm-table
      :config="tableConfig"
      @pageNumChange="onPageNumChange"
      @pageSizeChange="onPageSizeChange"
      @sort-change="onSortChange"
    >
      <template #toolbar>
        <el-button
          v-has="'sys_exception_log_export'"
          type="primary"
          :loading="loading"
          @click="excelExport"
        >
          导出
        </el-button>
      </template>
      <el-table-column
        v-if="has('sys_exception_log_detail')"
        class-name="op-buttons"
        label="操作"
        width="80"
        fixed="right"
      >
        <template v-slot="{ row }">
          <el-button type="primary" link @click="view(row)">查看详情</el-button>
        </template>
      </el-table-column>
    </akm-table>
  </div>
</template>

<script>
export default {
  name: 'ExceptionLog',
  data() {
    return {
      loading: false,

      formData: {
        clientType: '',
        requestId: '',
        apiDescription: '',
        uri: '',
        method: '',
        userId: '',
        username: '',
        name: '',
        ip: '',
        dateTimeRangeArr: [],
        startTimeBegin: '',
        startTimeEnd: '',
      },
      formConfig: {
        inline: true,
        showQuery: true,
        showBorder: true,
        showMoreBtn: true,
        items: [
          {
            type: 'dict',
            code: 'client_type',
            prop: 'clientType',
            label: '应用类型',
          },
          { type: 'input', prop: 'requestId', label: '请求id' },
          { type: 'input', prop: 'apiDescription', label: '操作描述' },
          { type: 'input', prop: 'uri', label: 'uri' },
          {
            type: 'dict',
            code: 'http_method',
            prop: 'method',
            label: 'method',
          },
          { type: 'input', prop: 'userId', label: '用户id' },
          { type: 'input', prop: 'username', label: '用户名' },
          { type: 'input', prop: 'name', label: '用户姓名' },
          { type: 'input', prop: 'ip', label: 'IP地址' },
          {
            type: 'datetimerange',
            prop: 'dateTimeRangeArr',
            label: '请求时间',
            className: 'width2X',
          },
        ],
      },

      tableConfig: {
        data: [],
        loading: false,
        autoComputedHeight: true,
        pagination: true,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        orderBy: '',
        columns: [
          {
            prop: 'clientType',
            label: '应用类型',
            width: 100,
            formatter: (row, column, cellValue) =>
              this.$dict.translateNameByTypeAndValue('client_type', cellValue),
          },
          { prop: 'requestId', label: '请求id' },
          { prop: 'apiDescription', label: '操作描述' },
          { prop: 'uri', label: 'uri' },
          { prop: 'method', label: 'method', width: 80 },
          { prop: 'userId', label: '用户id', width: 110 },
          { prop: 'username', label: '用户名', width: 120 },
          { prop: 'name', label: '用户姓名', width: 110 },
          {
            prop: 'startTime',
            label: '请求时间',
            width: 110,
            sortable: 'custom',
            formatter: (row, column, cellValue) =>
              this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          },
          { prop: 'ip', label: '请求IP', width: 120 },
          {
            prop: 'spendTime',
            label: '消耗时间/毫秒',
            width: 140,
            sortable: 'custom',
          },
        ],
      },
    }
  },
  created() {
    this.fetchTableData()
  },
  methods: {
    query() {
      this.tableConfig.pageNum = 1
      this.fetchTableData()
    },
    reset() {
      this.$refs.form.resetFields()
      this.query()
    },
    fetchTableData() {
      let config = this.tableConfig
      config.loading = true
      this.setBeginAndEndTime(this.formData.dateTimeRangeArr)
      this.$http
        .post('/auth/sys/log/view/exception/findPage', {
          pageNum: config.pageNum,
          pageSize: config.pageSize,
          orderBy: config.orderBy,
          condition: this.formData,
        })
        .then(res => {
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
    // 根据日期范围数组设置表单开始、结束时间查询条件
    setBeginAndEndTime(arr) {
      let hasDate = arr && arr.length === 2
      let beginDate = hasDate ? new Date(arr[0]).getTime() : ''
      let endDate = hasDate ? new Date(arr[1]).getTime() : ''
      this.formData.startTimeBegin = beginDate
      this.formData.startTimeEnd = endDate
    },
    onSortChange(sortData) {
      let prop = sortData.prop
      let order = sortData.order === 'descending' ? 'desc' : 'asc'
      this.tableConfig.orderBy = `${prop} ${order}`
      this.query()
    },
    view(row) {
      this.$router.push({
        name: 'ExceptionLogView',
        params: { id: row.id },
      })
    },
    onStartTimeRangeChange(value) {
      this.formData.startTimeBegin = new Date(value[0]).getTime()
      this.formData.startTimeEnd = new Date(value[1]).getTime()
      this.query()
    },
    excelExport() {
      this.loading = true
      this.$http
        .postResponseBlob('/auth/sys/log/op/exception/export', this.formData)
        .then(blob => {
          const time = this.$utils.formatDate(new Date(), 'yyyy-MM-dd_HH:mm')
          this.$utils.downloadBlob(blob, '异常日志列表_' + time + '.xlsx')
        })
        .finally(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style scoped></style>
