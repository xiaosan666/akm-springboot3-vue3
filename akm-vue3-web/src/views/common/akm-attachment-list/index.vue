<template>
  <div v-loading="loading" class="akm-attachment-list">
    <div v-for="item in data" :key="item.attachmentUrl" class="file-item">
      <img class="image" :src="getFileTypeByName(item.attachmentName)" alt="" />
      <div class="info">
        <div class="title" :title="item.attachmentName">
          <span>({{ $utils.formatFileSize(item.attachmentSize) }})</span>
          {{ item.attachmentName }}
        </div>
        <div class="createTime">
          {{ $utils.formatDate(item.createTime, 'yyyy-MM-dd HH:mm:ss') }}
        </div>
        <div class="op">
          <akm-warn-btn v-if="showDelBtn" @confirm="del(item)">删除</akm-warn-btn>
          <span v-padding-left="8" v-padding-right="8" @click="download(item, true)">下载</span>
          <span v-if="isImage(item)" @click="view(item)">预览</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Vite 预加载图片资源（只执行一次）
const fileIconImages = import.meta.glob('@/assets/icon/file/*.png', {
  eager: true,
  import: 'default',
})
// 构建图标映射表
const fileIconMap = {}
Object.keys(fileIconImages).forEach(path => {
  // 从路径中提取文件名（不含扩展名）作为 key
  const filename = path.split('/').pop().replace('.png', '')
  fileIconMap[filename] = fileIconImages[path]
})

export default {
  name: 'AkmAttachmentList',
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
      loading: false,
    }
  },
  methods: {
    del(row) {
      let delIndex = this.data.findIndex(item => item.attachmentUrl === row.attachmentUrl)
      this.data.splice(delIndex, 1)
      this.$emit('del', this.data)
    },
    download(row) {
      this.loading = true
      this.$api
        .fileDownload(row.attachmentUrl)
        .then(blob => {
          this.$utils.downloadBlob(blob, row.attachmentName)
        })
        .finally(() => {
          this.loading = false
        })
    },
    view(row) {
      this.loading = true
      this.$api
        .getFileUrl(row.attachmentUrl)
        .then(url => {
          window.open(url, '_blank')
        })
        .finally(() => {
          this.loading = false
        })
    },
    isImage(row) {
      return this.$utils.isImage(row.attachmentUrl)
    },
    getFileTypeByName(filename) {
      // 提取扩展名（从最后一个点之后的部分）
      const extension = filename.split('.').pop().toLowerCase()
      // 映射表：扩展名 -> 文件类型
      const fileTypeMap = {
        jpg: 'image',
        jpeg: 'image',
        png: 'image',
        gif: 'image',
        webp: 'image',
        tif: 'image',
        bmp: 'image',
        dwg: 'image',
        xls: 'excel',
        xlsx: 'excel',
        doc: 'word',
        docx: 'word',
        pdf: 'pdf',
        zip: 'zip',
      }
      // 根据扩展名返回文件类型图标
      const type = fileTypeMap[extension] || 'file'
      return fileIconMap[type] || fileIconMap['file']
    },
  },
}
</script>

<style lang="scss" scoped>
.akm-attachment-list {
  display: flex;
  flex-wrap: wrap;
  .file-item {
    line-height: 1.4em;
    display: flex;
    align-items: center;
    .image {
      width: 80px;
      height: 80px;
      margin: 10px;
    }
    .info {
      font-size: 13px;
      width: 140px;
      color: #222;
      .title {
        overflow: hidden;
        text-overflow: ellipsis;
        word-break: break-all;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
      }
      .createTime {
        color: #666;
        padding: 4px 0;
      }
      .op {
        color: #1e87f0;
        > span {
          cursor: pointer;
        }
      }
      .del {
        color: #f56c6c;
      }
    }
  }
}
</style>
