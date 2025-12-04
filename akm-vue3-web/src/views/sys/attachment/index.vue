<template>
  <div>
    <akm-form
      ref="form"
      :config="formConfig"
      :data="formData"
      @query="query"
      @reset="reset"
    ></akm-form>

    <akm-table ref="table" :config="tableConfig" @selectedChange="onSelectedChange">
      <template #toolbar>
        <el-button
          v-has="'sys_attachment_del'"
          type="danger"
          :disabled="!selectedList.length"
          @click="batchDel"
        >
          删除
        </el-button>
        <el-button v-has="'sys_attachment_add'" type="primary" @click="addOrEdit">新增</el-button>
      </template>
      <template #column-op="{ row }">
        <akm-warn-btn v-has="'sys_attachment_del'" @confirm="del([row.id])">删除</akm-warn-btn>
        <el-button v-has="'sys_attachment_edit'" type="primary" link @click="addOrEdit(row)">
          编辑
        </el-button>
        <el-button v-has="'sys_attachment_edit'" type="primary" link @click="download(row)">
          下载
        </el-button>
      </template>
    </akm-table>

    <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
      <akm-form ref="form" :config="dialogFormConfig">
        <template #form-file>
          <el-upload
            action="/"
            :show-file-list="false"
            :before-upload="uploadBefore"
            :http-request="httpRequest"
          >
            <el-button type="primary" :loading="dialogFormConfig.loading">点击上传</el-button>
          </el-upload>
        </template>
        <template #form-attachmentName>
          <div class="akm-flex-space-between">
            <el-input
              v-model.trim="dialogFormConfig.data.attachmentName"
              placeholder="请输入文件名称"
              clearable
              style="width: 260px"
            ></el-input>
            <el-button
              v-margin-left
              type="success"
              :disabled="!dialogFormConfig.data.attachmentName"
              :loading="dialogFormConfig.loading"
              @click="download(dialogFormConfig.data)"
            >
              下载
            </el-button>
          </div>
        </template>
      </akm-form>
    </akm-dialog>
  </div>
</template>

<script>
export default {
  name: 'SysAttachment',
  data() {
    return {
      formData: {
        attachmentName: '',
        attachmentUrl: '',
        recordType: '',
        recordId: '',
      },

      formConfig: {
        inline: true,
        showQueryInline: true,
        showBorder: true,
        labelWidth: 140,
        items: [
          { type: 'input', prop: 'attachmentName', label: '文件名称' },
          { type: 'input', prop: 'attachmentUrl', label: '文件路径' },
          { type: 'input', prop: 'recordType', label: '业务场景分类标志' },
          { type: 'input', prop: 'recordId', label: '业务表主键id' },
        ],
      },

      selectedList: [],
      tableConfig: {
        uri: '/share/public/biz/attachment/view/findPage',
        loading: false,
        autoComputedHeight: true,
        multipleSelect: true,
        pagination: true,
        pageSize: 50,
        columns: [
          {
            prop: 'attachmentName',
            label: '文件名称（原文件名称）',
            showOverflowTooltip: true,
          },
          {
            prop: 'attachmentUrl',
            label: '文件路径',
            showOverflowTooltip: true,
          },
          {
            prop: 'attachmentSize',
            label: '文件大小',
            width: 150,
            formatter: (row, column, cellValue) => this.$utils.formatFileSize(cellValue),
          },
          {
            prop: 'createTime',
            label: '创建时间',
            width: 170,
            formatter: (row, column, cellValue) =>
              this.$utils.formatDate(cellValue, 'yyyy-MM-dd HH:mm:ss'),
          },
          { prop: 'recordType', label: '业务场景分类标志' },
          { prop: 'recordId', label: '业务表主键id', width: 200 },
          { type: 'slot', prop: 'op', label: '操作' },
        ],
      },

      dialogConfig: {
        visible: false,
        loading: false,
        title: '新增',
        width: 700,
      },
      dialogFormConfig: {
        loading: false,
        labelWidth: 150,
        data: {
          id: '',
          attachmentName: '',
          attachmentSize: '',
          attachmentUrl: '',
          recordType: '',
          recordId: '',
          dir: 'sys_attachment',
        },
        items: [
          {
            type: 'input',
            prop: 'dir',
            label: '文件存储目录',
            disabled: false,
            placeholder: '请输入文件存储目录，默认：sys_attachment',
          },
          { type: 'slot', prop: 'file', label: '选择文件' },
          {
            type: 'slot',
            prop: 'attachmentName',
            label: '文件名称',
          },
          {
            type: 'input',
            prop: 'attachmentUrl',
            label: '文件路径',
            disabled: true,
          },
          { type: 'input', prop: 'recordType', label: '业务场景分类标志' },
          { type: 'input', prop: 'recordId', label: '业务表主键id' },
        ],
        rules: {
          dir: [{ required: true, message: '请输入文件存储目录', trigger: 'blur' }],
        },
      },
    }
  },
  mounted() {
    this.query()
  },
  methods: {
    query() {
      this.tableConfig.pageNum = 1
      this.$refs.table.fetchTableData(this.formData)
    },
    reset() {
      this.$refs.form.resetFields()
      this.query()
    },
    onSelectedChange(selectedList) {
      this.selectedList = selectedList
    },
    addOrEdit(row) {
      this.$helper.createCsrfToken()
      let dirItem = this.dialogFormConfig.items.find(it => it.prop === 'dir')
      if (row && row.id) {
        this.dialogFormConfig.data = this.$utils.clone(row)
        dirItem.disabled = true
        // 删除url最后的实际文件名，得到保存路径
        let urlArr = row.attachmentUrl.split('/')
        let dirArr = urlArr.splice(0, urlArr.length - 1)
        this.dialogFormConfig.data.dir = dirArr.join('/')
      } else {
        dirItem.disabled = false
        this.dialogFormConfig.data.id = ''
        this.dialogFormConfig.data.dir = 'sys_attachment'
      }
      this.dialogConfig.title = row.id ? '编辑' : '新增'
      this.dialogConfig.visible = true
    },
    confirm() {
      this.$refs.form.getForm().validate(valid => {
        if (valid) {
          this.dialogConfig.loading = true
          let data = this.dialogFormConfig.data
          this.$http
            .post('/share/public/biz/attachment/op/insertOrUpdate', data)
            .then(() => {
              this.$helper.successMessage('保存成功')
              this.$refs.form.resetFields()
              this.query()
              this.dialogConfig.visible = false
            })
            .catch(() => {
              this.$helper.createCsrfToken()
            })
            .finally(() => {
              this.dialogConfig.loading = false
            })
        }
      })
    },
    batchDel() {
      let list = this.selectedList
      this.$helper
        .confirm(`即将删除${list.length}条记录，确定删除吗？`)
        .then(() => {
          this.del(list.map(item => item.id))
        })
        .catch(() => {})
    },
    del(ids) {
      this.tableConfig.loading = true
      this.$http
        .post('/share/public/biz/attachment/op/batchDel', ids)
        .then(() => {
          this.$helper.successMessage('删除成功')
          this.query()
        })
        .finally(() => {
          this.tableConfig.loading = false
        })
    },
    uploadBefore(file) {
      // 这里需要判断文件尺寸、类型、数量，参考akm-attachment-upload组件
    },
    httpRequest(param) {
      let file = param.file
      this.dialogFormConfig.loading = true
      this.$api
        .fileUpload(file, this.dialogFormConfig.data.dir || '')
        .then(key => {
          this.dialogFormConfig.data.attachmentName = param.file.name
          this.dialogFormConfig.data.attachmentUrl = key
          this.dialogFormConfig.data.attachmentSize = param.file.size
          this.$helper.successMessage('上传成功')
        })
        .finally(() => {
          this.dialogFormConfig.loading = false
        })
    },
    download(data) {
      this.$api.getFileUrl(data.attachmentUrl).then(url => {
        const downloadElement = document.createElement('a')
        downloadElement.href = url
        downloadElement.target = '_blank'
        downloadElement.download = data.attachmentName
        document.body.appendChild(downloadElement)
        downloadElement.click() // 点击下载
        document.body.removeChild(downloadElement)
      })
    },
  },
}
</script>

<style lang="scss" scoped></style>
