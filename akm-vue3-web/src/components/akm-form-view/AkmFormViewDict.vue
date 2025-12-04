<template>
  <span>{{ label }}</span>
</template>
<script>
export default {
  name: 'AkmFormViewDict',
  props: {
    code: {
      type: [String, Number],
      request: true,
    },
    value: {
      type: [String, Number],
    },
  },
  data() {
    return {
      dictList: [],
      label: '',
    }
  },
  watch: {
    value(val) {
      this.formatterValue(val)
    },
  },
  created() {
    if (this.code) {
      this.$dict.getListByTypes(this.code).then(res => {
        this.dictList = res
        this.formatterValue(this.value)
      })
    }
  },
  methods: {
    formatterValue(val) {
      let data = this.dictList.find(item => String(item.value) === String(val))
      if (data) {
        this.label = data.name || data.label
      }
    },
  },
}
</script>
