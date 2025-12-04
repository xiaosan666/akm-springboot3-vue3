<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false">
    <el-tree
      ref="tree"
      :data="tree"
      show-checkbox
      node-key="id"
      :props="defaultProps"
      :check-strictly="true"
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
  name: 'TheUserMenuTree',
  data() {
    return {
      firstOpen: true, // 标记首次打开才去加载数据
      dialogConfig: {
        visible: false,
        loading: false,
        title: '查看用户权限',
        width: 500,
        footer: false,
      },

      defaultProps: {
        children: 'children',
        label: 'name',
      },

      tree: [],
    }
  },
  methods: {
    open(userId) {
      if (this.firstOpen) {
        this.fetchTreeData()
        this.firstOpen = false
      }
      // 获取userId已分配角色列表
      this.dialogConfig.visible = true
      this.dialogConfig.loading = true
      this.$http
        .post('/auth/sys/menu/view/getMenuIdByUserId', userId)
        .then(res => {
          this.$refs.tree.setCheckedKeys(res)
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
    fetchTreeData() {
      this.$http
        .post('/auth/sys/menu/view/findAll', {
          enable: 1,
        })
        .then(list => {
          this.tree = this.$utils.listToTree(list)
        })
    },
  },
}
</script>
