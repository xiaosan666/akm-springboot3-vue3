<template>
  <div>
    <el-tabs v-model="clientType" @tab-click="fetchTableData">
      <el-tab-pane v-for="item in tabs" :key="item.value" :name="item.value">
        <template v-slot:label>
          <span style="font-weight: bold; font-size: 15px">
            <el-icon><Menu /></el-icon>
            {{ item.label }}
          </span>
        </template>
      </el-tab-pane>
    </el-tabs>
    <akm-table ref="table" :config="tableConfig">
      <template #toolbar>
        <div class="akm-flex-space-between">
          <div style="display: flex">
            <el-input
              v-model="searchContent"
              placeholder="可关键字（名称、路由、权限标识）模糊搜索"
              clearable
              style="width: 280px"
              @keydown.enter="filterData"
              @clear="filterData"
            >
              <template v-slot:prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
            <el-button type="primary" style="margin-left: 4px" @click="filterData">搜 索</el-button>
          </div>
          <el-button v-has="'sys_menu_add'" type="primary" @click="add">新增资源</el-button>
        </div>
      </template>
      <template #column-icon="{ row }">
        <i :class="['menu-icon', row.icon]"></i>
      </template>
      <template #column-type="{ row }">
        <el-tag v-if="row.type === 1" type="info">目录</el-tag>
        <el-tag v-if="row.type === 2" type="success">菜单</el-tag>
        <el-tag v-if="row.type === 3">按钮</el-tag>
      </template>
      <template #column-enable="{ row }">
        <el-tag v-if="row.enable === 1" type="primary">启用</el-tag>
        <el-tag v-else type="danger">已停用</el-tag>
      </template>
      <template #column-op="{ row }">
        <akm-warn-btn v-has="'sys_menu_del'" @confirm="del(row)">删除</akm-warn-btn>
        <el-button v-has="'sys_menu_edit'" type="primary" link @click="edit(row)">编辑</el-button>
        <el-button v-has="'sys_menu_add'" type="primary" link @click="add(row, false)">
          添加子节点
        </el-button>
        <el-button
          v-has="'sys_menu_add'"
          type="primary"
          link
          :divider="row.type !== 1"
          @click="add(row, true)"
        >
          复制新增
        </el-button>
        <el-button
          v-if="row.type !== 1"
          v-has="'sys_menu_assign_api'"
          type="primary"
          link
          @click="setApi(row)"
        >
          接口权限
        </el-button>
      </template>
    </akm-table>

    <the-menu-add-edit-dialog
      ref="addEditDialog"
      :menuOptions="menuOptions"
      :clientType="clientType"
      @saveSuccess="fetchTableData"
    ></the-menu-add-edit-dialog>

    <the-menu-api-tree-dialog
      ref="apiTreeDialog"
      :menuTree="tableConfig.data"
      @saveSuccess="fetchTableData"
    ></the-menu-api-tree-dialog>
  </div>
</template>

<script>
import TheMenuAddEditDialog from '@/views/sys/menu/TheMenuAddEditDialog.vue'
import TheMenuApiTreeDialog from '@/views/sys/menu/TheMenuApiTreeDialog.vue'
import { Position, Search } from '@element-plus/icons-vue'

export default {
  name: 'SysMenu',
  components: {
    TheMenuApiTreeDialog,
    TheMenuAddEditDialog,
    Position,
    Search,
  },
  data() {
    return {
      tabs: [],
      clientType: '',

      searchContent: '',

      tableConfig: {
        data: [],
        loading: false,
        columns: [
          { prop: 'name', label: '资源名称' },
          { prop: 'uri', label: '路由' },
          { prop: 'code', label: '权限标识' },
          {
            type: 'slot',
            prop: 'icon',
            label: '图标',
            width: 90,
          },
          {
            type: 'slot',
            prop: 'type',
            label: '类型',
            width: 90,
            className: 'akm-no-padding',
          },
          { prop: 'orders', label: '排序（倒序）', width: 110 },
          {
            type: 'slot',
            prop: 'enable',
            label: '启用状态',
            width: 100,
          },
          { type: 'slot', prop: 'op', label: '操作', width: 320 },
        ],
      },

      rootMenu: {
        id: '0',
        name: '根目录',
        // disabled: true,
      },
      menuOptions: [],
      list: [],
    }
  },
  created() {
    // 初始化页面需要的字典值
    this.$dict.getListByTypes(['client_type']).then(res => {
      this.clientType = res[0].value
      this.tabs = res
      this.fetchTableData()
    })
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
          (item.uri && item.uri.includes(this.searchContent)) ||
          (item.code && item.code.includes(this.searchContent))
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
      this.searchContent = ''
      let config = this.tableConfig
      config.loading = true
      this.$http
        .post('/auth/sys/menu/view/findAll', {
          clientType: this.clientType,
        })
        .then(list => {
          this.list = list
          let tree = this.$utils.listToTree(list)
          config.data = tree
          this.tableConfig.data = tree
          this.menuOptions = [this.rootMenu, ...tree]
        })
        .finally(() => {
          config.loading = false
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
      this.$http.post('/auth/sys/menu/op/batchDel', idList).then(() => {
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
    setApi(row) {
      this.$refs.apiTreeDialog.open(row)
    },
  },
}
</script>

<style scoped lang="scss">
.menu-icon {
  font-size: 18px;
  color: #666;
}
</style>
