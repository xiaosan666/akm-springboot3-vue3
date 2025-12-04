<template>
  <div>
    <input id="file" type="file" />
    <el-button :loading="loading" type="primary" @click="upload">上传文件</el-button>
    <el-button v-if="baseUpload.objectKey" type="primary" @click="download">文件下载</el-button>
    <el-button v-if="baseUpload.objectKey" type="primary" @click="download2">
      文件下载zip（解压密码为用户名）
    </el-button>
    <el-button v-if="baseUpload.objectKey" type="primary" @click="preview">图片预览</el-button>
    <el-button v-if="baseUpload.objectKey" type="primary" @click="preview2">
      图片预览方式2
    </el-button>
    <div v-if="baseUpload.success">上传结果：{{ baseUpload }}</div>
    <div v-if="previewSrc"><img :src="previewSrc" alt="" /></div>
    <el-input type="textarea" :row="5" autosize></el-input>
  </div>
</template>

<script>
export default {
  name: 'TheBaseUpload',
  data() {
    return {
      loading: false,
      pathPrefix: '/hello/word',
      baseUpload: {
        success: false,
        objectKey: '',
        fileName: '',
        fileSize: '',
      },
      previewSrc: '',
    }
  },
  methods: {
    upload() {
      let file = document.getElementById('file').files[0]
      if (!file) {
        this.$helper.warningMessage('未选择上传文件')
        return
      }
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
    download() {
      this.$api.fileDownload(this.baseUpload.objectKey).then(blob => {
        this.$utils.downloadBlob(blob, this.baseUpload.fileName)
      })
    },
    download2() {
      this.$api.fileDownloadToZip(this.baseUpload.objectKey).then(blob => {
        this.$utils.downloadBlob(blob, this.baseUpload.fileName + '.zip')
      })
    },
    preview() {
      if (!this.$utils.isImage(this.baseUpload.fileName)) {
        this.$helper.warningMessage('非图片文件不支持预览')
        return
      }
      this.$api.fileDownload(this.baseUpload.objectKey).then(blob => {
        this.previewSrc = window.URL.createObjectURL(blob)
      })
    },
    preview2() {
      if (!this.$utils.isImage(this.baseUpload.fileName)) {
        this.$helper.warningMessage('非图片文件不支持预览')
        return
      }
      this.$api.getFileUrl(this.baseUpload.objectKey).then(url => {
        window.open(url, '_blank')
      })
    },
  },
}
</script>

<style scoped></style>
