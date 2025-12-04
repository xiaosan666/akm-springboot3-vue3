<template>
  <akm-table v-margin :config="tableConfig">
    <template #toolbar>
      <el-button type="primary">新增</el-button>
    </template>
    <template #column-op="{ row }">
      <akm-warn-btn @confirm="del(id)">删除</akm-warn-btn>
      <el-button type="primary" link @click="edit(row)">编辑</el-button>
    </template>
  </akm-table>
</template>
<script>
export default {
  name: 'DemoUser',
  data() {
    return {
      tableConfig: {
        id: 'akmTable', // 默认为'akmTable'，当页面出现多个表格，务必指定不同的id，
        loading: false, // 是否显示loading
        height: 'auto', // 表格高度
        autoComputedHeight: false, // 是否自动计算表格高度，设为true则当页面出现滚动条时，自动把滚动条加到表格上，表格最大高度将填充页面
        multipleSelect: true, // 是否显示checkbox复选框
        singleSelect: false, // 是否显示radio单选框
        hideIndexColumn: false, // 是否隐藏序号列，默认显示
        pagination: false, // 是否显示分页组件
        pageNum: 1, // 当前页码，当pagination为true有效
        pageSize: 10, // 每页大小，当pagination为true有效
        total: 0, // 总数，当pagination为true有效
        rowKey: 'id', // 行数据的 Key，用来优化 Table 的渲染，默认为id
        // 表格数据
        data: [
          { id: 1, username: 'zhagnsan', name: '张三', phone: '13012344321' },
          { id: 2, username: 'lisi', name: '李四', phone: '13012344321' },
        ],
        // 表格列
        columns: [
          { prop: 'username', label: '用户名' },
          { prop: 'name', label: '姓名' },
          { prop: 'phone', label: '手机号码' },
          { type: 'slot', prop: 'op', label: '操作' },
        ],
      },
    }
  },
  methods: {
    del() {
      this.$helper.alert('确认删除id：' + id)
    },
    edit(row) {
      this.$helper.alert('编辑：' + row.name)
    },
  },
}
</script>
