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
    @clear="onClear"
  >
    <el-option
      v-for="(item, index) in options"
      :key="index"
      :label="item.name || item.label"
      :value="item.value"
    ></el-option>
  </el-select>
</template>
<script>
export default {
  name: 'AkmSelect',
  props: {
    options: {
      type: Array,
      required: true,
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
  methods: {
    handleChange(value) {
      this.$emit('update:value', value)
      this.$emit('change', value)
    },
    onClear() {
      this.$emit('clear')
    },
  },
}
</script>
