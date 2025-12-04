<template>
  <div>
    <akm-table ref="table" :config="tableConfig">
      <template #toolbar>
        <div class="akm-flex-space-between">
          <div style="display: flex">
            <el-input
              v-model="searchContent"
              clearable
              placeholder="可关键字（名称、uri）模糊搜索"
              style="width: 280px"
              @clear="filterData"
              @keydown.enter="filterData"
            >
              <template v-slot:prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
            <el-button style="margin-left: 4px" type="primary" @click="filterData">搜 索</el-button>
          </div>
        </div>
      </template>
      <template #column-type="{ row }">
        <el-tag v-if="row.type === 1" type="info">目录</el-tag>
        <el-tag v-if="row.type === 2" type="success">接口</el-tag>
      </template>
    </akm-table>
  </div>
</template>

<script>
import { useSwaggerApiStore } from '@/stores/swaggerApi'
import { Search } from '@element-plus/icons-vue'

export default {
  name: 'TheSwaggerApi',
  components: {
    Search,
  },
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
          { prop: 'orders', label: '排序', width: 100 },
          { prop: 'remark', label: '备注' },
        ],
      },

      list: [],
    }
  },
  computed: {
    swaggerApiData() {
      const swaggerApiStore = useSwaggerApiStore()
      return swaggerApiStore.tree
    },
  },
  async created() {
    await this.initSwaggerApiData()
    let tree = this.swaggerApiData
    this.tableConfig.data = tree
    this.list = this.$utils.treeToList(tree)
  },
  methods: {
    async initSwaggerApiData() {
      const swaggerApiStore = useSwaggerApiStore()
      await swaggerApiStore.initSwaggerApiData()
    },
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
  },
}
</script>
