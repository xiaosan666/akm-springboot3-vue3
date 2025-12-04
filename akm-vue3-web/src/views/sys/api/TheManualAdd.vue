<template>
  <div>
    <akm-table ref="table" :config="tableConfig">
      <template #toolbar>
        <div class="akm-flex-space-between">
          <div style="display: flex">
            <el-input
              v-model="searchContent"
              placeholder="可关键字（名称、uri）模糊搜索"
              prefix-icon="el-icon-search"
              clearable
              style="width: 280px"
              @keydown.enter.native="filterData"
              @clear="filterData"
            ></el-input>
            <el-button type="primary" style="margin-left: 4px" @click="filterData">搜 索</el-button>
          </div>
          <el-button v-has="'sys_api_add'" type="primary" @click="add">新增接口</el-button>
        </div>
      </template>
      <template #column-type="{ row }">
        <el-tag v-if="row.type === 1" type="info">目录</el-tag>
        <el-tag v-if="row.type === 2" type="success">接口</el-tag>
      </template>
      <template #column-op="{ row }">
        <akm-warn-btn v-has="'sys_api_del'" @confirm="del(row)">删除</akm-warn-btn>
        <el-button v-has="'sys_api_edit'" type="primary" link @click="edit(row)">编辑</el-button>
        <el-button v-has="'sys_api_add'" type="primary" link @click="add(row, false)">
          添加子节点
        </el-button>
        <el-button v-has="'sys_api_add'" type="primary" link @click="add(row, true)">
          复制新增
        </el-button>
      </template>
    </akm-table>

    <the-api-add-edit-dialog
      ref="addEditDialog"
      :menuOptions="menuOptions"
      @saveSuccess="fetchTableData"
    ></the-api-add-edit-dialog>
  </div>
</template>

<script>
import TheApiAddEditDialog from '@/views/sys/api/TheApiAddEditDialog.vue'

export default {
  name: 'TheManualAdd',
  components: { TheApiAddEditDialog },
  data() {
    return {
      searchContent: '',
      tableConfig: {
        data: [],
        loading: false,
        columns: [
          { prop: 'name', label: '名称' },
          { prop: 'uri', label: 'uri' },
          {
            type: 'slot',
            prop: 'type',
            label: '类型',
            width: 100,
          },
          { prop: 'remark', label: '备注' },
          { prop: 'orders', label: '排序', width: 100 },
          { type: 'slot', prop: 'op', label: '操作', width: 260 },
        ],
      },

      rootMenu: {
        id: '0',
        name: '根目录',
        disabled: true,
      },
      menuOptions: [],
      list: [],
    }
  },
  created() {
    this.fetchTableData()
  },
  methods: {
    filterData() {
      if (!this.searchContent) {
        this.tableConfig.data = this.$utils.listToTree(this.list, '0')
        // 合并
        this.tableConfig.data.forEach(item => {
          this.$refs.table.$refs.akmTable.toggleRowExpansion(item, false)
        })
        return
      }
      let filterList = this.list.filter(item => {
        return (
          (item.name && item.name.includes(this.searchContent)) ||
          (item.uri && item.uri.includes(this.searchContent))
        )
      })
      // 找到过滤后数据的父级，用于重新组装树
      let newList = filterList.reduce((pre, item) => {
        let parentList = this.getParentIds(item.id)
          .filter(id => !pre.some(preItem => preItem.id === id))
          .map(id => {
            return this.list.find(listItem => listItem.id === id)
          })
        return pre.concat(parentList)
      }, filterList)
      this.tableConfig.data = this.$utils.listToTree(newList, '0')
      // 展开
      this.$nextTick(() => {
        newList.forEach(item => {
          this.$refs.table.$refs.akmTable.toggleRowExpansion(item, true)
        })
      })
    },
    // 获取节点的所有父节点
    getParentIds(nodeId, arr = []) {
      let node = this.list.find(item => item.id === nodeId)
      if (node && node.parentId !== '0') {
        arr.push(node.parentId)
        this.getParentIds(node.parentId, arr)
      }
      return arr
    },
    fetchTableData() {
      this.tableConfig.loading = true
      this.$http
        .post('/auth/sys/api/view/findAll', {})
        .then(list => {
          this.list = list
          let tree = this.$utils.listToTree(list)
          this.tableConfig.data = tree
          this.menuOptions = [this.rootMenu, ...tree]
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
    del(row) {
      if (row.children && row.children.length > 0) {
        let list = this.$utils.treeToList(row)
        this.$helper
          .confirm(`即将删除该节点及其子节点共计${list.length}条记录，确定删除吗？`)
          .then(() => {
            let idList = list.map(item => item.id)
            this.doDel(idList)
          })
          .catch(() => {})
      } else {
        this.doDel([row.id])
      }
    },
    doDel(idList) {
      this.$http.post('/auth/sys/api/op/batchDel', idList).then(() => {
        this.$helper.successMessage('删除成功')
        this.fetchTableData()
      })
    },
    add(row, isCopyAdd) {
      this.$refs.addEditDialog.add(row, isCopyAdd)
    },
    edit(row) {
      this.$refs.addEditDialog.edit(row)
    },
  },
}
</script>

<style scoped>
.icon {
  font-size: 18px;
  color: #1e87f0;
}
</style>
