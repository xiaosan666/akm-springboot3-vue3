<template>
  <!--附件上传与列表展示-->
  <div>
    <el-upload
      v-if="isEdit"
      action="/"
      :accept="accept"
      :show-file-list="false"
      :before-upload="uploadBefore"
      :http-request="httpRequest"
    >
      <el-button type="primary" :loading="loading">点击上传</el-button>
      <span style="color: red; margin-left: 10px; font-size: 12px">
        {{ computedAcceptTip }}
      </span>
    </el-upload>
    <akm-attachment-table
      v-if="showTable"
      :data="data"
      :show-del-btn="isEdit"
      @del="del"
    ></akm-attachment-table>
    <akm-attachment-list
      v-if="!showTable"
      :data="data"
      :show-del-btn="isEdit"
      @del="del"
    ></akm-attachment-list>
  </div>
</template>

<script>
// 图片类型
import AkmAttachmentTable from '@/views/common/akm-attachment-table/index.vue'
import AkmAttachmentList from '@/views/common/akm-attachment-list/index.vue'

export default {
  name: 'AkmAttachmentUpload',
  components: { AkmAttachmentList, AkmAttachmentTable },
  props: {
    // 附件列表数据
    data: {
      type: Array,
      default() {
        return []
      },
    },
    // 是否显示上传按钮和附件删除按钮
    isEdit: {
      type: Boolean,
      default: true,
    },
    // 是否显示上传按钮和附件删除按钮
    showTable: {
      type: Boolean,
      default: true,
    },
    // 文件路径前缀，如/wz_file
    pathPrefix: {
      type: String,
      default: '',
    },
    // 允许上传的附件大小，单位M,默认20M
    size: {
      type: Number,
      default: 20,
    },
    // 允许上传的附件数量
    number: {
      type: Number,
      default: 5,
    },
    // 允许上传的附件类型
    accept: {
      type: String,
      default: `.jpg,.jpeg,.png,.gif,.webp,.tif,.bmp,.dwg,.doc,.docx,.pdf`,
    },
    // 上传附件提示内容
    acceptTip: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      loading: false,
    }
  },
  computed: {
    computedAcceptTip() {
      if (this.acceptTip) {
        return this.acceptTip
      }
      return `注：附件最大为 ${this.size} M，只允许图片、word、pdf格式；最多上传 ${this.number} 个附件！`
    },
  },
  methods: {
    uploadBefore(file) {
      // 判断文件数量
      if (this.data.length >= this.number) {
        this.$helper.alert(`最多上传 ${this.number} 个附件!`)
        return false
      }
      // 判断文件尺寸
      const limit = this.size
      if (file.size / 1024 / 1024 > limit) {
        this.$helper.alert(`文件大小不能超过 ${limit}MB!`)
        return false
      }
      // 判断文件类型
      let nameSuffix = file.name.split('.').pop().toLowerCase()
      if (this.accept !== '*' && this.accept.indexOf(nameSuffix) === -1) {
        this.$helper.alert(`不允许上传.${nameSuffix}类型的文件!`)
        return false
      }
      return true
    },
    async httpRequest(param) {
      let file = param.file
      this.loading = true

      try {
        const key = await this.$api.fileUpload(file, this.pathPrefix)
        this.$helper.successMessage('上传成功')
        let row = {
          attachmentName: file.name,
          attachmentUrl: key,
          attachmentSize: file.size,
          createTime: Date.now(),
        }
        const newData = [...this.data, row]
        this.$emit('update', newData)
      } catch (error) {
        console.error('文件上传失败:', error)
        this.$helper.errorMessage('上传失败')
      } finally {
        this.loading = false
      }
    },
    del(data) {
      this.$emit('update', data)
    },
  },
}
</script>

<style lang="scss" scoped></style>
