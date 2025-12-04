<template>
  <div>
    <span style="color: red; margin-left: 10px; font-size: 12px">
      注：导入不是直接上传文件到后台接口，是先把excel上传到文件服务器，
      得到文件key，然后调用导入接口，服务端根据文件key下载excel导入数据
    </span>
    <el-divider content-position="left">自定义导入</el-divider>
    <the-excel-import></the-excel-import>
    <el-divider content-position="left">使用使用&lt;akm-excel-import&gt;组件导入</el-divider>
    <el-button type="primary" @click="excelImport">导入</el-button>
    <akm-excel-import
      ref="akmExcelImport"
      uri="/auth/demo/user/open/op/import"
      templateDictType="excel_template_demo_user"
      :size="1"
      pathPrefix="demo/import"
      @success="excelImportSuccess"
    ></akm-excel-import>
    <el-divider content-position="left">导出</el-divider>
    <el-button type="primary" :loading="loading" @click="excelExport">导出</el-button>
  </div>
</template>

<script>
import AkmExcelImport from '@/views/common/akm-excel-import/index.vue'
import TheExcelImport from '@/views/demo/excel/TheExcelImport.vue'
export default {
  name: 'DemoExcel',
  components: { TheExcelImport, AkmExcelImport },
  data() {
    return {
      loading: false,
    }
  },
  methods: {
    excelImport() {
      this.$refs.akmExcelImport.open()
    },
    excelImportSuccess() {
      this.$helper.successMessage('导入成功')
    },
    excelExport() {
      this.loading = true
      this.$http
        .postResponseBlob('/auth/demo/user/open/op/export', {})
        .then(blob => {
          this.$utils.downloadBlob(blob, 'demo导出列表.xlsx')
        })
        .finally(() => {
          this.loading = false
        })
    },
  },
}
</script>

<style scoped></style>
