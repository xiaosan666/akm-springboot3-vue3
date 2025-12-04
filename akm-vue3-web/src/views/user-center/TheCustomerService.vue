<template>
  <div>
    <div v-if="tableConfig.data.length > 0" class="contact-warp">
      <span>客服热线 - {{ tableConfig.data[0].name }}：{{ tableConfig.data[0].value }}</span>
      <el-button
        v-margin-left
        type="primary"
        plain
        icon="More"
        size="small"
        @click="dialogConfig.visible = true"
      >
        全部客服热线
      </el-button>
    </div>
    <!--客服热线dialog-->
    <akm-dialog :config="dialogConfig">
      <akm-table :config="tableConfig">
        <el-table-column class-name="op-buttons" label="操作" width="100">
          <template v-slot="{ row }">
            <el-button link @click="copyToClip(row.value)">复制</el-button>
          </template>
        </el-table-column>
      </akm-table>
    </akm-dialog>
  </div>
</template>

<script>
import { More } from '@element-plus/icons-vue'

export default {
  name: 'TheCustomerService',
  components: {
    More,
  },
  data() {
    return {
      dialogConfig: {
        visible: false,
        title: '全部客服热线',
        width: 500,
        footer: false,
      },
      tableConfig: {
        data: [
          {
            name: '北京',
            value: '18688490000',
          },
          {
            name: '上海',
            value: '18688490000',
          },
          {
            name: '广州',
            value: '18688490000',
          },
          {
            name: '西安',
            value: '18688490000',
          },
        ], // 联系人电话
        columns: [
          { prop: 'name', label: '各分子公司' },
          { prop: 'value', label: '一线客服热线' },
        ],
      },
    }
  },
  methods: {
    /**
     * 复制内容到粘贴板
     * content : 需要复制的内容
     */
    async copyToClip(content) {
      try {
        if (navigator.clipboard && window.isSecureContext) {
          // 使用现代 Clipboard API
          await navigator.clipboard.writeText(content)
        } else {
          // 降级到传统方法
          let aux = document.createElement('input')
          aux.setAttribute('value', content)
          document.body.appendChild(aux)
          aux.select()
          document.execCommand('copy')
          document.body.removeChild(aux)
        }
        this.$helper.successMessage('复制成功')
      } catch (error) {
        console.error('复制失败:', error)
        this.$helper.errorMessage('复制失败，请手动复制')
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.contact-warp {
  color: #a7a7a7;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  margin: 0 auto;
  cursor: pointer;
}
</style>
