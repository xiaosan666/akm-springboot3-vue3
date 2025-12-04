<template>
  <el-form
    ref="form"
    v-loading="formConfig.loading"
    :class="['akm-form-view', { 'form-border': formConfig.showBorder }, formConfig.className]"
    :inline="formConfig.inline"
    :model="formConfig.data"
    :label-width="labelWidth"
    :label-position="formConfig.labelPosition"
  >
    <el-form-item
      v-for="(item, index) in formConfig.items.filter(i => i.hide !== true)"
      :key="index"
      :prop="item.prop"
      :label="item.label ? item.label + '：' : ''"
      :class="item.className"
    >
      <!-- solt -->
      <template v-if="item.type === 'slot'">
        <slot :name="'form-' + item.prop" />
      </template>

      <!-- view -->
      <template v-if="!item.type || item.type === 'view'">
        <span style="word-break: break-all">
          {{ formConfig.data[item.prop] }}
        </span>
      </template>

      <akm-form-view-dict
        v-if="item.type === 'dict'"
        :code="item.code"
        :value="formConfig.data[item.prop]"
      ></akm-form-view-dict>

      <akm-form-view-select
        v-if="item.type === 'select'"
        :options="item.options"
        :value="formConfig.data[item.prop]"
      ></akm-form-view-select>

      <!-- 开关 -->
      <el-switch
        v-if="item.type === 'switch'"
        v-model="formConfig.data[item.prop]"
        :disabled="item.disabled"
        :active-value="item.activeValue !== undefined ? item.activeValue : 1"
        :inactive-value="item.inactiveValue !== undefined ? item.inactiveValue : 0"
      />

      <!-- date -->
      <template v-if="item.type === 'date'">
        <span>
          {{ $utils.formatDate(formConfig.data[item.prop], 'yyyy-MM-dd') }}
        </span>
      </template>

      <!-- datetime -->
      <template v-if="item.type === 'datetime'">
        <span>
          {{ $utils.formatDate(formConfig.data[item.prop], 'yyyy-MM-dd HH:mm:ss') }}
        </span>
      </template>
    </el-form-item>

    <!--自定义内容-->
    <slot name="form-content"></slot>
  </el-form>
</template>
<script>
import AkmFormViewDict from '@/components/akm-form-view/AkmFormViewDict.vue'
import AkmFormViewSelect from '@/components/akm-form-view/AkmFormViewSelect.vue'

export default {
  name: 'AkmFormView',
  components: { AkmFormViewSelect, AkmFormViewDict },
  props: {
    config: {
      type: Object,
      required: true,
      default: () => {},
    },
    data: {
      type: Object,
      required: false,
    },
  },
  computed: {
    // 表格参数默认值
    formConfig() {
      let defaultConfig = {
        loading: false, // 是否显示loading
        inline: false, // 表单内容是否一行显示
        className: '', // 自定义class
        data: {}, // 表单绑定的数据对象
        labelPosition: 'right', // label显示位置， right/left/top
        labelWidth: 110, // label宽度
        items: [], // 表单内容项
      }
      let config = { ...defaultConfig, ...this.config }
      if (this.data) {
        config.data = this.data
      }
      return config
    },
    labelWidth() {
      let width = this.formConfig.labelWidth
      if (!width) {
        return '110px'
      }
      return this.$utils.isNumber(width) ? width + 'px' : width
    },
  },
  methods: {
    getForm() {
      return this.$refs.form
    },
  },
}
</script>
<style lang="scss">
.akm-form-view {
  .el-form-item {
    margin-bottom: 0;
  }
}
</style>
