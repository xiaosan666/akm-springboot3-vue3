<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <el-tabs v-model="activeName" @tab-click="handleTabClick">
      <el-tab-pane label="Swagger自动生成接口" name="first"></el-tab-pane>
      <el-tab-pane label="手动添加接口" name="second"></el-tab-pane>
    </el-tabs>
    <div style="display: flex">
      <el-input
        v-model="searchContent"
        placeholder="可关键字（名称、uri）模糊搜索"
        clearable
        @keydown.enter="filterData"
        @clear="filterData"
      >
        <template v-slot:prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-button type="primary" style="margin-left: 4px" @click="filterData">搜 索</el-button>
    </div>
    <el-tree
      ref="tree"
      :data="tree"
      show-checkbox
      node-key="uri"
      check-strictly
      :props="defaultProps"
      :default-checked-keys="checkedKeys"
      :default-expanded-keys="expandedKeys"
      highlight-current
    >
      <template v-slot="{ node, data }">
        <div>
          {{ data.name }}
          <span v-if="data.uri" style="color: #aaa">{{ data.uri }}</span>
        </div>
      </template>
    </el-tree>
  </akm-dialog>
</template>

<script>
import { useSwaggerApiStore } from '@/stores/swaggerApi'
import { Search } from '@element-plus/icons-vue'

export default {
  name: 'TheMenuApiTreeDialog',
  components: {
    Search,
  },
  data() {
    return {
      activeName: 'first',

      firstOpen: true, // 标记首次打开才去加载数据

      dialogConfig: {
        visible: false,
        loading: false,
        title: '',
        width: 600,
      },

      defaultProps: {
        children: 'children',
        label: 'name',
      },

      tree: [],
      list: [],
      checkedKeys: [],
      expandedKeys: [],
      menu: {},
      searchContent: '',

      manualApiList: [],
    }
  },
  computed: {
    swaggerApiData() {
      const swaggerApiStore = useSwaggerApiStore()
      return swaggerApiStore.tree
    },
  },
  created() {
    this.initSwaggerApiData()
  },
  methods: {
    async initSwaggerApiData() {
      const swaggerApiStore = useSwaggerApiStore()
      await swaggerApiStore.initSwaggerApiData()
    },
    async open(menu) {
      if (this.firstOpen) {
        await this.initSwaggerApiData()
        this.setSwaggerApi()
        this.$http.post('/auth/sys/api/view/findAll', {}, { cacheData: true }).then(list => {
          this.manualApiList = list
        })
        this.firstOpen = false
      }
      this.menu = menu
      this.dialogConfig.title = `接口权限【${this.menu.name}】`
      this.dialogConfig.visible = true
      this.$nextTick(() => {
        let selectedList = this.menu.uriList
        this.expandedKeys = selectedList
        this.checkedKeys = selectedList
        this.$refs.tree.setCheckedKeys(selectedList)
      })
    },
    handleTabClick() {
      this.activeName === 'first' ? this.setSwaggerApi() : this.setManualAddApi()
    },
    setSwaggerApi() {
      this.tree = this.swaggerApiData
      this.list = this.$utils.treeToList(this.swaggerApiData)
    },
    setManualAddApi() {
      this.list = this.manualApiList
      this.tree = this.$utils.listToTree(this.manualApiList)
    },
    filterData() {
      if (!this.searchContent) {
        this.tree = this.$utils.listToTree(this.list, '0')
        return
      }
      this.checkedKeys = this.$refs.tree.getCheckedKeys()
      let filterList = this.list.filter(item => {
        return (
          this.checkedKeys.includes(item.uri) ||
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
      this.tree = this.$utils.listToTree(newList, '0')
      this.expandedKeys = this.tree.map(item => item.uri)
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
    confirm() {
      this.dialogConfig.loading = true
      let selectedList = this.$refs.tree.getCheckedKeys()
      this.$http
        .post('/auth/sys/menu/op/updateApiByMenuId', {
          menuId: this.menu.id,
          uriList: selectedList,
        })
        .then(() => {
          this.$helper.successMessage()
          this.$emit('saveSuccess')
          this.dialogConfig.visible = false
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
  },
}
</script>
