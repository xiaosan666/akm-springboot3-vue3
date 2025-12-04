<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <el-switch
      v-model="treeStrictly"
      v-margin-bottom
      active-text="选中父级自动全选子集"
    ></el-switch>
    <el-tree
      ref="tree"
      :data="tree"
      show-checkbox
      node-key="id"
      :props="defaultProps"
      highlight-current
      :check-strictly="true"
      @check="onCheck"
    >
      <template v-slot="{ node, data }">
        <div>
          {{ data.name }}
          <span v-if="data.parentId === '0'" style="color: #aaa">
            （{{ $dict.translateNameByTypeAndValue('client_type', data.clientType) }}）
          </span>
        </div>
      </template>
    </el-tree>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheRoleMenuTreeDialog',
  computed: {
    dict() {
      return dict
    },
  },
  data() {
    return {
      firstOpen: true, // 标记首次打开才去加载数据
      treeStrictly: false, // tree在显示复选框的情况下，是否严格的遵循父子不互相关联的做法, true父子互相关联
      dialogConfig: {
        visible: false,
        loading: false,
        title: '分配权限',
        width: 500,
      },

      defaultProps: {
        children: 'children',
        label: 'name',
      },

      tree: [],
      list: [],
      role: {},
    }
  },
  methods: {
    fetchTreeData() {
      this.$http
        .post('/auth/sys/menu/view/findAll', {
          enable: 1,
        })
        .then(list => {
          this.list = list
          this.tree = this.$utils.listToTree(list)
        })
    },
    open(role) {
      if (this.firstOpen) {
        this.fetchTreeData()
        this.firstOpen = false
      }
      this.role = role
      this.dialogConfig.title = `分配权限【${this.role.name}】`
      this.dialogConfig.visible = true
      let menuIds = this.role.menuIds
      this.$nextTick(() => {
        this.$refs.tree.setCheckedKeys(menuIds ? menuIds.split(',') : [])
      })
    },
    // 父子不相互关联，当节点被点击时（当节点被选中时，自动选中父节点，当节点取消选中时，自动取消所有子节点）
    onCheck(data, tree) {
      let checked = tree.checkedKeys.includes(data.id)
      if (checked) {
        let parentIds = this.getParentIds(data.id)
        let checkedKeys = [...tree.checkedKeys, ...parentIds]
        if (this.treeStrictly) {
          checkedKeys = [...checkedKeys, ...this.getChildrenIds(data)]
        }
        this.$refs.tree.setCheckedKeys(checkedKeys)
        // 展开节点
        this.$refs.tree.store.nodesMap[data.id].expanded = true
      } else {
        let childrenIds = this.getChildrenIds(data)
        let checkedKeys = tree.checkedKeys.filter(key => {
          return !childrenIds.includes(key)
        })
        this.$refs.tree.setCheckedKeys(checkedKeys)
      }
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
    // 获取节点的所有子节点
    getChildrenIds(node, arr = []) {
      if (node && node.children) {
        node.children.forEach(item => {
          arr.push(item.id)
          this.getChildrenIds(item, arr)
        })
      }
      return arr
    },
    confirm() {
      this.dialogConfig.loading = true
      let menuIdList = this.$refs.tree.getCheckedKeys()
      this.$http
        .post('/auth/sys/role/op/updateMenuByRoleId', {
          roleId: this.role.id,
          menuIdList,
        })
        .then(() => {
          this.$helper.successMessage()
          this.$emit('confirm')
          this.dialogConfig.visible = false
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
  },
}
</script>
