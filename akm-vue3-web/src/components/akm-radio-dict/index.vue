<template>
  <el-radio-group v-model="radioValue" :disabled="disabled" @change="handleChange">
    <template v-if="button !== true">
      <el-radio v-for="item in dictList" :key="item.value" :value="item.value">
        {{ item.name || item.label }}
      </el-radio>
    </template>
    <template v-if="button === true">
      <el-radio-button v-for="item in dictList" :key="item.value" :value="item.value">
        {{ item.name || item.label }}
      </el-radio-button>
    </template>
  </el-radio-group>
</template>
<script>
export default {
  name: 'AkmRadioDict',
  props: {
    code: {
      type: [String, Number],
      request: true,
    },
    value: {
      type: [String, Number],
    },
    disabled: {
      type: Boolean,
      default() {
        return false
      },
    },
    button: {
      type: Boolean,
      default() {
        return false
      },
    },
  },
  data() {
    return {
      radioValue: this.value,
      dictList: [],
    }
  },
  watch: {
    value(val) {
      this.radioValue = val
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
      this.dictList.forEach(item => {
        item.value = typeof this.value === 'number' ? Number(item.value) : String(item.value)
      })
    },
  },
}
</script>
