<template>
  <div>
    <div class="message-add-or-edit">
      <akm-form ref="form" :data="formData" :config="formConfig" @handleEvent="handleFormEvent">
        <template #form-rangeTypeContent>
          <div v-if="formData.orgList && formData.orgList.length">
            <el-button v-margin-right type="success" size="small" @click="selectOrgTreeDialogOpen">
              修改
            </el-button>
            <el-tag
              v-for="tag in formData.orgList"
              :key="tag.orgId"
              v-margin-right
              type="info"
              closable
              @close="del('org', tag.orgId)"
            >
              {{ tag.orgName }}
            </el-tag>
          </div>
          <div v-if="formData.userList && formData.userList.length">
            <el-button v-margin-right type="success" size="small" @click="selectUserDialogOpen">
              修改
            </el-button>
            <el-tag
              v-for="tag in formData.userList"
              :key="tag.userId"
              v-margin-right
              type="primary"
              closable
              @close="del('user', tag.userId)"
            >
              {{ tag.userName }}
            </el-tag>
          </div>
        </template>
        <template #form-file>
          <akm-attachment-upload
            :data="formData.bizAttachments"
            :show-table="false"
            :is-edit="!isView"
            path-prefix="message"
            acceptTip="注：附件最大为20M"
            @update="attachmentUpdate"
          ></akm-attachment-upload>
        </template>
      </akm-form>
      <div v-text-center>
        <el-button v-if="!isView" :loading="loading" type="success" @click="submit(0)">
          保 存
        </el-button>
        <el-button v-if="!isView" :loading="loading" type="primary" @click="submit(1)">
          下 发
        </el-button>
        <el-button :loading="loading" @click="cancel">取 消</el-button>
      </div>
    </div>
    <akm-select-org-tree-dialog
      ref="selectOrgTreeDialog"
      @confirm="selectOrgTreeConfirm"
    ></akm-select-org-tree-dialog>
    <akm-select-user-dialog
      ref="selectUserDialog"
      @confirm="selectUserConfirm"
    ></akm-select-user-dialog>
  </div>
</template>

<script>
import AkmSelectOrgTreeDialog from '@/views/common/akm-select-org-tree-dialog/index.vue'
import AkmSelectUserDialog from '@/views/common/akm-select-user-dialog/index.vue'
import AkmAttachmentUpload from '@/views/common/akm-attachment-upload/index.vue'

export default {
  name: 'SysMessageAddOrEdit',
  components: {
    AkmAttachmentUpload,
    AkmSelectUserDialog,
    AkmSelectOrgTreeDialog,
  },
  data() {
    return {
      loading: false,
      isView: true,
      isEdit: false,
      formData: {
        id: '', // 主键
        title: '', // 标题
        content: '', // 内容
        messageType: 1, // 消息分类(1消息/站内信；5公告；9其他)
        messagePriority: 1, // 消息优先级(1普通；5紧急)
        messageStatus: 0, // 消息状态(0暂存；1下发)
        rangeType: 1, // 发送范围类型(1全部用户；2指定单位/部门；3指定用户)
        rangeStr: '', // 发送范围明细
        bizRecordType: '', // 业务场景分类(冗余字段，分区不同业务下发的消息)
        bizRecordId: '', // 业务场景业务id
        bizUrl: '', // 业务跳转链接地址
        bizRemark: '', // 业务根据需要存储其他内容
        orgList: [], // 消息下发的单位
        userList: [], // 消息下发的用户
        bizAttachments: [], // 消息附件
      },

      formConfig: {
        labelWidth: 150, // label宽度
        disabled: true,
        items: [
          { type: 'input', prop: 'title', label: '标题' },
          { type: 'textarea', prop: 'content', label: '内容' },
          {
            type: 'radioDict',
            code: 'biz_message_type',
            prop: 'messageType',
            label: '消息分类',
          },
          {
            type: 'radioDict',
            code: 'biz_message_priority_type',
            prop: 'messagePriority',
            label: '消息优先级',
          },
          {
            type: 'radioDict',
            code: 'biz_message_range_type',
            prop: 'rangeType',
            label: '发送范围',
            event: 'onChangeMessageRangeType',
          },
          {
            type: 'slot',
            prop: 'rangeTypeContent',
            label: '已选择单位/人员',
            hide: true,
          },
          { prop: 'bizRecordType', label: '业务场景分类' },
          { prop: 'bizRecordId', label: '业务场景业务id' },
          { prop: 'bizUrl', label: '业务跳转链接地址' },
          { prop: 'file', label: '添加附件', type: 'slot' },
        ],
        rules: {
          title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
          content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
        },
      },
    }
  },
  created() {
    let messageId = this.$route.params.messageId
    let { isEdit, isView } = {
      ...this.$route.meta,
    }
    this.isView = isView
    this.isEdit = isEdit
    this.formConfig.disabled = isView
    if (messageId) {
      this.loading = true
      this.$http
        .post('/share/public/biz/message/view/detail', messageId)
        .then(res => {
          this.formData = { ...this.formData, ...res }
          if (!isEdit) {
            this.formData.id = ''
          }
        })
        .finally(() => {
          this.loading = false
        })
    }
    this.$helper.createCsrfToken()
  },
  methods: {
    // messageStatus 消息状态(0暂存；1下发)
    submit(messageStatus) {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.formData.messageStatus = messageStatus
          if (messageStatus === 1) {
            this.$helper
              .confirm('确认下发吗，下发后将不能修改？')
              .then(() => {
                this.doSubmit()
              })
              .catch()
          } else {
            this.doSubmit()
          }
        }
      })
    },
    doSubmit() {
      this.loading = true
      this.$http
        .post('/share/public/biz/message/op/insertOrUpdate', this.formData)
        .then(() => {
          this.$helper.successMessage(this.formData.messageStatus === 1 ? '下发成功' : '保存成功')
          this.$refs.form.resetFields()
          this.$router.push('/sys/message')
        })
        .finally(() => {
          this.loading = false
          this.$helper.createCsrfToken()
        })
    },
    cancel() {
      this.$router.push('/sys/message')
    },
    selectOrgTreeDialogOpen() {
      let orgIdList = this.formData.orgList.map(it => it.orgId)
      this.$refs.selectOrgTreeDialog.open(orgIdList)
    },
    selectOrgTreeConfirm(selectedList) {
      this.formData.orgList = selectedList.map(it => {
        return {
          orgId: it.id,
          orgName: it.name,
        }
      })
    },
    selectUserDialogOpen() {
      let userIdList = this.formData.userList.map(it => it.userId)
      this.$refs.selectUserDialog.open(userIdList)
    },
    selectUserConfirm(selectedList) {
      this.$helper.successMessage(`成功新增${selectedList.length}条数据`)
      let userList = selectedList.map(it => {
        return {
          userId: it.id,
          userName: it.realname,
        }
      })
      this.formData.userList = [...this.formData.userList, ...userList]
    },
    handleFormEvent(eventName, data) {
      // 发送范围改变事件
      if (eventName === 'onChangeMessageRangeType') {
        // 发送范围为全部，则隐藏已选择单位/人员
        let rangeTypeContentItem = this.formConfig.items.filter(
          it => it.prop === 'rangeTypeContent'
        )[0]
        rangeTypeContentItem.hide = data === 1
        if (data === 2) {
          this.selectOrgTreeDialogOpen()
        }
        if (data === 3) {
          this.selectUserDialogOpen()
        }
      }
    },
    del(type, id) {
      // 编辑状态则确认删除
      if (this.formData.id) {
        this.$helper
          .confirm('确定删除吗？')
          .then(() => {
            this.doDel(type, id)
          })
          .catch(() => {})
      } else {
        this.doDel(type, id)
      }
    },
    doDel(type, id) {
      if (type === 'org') {
        // 查找位置
        let index = this.formData.orgList.findIndex(it => it.orgId === id)
        // 直接删除
        this.formData.orgList.splice(index, 1)
        // 重置发布范围
        if (this.formData.orgList.length === 0) {
          this.formData.rangeType = 1
        }
      } else if (type === 'user') {
        let index = this.formData.userList.findIndex(it => it.userId === id)
        this.formData.userList.splice(index, 1)
        if (this.formData.userList.length === 0) {
          this.formData.rangeType = 1
        }
      }
    },
    attachmentUpdate(list) {
      this.formData.bizAttachments = list
    },
  },
}
</script>

<style lang="scss" scoped>
.message-add-or-edit {
  width: 600px;
}
</style>
