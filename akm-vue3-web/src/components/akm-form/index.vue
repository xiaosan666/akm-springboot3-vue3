<template>
  <div :class="['akm-form-warp', { 'form-border': formConfig.showBorder }]">
    <div v-if="formConfig.showMoreBtn" class="search-more-btn" @click="clickSearchMore">
      {{ switchSearchMore ? '收起' : '展开' }}
    </div>
    <el-form
      ref="form"
      v-loading="formConfig.loading"
      :class="['akm-form', { 'hide-search-more': formConfig.showMoreBtn }, formConfig.className]"
      :inline="formConfig.inline"
      :model="formConfig.data"
      :rules="formConfig.rules"
      :label-width="labelWidth"
      :label-position="formConfig.labelPosition"
      :disabled="formConfig.disabled"
      @keydown.enter="query"
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
        <template v-if="item.type === 'view'">
          <span>{{ formConfig.data[item.prop] }}</span>
        </template>

        <!-- 普通输入框 -->
        <el-input
          v-if="!item.type || item.type === 'input'"
          v-model.trim="formConfig.data[item.prop]"
          :disabled="item.disabled"
          :placeholder="item.placeholder"
          clearable
        />

        <!-- 文本输入框 -->
        <el-input
          v-if="item.type === 'textarea'"
          v-model.trim="formConfig.data[item.prop]"
          :type="item.type"
          :disabled="item.disabled"
          :placeholder="item.placeholder"
          :autosize="{ minRows: 2, maxRows: 10 }"
          :maxlength="item.maxLength"
          :show-word-limit="item.maxLength && item.maxLength > 0"
          clearable
        />

        <!-- 计数器 -->
        <el-input-number
          v-if="item.type === 'inputNumber'"
          v-model="formConfig.data[item.prop]"
          :min="item.min"
          :max="item.max"
          :step="item.step"
          :disabled="item.disabled"
          :placeholder="item.placeholder"
        />

        <!-- 下拉选择框 -->
        <akm-select
          v-if="item.type === 'select'"
          :options="item.options"
          v-model:value="formConfig.data[item.prop]"
          :multiple="item.multiple"
          :disabled="item.disabled"
          :clearable="item.clearable"
          :placeholder="item.placeholder"
          :collapseTags="item.collapseTags"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        ></akm-select>

        <!-- 下拉选择框：dict -->
        <akm-select-dict
          v-if="item.type === 'dict'"
          :code="item.code"
          v-model:value="formConfig.data[item.prop]"
          :multiple="item.multiple"
          :disabled="item.disabled"
          :clearable="item.clearable"
          :placeholder="item.placeholder"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        ></akm-select-dict>

        <!-- 单选 -->
        <el-radio-group
          v-if="item.type === 'radio'"
          v-model="formConfig.data[item.prop]"
          :disabled="item.disabled"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        >
          <el-radio v-for="item in item.options" :key="item.value" :label="item.value">
            {{ item.name || item.label }}
          </el-radio>
        </el-radio-group>

        <!-- 单选按钮 -->
        <el-radio-group
          v-if="item.type === 'radioButton'"
          v-model="formConfig.data[item.prop]"
          :disabled="item.disabled"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        >
          <el-radio-button v-for="item in item.options" :key="item.value" :label="item.value">
            {{ item.name || item.label }}
          </el-radio-button>
        </el-radio-group>

        <!-- 单选：dict -->
        <akm-radio-dict
          v-if="item.type === 'radioDict'"
          :code="item.code"
          v-model:value="formConfig.data[item.prop]"
          :disabled="item.disabled"
          :button="false"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        ></akm-radio-dict>

        <!-- 单选按钮：dict -->
        <akm-radio-dict
          v-if="item.type === 'radioButtonDict'"
          :code="item.code"
          v-model:value="formConfig.data[item.prop]"
          :disabled="item.disabled"
          :button="true"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        ></akm-radio-dict>

        <!-- 复选框组 -->
        <el-checkbox-group
          v-if="item.type === 'checkbox'"
          v-model="formConfig.data[item.prop]"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        >
          <el-checkbox
            v-for="item in item.options"
            :key="item.value"
            :disabled="item.isDisabled"
            :label="item.value"
          >
            {{ item.label }}
          </el-checkbox>
        </el-checkbox-group>

        <!-- 日期选择框 -->
        <el-date-picker
          v-if="item.type === 'date'"
          v-model="formConfig.data[item.prop]"
          type="date"
          :value-format="item.valueFormat || 'yyyy-MM-dd'"
          clearable
          :disabled="item.disabled"
          :placeholder="item.placeholder ? item.placeholder : '请选择日期'"
          :picker-options="item.pickerOptions ? item.pickerOptions : {}"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        />

        <!-- 日期选择框 -->
        <el-date-picker
          v-if="item.type === 'daterange'"
          v-model="formConfig.data[item.prop]"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          clearable
          :disabled="item.disabled"
          :value-format="item.valueFormat"
          :picker-options="item.pickerOptions ? item.pickerOptions : {}"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        />

        <!-- 时间选择器 -->
        <el-time-picker
          v-if="item.type === 'time'"
          v-model="formConfig.data[item.prop]"
          clearable
          :disabled="item.disabled"
          :placeholder="item.placeholder ? item.placeholder : '请选择时间'"
        />

        <!-- 月份选择器 -->
        <el-date-picker
          v-if="item.type === 'month'"
          v-model="formConfig.data[item.prop]"
          type="month"
          :value-format="item.format || 'yyyy-MM'"
          :clearable="item.clearable === undefined || item.clearable"
          :disabled="item.disabled"
          :placeholder="item.placeholder ? item.placeholder : '请选择月份'"
        />

        <!-- 年选择器 -->
        <el-date-picker
          v-if="item.type === 'year'"
          v-model="formConfig.data[item.prop]"
          type="year"
          :value-format="item.format || 'yyyy'"
          :clearable="item.clearable === undefined || item.clearable"
          :disabled="item.disabled"
          :placeholder="item.placeholder ? item.placeholder : '请选择年'"
        />

        <!-- 日期时间选择器 -->
        <el-date-picker
          v-if="item.type === 'datetime'"
          v-model="formConfig.data[item.prop]"
          type="datetime"
          clearable
          :placeholder="item.placeholder ? item.placeholder : '请选择日期时间'"
        />

        <!-- 日期和时间范围选择器  -->
        <el-date-picker
          v-if="item.type === 'datetimerange'"
          v-model="formConfig.data[item.prop]"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          clearable
          :disabled="item.disabled"
          @change="handleEvent(item.event, formConfig.data[item.prop])"
        />

        <!-- 月份范围选择框 -->
        <el-date-picker
          v-if="item.type === 'monthrange'"
          v-model="formConfig.data[item.prop]"
          type="monthrange"
          value-format="yyyy-MM"
          range-separator="~"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          clearable
          :disabled="item.disabled"
          :picker-options="item.pickerOptions ? item.pickerOptions : {}"
        />

        <!-- 开关 -->
        <el-switch
          v-if="item.type === 'switch'"
          v-model="formConfig.data[item.prop]"
          :disabled="item.disabled"
          :active-value="item.activeValue !== undefined ? item.activeValue : 1"
          :inactive-value="item.inactiveValue !== undefined ? item.inactiveValue : 0"
        />
      </el-form-item>
      <el-form-item v-if="formConfig.showQueryInline">
        <div style="display: flex">
          <el-button v-margin-left type="primary" icon="Search" @click="query">查询</el-button>
          <el-button v-margin-left type="warning" icon="RefreshLeft" @click="reset">
            重置
          </el-button>
        </div>
      </el-form-item>
    </el-form>
    <div v-if="formConfig.showQuery" v-text-center>
      <el-button type="primary" icon="Search" @click="query">查询</el-button>
      <el-button type="warning" icon="RefreshLeft" @click="reset">重置</el-button>
    </div>
    <!--自定义内容，如操作按钮-->
    <slot></slot>
  </div>
</template>
<script>
import { Search as SearchIcon, RefreshLeft } from '@element-plus/icons-vue'

export default {
  name: 'AkmForm',
  components: {
    SearchIcon,
    RefreshLeft,
  },
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
  data() {
    return {
      switchSearchMore: false,
    }
  },
  computed: {
    formConfig() {
      // 表单参数默认值
      let defaultConfig = {
        loading: false, // 是否显示loading
        inline: false, // 表单内容是否一行显示
        showQuery: false, // 是否显示查询按钮
        showQueryInline: false, // 是否显示查询按钮
        showBorder: false, // 是否显示边框
        showMoreBtn: false, // 是否显示“更多搜索”查询按钮，为true则查询条件只显示一行
        className: '', // 自定义class
        data: {}, // 表单绑定的数据对象
        rules: {}, // 表单验证规则
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
    resetFields() {
      this.$refs.form.resetFields()
    },
    query() {
      this.$emit('query', this.formConfig.data)
    },
    reset() {
      this.resetFields()
      this.$emit('reset')
    },
    clickSearchMore() {
      let formEl = document.getElementsByClassName('akm-form')[0]
      toggleClass(formEl, 'hide-search-more')
      this.switchSearchMore = !this.switchSearchMore
      this.$eventBus.emit(this.$eventBus.keys.heightChange)
    },
    // 绑定的相关事件
    handleEvent(event, value) {
      if (event) {
        this.$emit('handleEvent', event, value)
      }
    },
  },
}

/* begin:dom class操作工具方法 */
function hasClass(obj, cls) {
  return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'))
}

function addClass(obj, cls) {
  if (!hasClass(obj, cls)) obj.className += ' ' + cls
}

function removeClass(obj, cls) {
  if (hasClass(obj, cls)) {
    let reg = new RegExp('(\\s|^)' + cls + '(\\s|$)')
    obj.className = obj.className.replace(reg, ' ')
  }
}

function toggleClass(obj, cls) {
  if (hasClass(obj, cls)) {
    removeClass(obj, cls)
  } else {
    addClass(obj, cls)
  }
}

/* end:dom class操作工具方法 */
</script>
<style scoped lang="scss">
.search-more-btn {
  position: absolute;
  right: 10px;
  top: 2px;
  color: #0000ff;
  text-decoration: underline;
  cursor: pointer;
  z-index: 1;
  &:hover {
    color: #1e87f0;
  }
}

.hide-search-more {
  height: 55px;
}
</style>
