<template>
  <akm-table ref="table" :config="tableConfig" :data="data">
    <el-table-column class-name="op-buttons" label="操作" fixed="right">
      <template v-slot="{ row }">
        <akm-warn-btn v-if="showDelBtn" @confirm="del(row)">删除</akm-warn-btn>
        <el-button link @click="download(row, true)">下载</el-button>
        <el-button v-if="isImage(row)" link @click="view(row)">预览</el-button>
      </template>
    </el-table-column>
  </akm-table>
</template>

<script>
export default {
  name: 'AkmAttachmentTable',
  props: {
    data: {
      type: Array,
      default() {
        return []
      },
    },
    showDelBtn: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      tableConfig: {
        loading: false,
        rowKey: 'attachmentUrl',
        columns: [
          { prop: 'attachmentName', label: '附件名称' },
          {
            prop: 'attachmentSize',
            label: '附件大小',
            formatter: (row, column, cellValue) => this.$utils.formatFileSize(cellValue),
          },
          {
            prop: 'createTime',
            label: '创建时间',
            formatter: (row, column, cellValue) =>
              this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          },
        ],
      },
    }
  },
  methods: {
    del(row) {
      let delIndex = this.data.findIndex(item => item.attachmentUrl === row.attachmentUrl)
      this.data.splice(delIndex, 1)
      this.$emit('del', this.data)
    },
    download(row) {
      this.tableConfig.loading = true
      this.$api
        .fileDownload(row.attachmentUrl)
        .then(blob => {
          this.$utils.downloadBlob(blob, row.attachmentName)
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
    view(row) {
      this.tableConfig.loading = true
      this.$api
        .getFileUrl(row.attachmentUrl)
        .then(url => {
          window.open(url, '_blank')
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
    isImage(row) {
      return this.$utils.isImage(row.attachmentUrl)
    },
  },
}
</script>

<style scoped></style>
