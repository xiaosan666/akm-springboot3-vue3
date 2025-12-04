<template>
  <div>
    <div v-if="!singleSelect" v-margin-bottom class="akm-flex-space-between">
      <el-switch v-model="treeStrictly" active-text="选中父级自动全选子集"></el-switch>
      <el-button size="small" type="warning" @click="handleAllChange">全选</el-button>
    </div>
    <el-input v-model="filterText" clearable placeholder="输入关键字进行过滤"></el-input>
    <el-tree
      ref="tree"
      :check-strictly="!treeStrictly"
      :data="tree"
      :default-expanded-keys="defaultExpandedKeys"
      :filter-node-method="filterNode"
      :props="defaultProps"
      highlight-current
      node-key="id"
      show-checkbox
      style="max-height: 400px; overflow: auto"
      @check="onCheck"
    >
      <template #default="{ data }">
        <div>{{ data.name }}</div>
      </template>
    </el-tree>
  </div>
</template>

<script>
export default {
  name: 'AkmSelectOrgTree',
  props: {
    // 是否单选
    singleSelect: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      treeStrictly: false, // tree在显示复选框的情况下，是否严格的遵循父子不互相关联的做法, true父子互相关联

      defaultProps: {
        children: 'children',
        label: 'name',
      },

      defaultExpandedKeys: [],
      tree: [],
      list: [],

      filterText: '',
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    },
  },
  created() {
    this.fetchTreeData()
  },
  methods: {
    async fetchTreeData() {
      try {
        const list = await this.$http.post('/auth/sys/org/view/findAll', {})
        if (!list || list.length === 0) {
          return
        }
        this.list = list
        this.tree = this.$utils.listToTree(list, list[0].parentId)
      } catch (error) {
        console.error('获取组织树数据失败:', error)
      }
    },
    filterNode(value, data) {
      if (!value) return true
      return data.name && data.name.indexOf(value) !== -1
    },
    // 全选
    handleAllChange() {
      // 用于判断是否已经全选
      let menuIdList = this.$refs.tree.getCheckedKeys()
      const ids = this.list.filter(el => el.enable).map(el => el.id)
      this.$refs.tree.setCheckedKeys(menuIdList.length === ids.length ? [] : ids)
    },
    // 注意：如果是单选返回对象，不是数组
    onCheck(node, data) {
      let selected = []
      if (this.singleSelect && data.checkedKeys.length > 1) {
        this.setCheckedKeys([node.id])
        selected = [node]
      } else {
        selected = this.list.filter(it => data.checkedKeys.includes(it.id))
      }
      // 注意：如果是单选返回对象，不是数组
      if (this.singleSelect) {
        selected = selected.length === 1 ? selected.pop() : null
      }
      this.$emit('change', this.$utils.clone(selected))
    },
    setExpandedKeys(checkedIds) {
      this.defaultExpandedKeys = checkedIds
    },
    setCheckedKeys(checkedIds) {
      this.$refs.tree.setCheckedKeys(checkedIds)
    },
    getCheckedKeys() {
      return this.$refs.tree.getCheckedKeys()
    },
    getCheckedData() {
      let keys = this.$refs.tree.getCheckedKeys()
      return this.list.filter(it => keys.includes(it.id))
    },
  },
}
</script>
