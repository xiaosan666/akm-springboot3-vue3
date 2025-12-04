<template>
  <div>
    <akm-table ref="table" :config="tableConfig">
      <template #toolbar>
        <div class="akm-flex-space-between">
          <div style="display: flex">
            <el-input
              v-model="searchContent"
              clearable
              placeholder="可关键字（名称）模糊搜索"
              prefix-icon="el-icon-search"
              style="width: 280px"
              @clear="filterData"
              @keydown.enter.native="filterData"
            ></el-input>
            <el-button style="margin-left: 4px" type="primary" @click="filterData">搜 索</el-button>
          </div>
          <el-button v-has="'sys_org_add'" type="primary" @click="add">新增组织机构</el-button>
        </div>
      </template>
      <template #column-type="{ row }">
        <el-tag v-if="row.type === 1" type="info">目录</el-tag>
        <el-tag v-if="row.type === 2" type="success">接口</el-tag>
      </template>
      <template #column-enable="{ row }">
        <el-tag v-if="row.enable === 1" type="primary">启用</el-tag>
        <el-tag v-else type="danger">已停用</el-tag>
      </template>
      <el-table-column class-name="op-buttons" label="操作" width="280">
        <template v-slot="{ row }">
          <akm-warn-btn v-has="'sys_org_del'" @confirm="del(row)">删除</akm-warn-btn>
          <el-button v-has="'sys_org_edit'" type="primary" link @click="edit(row)">编辑</el-button>
          <el-button v-has="'sys_org_add'" type="primary" link @click="add(row, false)">
            添加子节点
          </el-button>
          <el-button v-has="'sys_org_add'" type="primary" link @click="add(row, true)">
            复制新增
          </el-button>
        </template>
      </el-table-column>
    </akm-table>

    <the-org-add-edit-dialog
      ref="addEditDialog"
      :orgOptions="orgOptions"
      :dataList="list"
      @saveSuccess="fetchTableData"
    ></the-org-add-edit-dialog>
  </div>
</template>

<script>
import TheOrgAddEditDialog from '@/views/sys/org/TheOrgAddEditDialog.vue'

export default {
  name: 'SysOrg',
  components: { TheOrgAddEditDialog },
  data() {
    return {
      searchContent: '',
      tableConfig: {
        data: [],
        loading: false,
        columns: [
          { prop: 'name', label: '组织机构名称' },
          { prop: 'orgCode', label: '组织机构编码' },
          { prop: 'levelName', label: '组织机构等级', width: 110 },
          // { prop: 'orgFullName', label: '组织机构全路径' },
          { prop: 'remark', label: '备注' },
          { prop: 'orders', label: '排序', width: 80 },
          {
            type: 'slot',
            prop: 'enable',
            label: '启用状态',
            width: 100,
          },
        ],
      },

      rootMenu: {
        id: '0',
        name: '根目录',
        disabled: false,
      },
      orgOptions: [],
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
          (item.orgFullName && item.orgFullName.includes(this.searchContent))
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
        .post('/auth/sys/org/view/findAll', {})
        .then(list => {
          if (!list || list.length === 0) {
            return
          }
          this.list = list
          let tree = this.$utils.listToTree(list, list[0].parentId)
          this.tableConfig.data = tree
          this.orgOptions = [this.rootMenu, ...tree]
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
      this.$http.post('/auth/sys/org/op/batchDel', idList).then(() => {
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
