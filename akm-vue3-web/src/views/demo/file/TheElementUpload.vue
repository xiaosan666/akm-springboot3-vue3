<template>
  <el-upload
    action="/"
    :show-file-list="false"
    :before-upload="uploadBefore"
    :http-request="httpRequest"
  >
    <el-button type="primary" :loading="loading">点击上传</el-button>
  </el-upload>
</template>

<script>
export default {
  name: 'TheElementUpload',
  data() {
    return {
      loading: false,
      pathPrefix: '/hello/word',
    }
  },
  methods: {
    uploadBefore(file) {
      // 这里需要判断文件尺寸、类型、数量，参考akm-attachment-upload组件
    },
    httpRequest(param) {
      let file = param.file
      this.loading = true
      this.$api
        .fileUpload(file, this.pathPrefix)
        .then(key => {
          this.baseUpload = {
            success: true,
            objectKey: key,
            fileName: file.name,
            fileSize: this.$utils.formatFileSize(file.size),
          }
          this.$helper.successMessage('上传成功')
        })
        .finally(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style scoped></style>
