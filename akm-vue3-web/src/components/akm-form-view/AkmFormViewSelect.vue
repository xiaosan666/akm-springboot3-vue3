<template>
  <span>{{ label }}</span>
</template>
<script>
export default {
  name: 'AkmFormViewSelect',
  props: {
    options: {
      type: Array,
      required: true,
    },
    value: {
      type: [String, Number],
    },
  },
  data() {
    return {
      label: '',
    }
  },
  watch: {
    value(val) {
      this.formatterValue(val)
    },
  },
  methods: {
    formatterValue(val) {
      let data = this.options.find(item => String(item.value) === String(val))
      if (data) {
        this.label = data.name || data.label
      }
    },
  },
}
</script>
