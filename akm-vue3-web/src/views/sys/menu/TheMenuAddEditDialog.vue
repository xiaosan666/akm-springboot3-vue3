<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <el-form ref="form" class="akm-form" :model="data" :rules="rules" label-width="100px">
      <el-form-item v-model="data" prop="type" label="资源类型：">
        <akm-radio-dict code="menu_type" :value.sync="data.type" :button="true"></akm-radio-dict>
      </el-form-item>
      <el-form-item prop="name" label="资源名称：">
        <el-input v-model="data.name" clearable />
      </el-form-item>
      <!--该组件选中的值是数组，所以通过change事件处理-->
      <el-form-item prop="parentId" label="上级资源：">
        <el-cascader
          ref="cascader"
          v-model="cascaderParentId"
          :options="menuOptions"
          :props="{ checkStrictly: true, value: 'id', label: 'name' }"
          style="width: 100%"
          @change="onCascaderChange"
        ></el-cascader>
      </el-form-item>
      <el-form-item prop="icon" label="图标：">
        <div style="display: flex">
          <el-input v-model="data.icon" clearable />
          <a
            style="width: 50px; padding-left: 8px"
            href="https://element.eleme.cn/#/zh-CN/component/icon"
            target="_blank"
          >
            图标库
          </a>
        </div>
      </el-form-item>
      <el-form-item v-if="data.type === 2" prop="uri" label="路由地址">
        <el-input v-model.trim="data.uri" clearable />
      </el-form-item>
      <el-form-item prop="code" label="权限标识：">
        <el-input
          v-model.trim="data.code"
          clearable
          placeholder="形如：sys_dict_add（应用模块_业务名称_操作）"
        />
      </el-form-item>
      <el-form-item label="是否启用">
        <akm-radio-dict code="enable_status_op" :value.sync="data.enable"></akm-radio-dict>
      </el-form-item>
      <el-form-item prop="orders" label="排序（倒序）：">
        <el-input-number v-model="data.orders" />
      </el-form-item>
    </el-form>
  </akm-dialog>
</template>

<script>
export default {
  name: 'TheMenuAddEditDialog',
  props: {
    menuOptions: {
      type: Array,
      default() {
        return []
      },
    },
    clientType: {
      type: String,
    },
  },
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 560,
      },
      data: {
        type: 2, // 1 目录，2 菜单，3按钮
        name: '',
        parentId: '',
        icon: 'el-icon-menu',
        uri: '',
        code: '',
        enable: 1,
        orders: 100,
      },
      rules: {
        type: [{ required: true, message: '请选择类型', trigger: 'change' }],
        name: [{ required: true, message: '请输入名称', trigger: 'change' }],
        uri: [{ required: true, message: '请输入路由或接口', trigger: 'change' }],
        parentId: [{ required: true, message: '请选择上级菜单', trigger: 'change' }],
      },

      cascaderParentId: '',
    }
  },
  // watch: {
  //   'data.type'(val) {
  //     if (val !== 1) {
  //       // 非目录类型，选中根目录则清空选中
  //       if (this.data.parentId === '0') {
  //         this.cascaderParentId = ''
  //         this.data.parentId = ''
  //       }
  //     }
  //     // 非目录类型，禁用根目录
  //     let root = this.menuOptions.find(item => item.id === '0')
  //     root.disabled = val !== 1
  //   },
  // },
  methods: {
    add(row, isCopyAdd) {
      this.$helper.createCsrfToken()
      // 复制新增
      if (isCopyAdd) {
        this.data = this.$utils.clone(row)
        this.cascaderParentId = row.parentId
      } else {
        // 新增子节点
        if (row && row.id) {
          this.cascaderParentId = row.id
          this.data.parentId = row.id
          if (row.type === 1 || row.type === 2) {
            this.data.type = row.type + 1
          }
        }
        this.data.name = ''
      }
      this.data.id = ''
      this.dialogConfig.title = '新增'
      this.dialogConfig.visible = true
    },
    edit(row) {
      this.$helper.createCsrfToken()
      let data = this.$utils.clone(row)
      delete data.children
      this.data = data
      this.cascaderParentId = row.parentId
      this.dialogConfig.title = '编辑'
      this.dialogConfig.visible = true
    },
    confirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          // 修复数据
          if (this.data.type === 1 || this.data.type === 3) {
            this.data.uri = ''
          }
          this.dialogConfig.loading = true
          this.data.clientType = this.clientType
          this.$http
            .post('/auth/sys/menu/op/insertOrUpdate', this.data)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$emit('saveSuccess')
              this.data.type = 2 // 还原type默认值
              // 待type watch事件执行完成重置表单，否则字段默认值重置有无
              this.$nextTick(() => {
                this.$refs.form.resetFields()
                this.cascaderParentId = ''
                this.dialogConfig.visible = false
              })
            })
            .catch(() => {
              this.$helper.createCsrfToken()
            })
            .finally(() => {
              this.dialogConfig.loading = false
            })
        }
      })
    },
    onCascaderChange(valueArr) {
      this.data.parentId = this.$utils.clone(valueArr).pop()
    },
  },
}
</script>
