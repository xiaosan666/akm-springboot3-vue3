<template>
  <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false">
    <div v-text-center>
      <el-upload
        drag
        action="/"
        :accept="accept"
        :show-file-list="false"
        :before-upload="uploadBefore"
        :http-request="httpRequest"
      >
        <el-icon><Upload /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <template v-slot:tip>
          <div class="el-upload__tip">只能上传excel文件，且不超过 {{ size }} MB</div>
        </template>
      </el-upload>
    </div>
    <h3>模版下载</h3>
    <div v-text-center>
      <el-button link @click="download">
        <el-icon style="font-size: 18px"><Download /></el-icon>
        {{ templateName }}
      </el-button>
    </div>
  </akm-dialog>
</template>

<script>
import { Upload, Download } from '@element-plus/icons-vue'

export default {
  name: 'AkmExcelImport',
  components: {
    Upload,
    Download,
  },
  props: {
    // 导入接口地址
    uri: {
      type: String,
      required: true,
    },
    // 数据字典类型（根据该类型获取导入模版）
    templateDictType: {
      type: String,
      required: true,
    },
    // 允许上传的excel大小，单位M,默认2M
    size: {
      type: Number,
      default: 2,
    },
    // excel存储在的文件服务器的位置（导入操作是先上传excel到文件服务器，在把文件服务器的key给后端，后端通过key拿文件）
    pathPrefix: {
      type: String,
      default: '/excel/import',
    },
  },
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
      templateName: '',
      templateKey: '',
    }
  },
  created() {
    // 从数据字典获取导入模版key
    this.$dict.getListByTypes([this.templateDictType]).then(res => {
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
      if (file.size / 1024 / 1024 > this.size) {
        this.$helper.alert(`文件大小不能超过 ${this.size}MB!`)
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
          return this.$http.post(this.uri, key)
        })
        .then(data => {
          this.dialogConfig.visible = false
          this.$emit('success', data)
        })
        .finally(() => {
          this.dialogConfig.loading = false
        })
    },
  },
}
</script>
