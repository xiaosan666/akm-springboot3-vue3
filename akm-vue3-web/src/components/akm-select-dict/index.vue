<template>
  <el-select
    style="width: 100%"
    :model-value="value"
    filterable
    :clearable="clearable"
    :placeholder="placeholder"
    :disabled="disabled"
    :multiple="multiple"
    :collapse-tags="collapseTags"
    @change="handleChange"
  >
    <el-option
      v-for="(item, index) in dictList"
      :key="index"
      :label="item.name || item.label"
      :value="item.value"
    ></el-option>
  </el-select>
</template>
<script>
export default {
  name: 'AkmSelectDict',
  props: {
    code: {
      type: [String, Number],
      request: true,
    },
    value: {
      type: [String, Number, Array],
    },
    placeholder: {
      type: String,
      default() {
        return '请选择'
      },
    },
    disabled: {
      type: Boolean,
      default() {
        return false
      },
    },
    multiple: {
      type: Boolean,
      default() {
        return false
      },
    },
    // 多选时是否将选中值按文字的形式展示
    collapseTags: {
      type: Boolean,
      default() {
        return false
      },
    },
    clearable: {
      type: Boolean,
      default() {
        return true
      },
    },
  },
  data() {
    return {
      dictList: [],
    }
  },
  watch: {
    value() {
      this.formatterDictList()
    },
  },
  created() {
    if (this.code) {
      this.$dict.getListByTypes(this.code).then(res => {
        this.dictList = res
        this.formatterDictList()
      })
    }
  },
  methods: {
    handleChange(value) {
      this.$emit('update:value', value)
      this.$emit('change', value)
    },
    // 根据value类型转换列表类型
    formatterDictList() {
      if (this.value === undefined || this.value === '') {
        return
      }
      let isNumberValue = false
      if (
        typeof this.value === 'number' ||
        (this.$utils.isArray(this.value) && this.value.length && typeof this.value[0] === 'number')
      ) {
        isNumberValue = true
      }
      this.dictList.forEach(item => {
        item.value = isNumberValue ? Number(item.value) : String(item.value)
      })
    },
  },
}
</script>
