<template>
  <div>
    <el-divider content-position="left">基础文件上传</el-divider>
    <the-base-upload></the-base-upload>
    <br />
    <br />
    <el-divider content-position="left">使用&lt;el-upload&gt;上传</el-divider>
    <the-element-upload></the-element-upload>
    <br />
    <br />
    <br />
    <br />
    <el-divider content-position="left">使用&lt;akm-attachment-upload&gt;组件上传</el-divider>
    <akm-attachment-upload
      :data="attachmentList"
      :path-prefix="attachmentPathPrefix"
    ></akm-attachment-upload>
    <el-button type="primary" @click="saveAttachment">保存附件</el-button>
    <span style="color: red; margin-left: 10px; font-size: 12px">
      注：上传文件只是把文件上传到文件服务器，保存附件才会把文件和业务绑定上关系
    </span>
    <br />
    <br />
    <br />
    <br />
    <el-divider content-position="left">使用&lt;akm-attachment-upload&gt;组件上传2</el-divider>
    <akm-attachment-upload
      :data="attachmentList"
      :path-prefix="attachmentPathPrefix"
      :show-table="false"
    ></akm-attachment-upload>
  </div>
</template>

<script>
import TheBaseUpload from '@/views/demo/file/TheBaseUpload.vue'
import TheElementUpload from '@/views/demo/file/TheElementUpload.vue'
import AkmAttachmentUpload from '@/views/common/akm-attachment-upload/index.vue'
export default {
  name: 'DemoFile',
  components: {
    AkmAttachmentUpload,
    TheElementUpload,
    TheBaseUpload,
  },
  data() {
    return {
      recordId: '1320922542750130175', // 附件关联的业务id
      recordType: 'demo', // 附件关联的业务类型
      attachmentPathPrefix: 'demo', // 附件关联的业务类型
      attachmentList: [],
    }
  },
  created() {
    this.fetchAttachment()
  },
  methods: {
    fetchAttachment() {
      this.$api.findAttachment(this.recordType, this.recordId).then(res => {
        this.attachmentList = res
      })
    },
    saveAttachment() {
      this.attachmentList.forEach(item => {
        item.recordType = this.recordType
        item.recordId = this.recordId
      })
      this.$helper.showLoading()
      /**
       *  先删除业务附件，然后批量新增
       *  实际业务中，附件和业务数据一起传给后台，删除附件和批量新增均有后台进行，这里只是展示demo
       */
      this.$api
        .deleteAttachment(this.recordType, this.recordId)
        .then(() => {
          return this.$api.batchInsertAttachment(this.attachmentList)
        })
        .then(() => {
          this.$helper.successMessage('附件保存成功')
          this.fetchAttachment()
        })
        .finally(() => {
          this.$helper.hideLoading()
        })
    },
    exportExcel() {
      const filename = '测试.xlsx'
      this.$http.post('/auth/demo/user/open/op/export').then(blob => {
        this.$utils.downloadBlob(blob, filename)
      })
    },
  },
}
</script>

<style scoped></style>
