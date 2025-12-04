<template>
  <div>
    <h3>模版下载</h3>
    <div>
      <a
        download="用户管理demo导入模板"
        href="https://public-1253449983.cos.ap-guangzhou.myqcloud.com/excel_template/excel_template_demo_user.xlsx"
        target="_blank"
      >
        在线模板
      </a>
    </div>
    <el-button link @click="download">
      <i class="el-icon-download" style="font-size: 18px"></i>
      {{ templateName }}
    </el-button>
    <el-upload
      drag
      action="/"
      :accept="accept"
      :show-file-list="false"
      :before-upload="uploadBefore"
      :http-request="httpRequest"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">
        将文件拖到此处，或
        <em>点击上传</em>
      </div>
      <div slot="tip" class="el-upload__tip">只能上传excel文件，且不超过 2 MB</div>
    </el-upload>
  </div>
</template>

<script>
export default {
  name: 'TheExcelImport',
  data() {
    return {
      dialogConfig: {
        visible: false,
        loading: false,
        title: '导入',
        width: 500,
        footer: false,
      },
      accept: '.xlsx,.xls',
      pathPrefix: '/hello/word',
      templateName: '',
      templateKey: '',
    }
  },
  created() {
    // 从数据字典获取导入模版key
    this.$dict.getListByTypes(['excel_template_demo_user']).then(res => {
      if (res && res[0]) {
        this.templateName = res[0].label
        this.templateKey = res[0].value
      }
    })
  },
  methods: {
    open() {
      this.dialogConfig.visible = true
    },
    // 模版下载
    download() {
      this.$api.fileDownload(this.templateKey).then(blob => {
        this.$utils.downloadBlob(blob, this.templateName)
      })
    },
    uploadBefore(file) {
      // 判断文件尺寸
      const limit = 2
      if (file.size / 1024 / 1024 > limit) {
        this.$helper.alert(`文件大小不能超过 ${limit}MB!`)
        return false
      }
      // 判断文件类型
      let nameSuffix = file.name.split('.').pop().toLowerCase()
      if (this.accept !== '*' && this.accept.indexOf(nameSuffix) === -1) {
        this.$helper.alert(`不允许上传excel文件!`)
        return false
      }
    },
    httpRequest(param) {
      let file = param.file
      this.dialogConfig.loading = true
      // 先把文件上传到文件服务器，然后调用导入接口传入文件key
      this.$api
        .fileUpload(file, this.pathPrefix)
        .then(key => {
          return this.$http.post('/auth/demo/user/open/op/import', key)
        })
        .then(() => {
          this.$helper.successMessage('导入成功')
          this.dialogConfig.visible = false
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
  },
}
</script>

<style scoped></style>
