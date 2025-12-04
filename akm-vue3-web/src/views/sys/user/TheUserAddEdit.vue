<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
    <div class="user-form-warp">
      <div>
        <div class="akm-section-title">基本信息</div>
        <akm-form ref="form" :config="formConfig" :data="formData"></akm-form>
      </div>
      <div class="org-tree-warp">
        <div class="akm-section-title">选择归属组织</div>
        <akm-select-org-tree
          ref="akmSelectOrgTree"
          single-select
          @change="onOrgChange"
        ></akm-select-org-tree>
      </div>
    </div>
  </akm-dialog>
</template>
<script>
import AkmSelectOrgTree from '@/views/common/akm-select-org-tree/index.vue'
export default {
  name: 'TheUserAddEdit',
  components: { AkmSelectOrgTree },
  data() {
    return {
      firstOpen: true, // 标记首次打开才去加载数据
      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 700,
      },
      formData: {
        id: '',
        username: '',
        password: '',
        realname: '',
        tenantId: '',
        enable: 1,
        expiredTime: '',
        orgId: '',
        orgName: '',
      },
      formConfig: {
        items: [
          {
            type: 'input',
            prop: 'username',
            label: '手机号码',
          },
          {
            type: 'input',
            prop: 'password',
            label: '密码',
            hide: false,
          },
          {
            type: 'input',
            prop: 'realname',
            label: '真实姓名',
          },
          {
            type: 'input',
            prop: 'orgName',
            label: '归属组织',
            disabled: true,
          },
          {
            type: 'select',
            prop: 'tenantId',
            label: '所属租户',
            options: [],
          },
          {
            type: 'radioDict',
            code: 'enable_status_op',
            prop: 'enable',
            label: '启用状态',
          },
          // { type: 'datetime', prop: 'expiredTime', label: '账号过期时间' },
        ],
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            {
              validator: this.$validate.elementValidator(
                this.$validate.regex.phone,
                '手机号码输入不合法'
              ),
              trigger: 'blur',
            },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            {
              validator: this.$validate.elementValidator(
                this.$validate.regex.password,
                '密码8-20位，需同时包含数字、字母以及特殊字符（英文状态）!@#$%^&*()'
              ),
              trigger: 'blur',
            },
          ],
          realname: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
          tenantId: [{ required: true, message: '请选择所属租户', trigger: 'change' }],
        },
      },
    }
  },
  methods: {
    open(row) {
      if (this.firstOpen) {
        this.fetchAllTenant()
        this.firstOpen = false
      }
      this.$helper.createCsrfToken()
      if (row && row.id) {
        this.formData = this.$utils.clone(row)
        this.dialogConfig.title = '编辑'
      } else {
        this.formData.id = ''
        this.dialogConfig.title = '新增'
      }
      // 编辑隐藏密码框
      let passwordItem = this.formConfig.items.find(item => item.prop === 'password')
      passwordItem.hide = Boolean(row && row.id)

      this.dialogConfig.visible = true

      setTimeout(() => {
        // 选中归属组织
        let checkedIds = row && row.orgId ? [row.orgId] : []
        this.$refs.akmSelectOrgTree.setExpandedKeys(checkedIds)
        this.$refs.akmSelectOrgTree.setCheckedKeys(checkedIds)
      })
    },
    fetchAllTenant() {
      this.$api.fetchTenantList().then(list => {
        if (list.length > 0) {
          this.formConfig.items.find(item => item.prop === 'tenantId').options = list
        }
      })
    },
    onOrgChange(data) {
      this.formData.orgId = data ? data.id : ''
      this.formData.orgName = data ? data.name : ''
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.dialogConfig.loading = true
          let data = this.$utils.clone(this.formData)
          if (!data.id) {
            data.password = this.$security.sha256(data.password)
          }
          this.$http
            .post('/auth/sys/user/op/insertOrUpdate', data)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$emit('saveSuccess')
              this.$refs.form.resetFields()
              this.dialogConfig.visible = false
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
  },
}
</script>

<style scoped>
.user-form-warp {
  display: flex;
  .org-tree-warp {
    flex: 1;
    margin-left: 20px;
    padding-left: 20px;
    border-left: 1px solid #f5f5f5;
  }
}
</style>
