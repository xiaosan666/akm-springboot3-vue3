<template>
  <el-cascader
    :value="value"
    :props="{ value: 'adCode', label: 'name' }"
    :options="districtTree"
    :disabled="disabled"
    :clearable="clearable"
    @change="onValueChange"
  ></el-cascader>
</template>

<script>
export default {
  name: 'AkmProvinceCityCascade',
  props: {
    provinceCode: {
      type: String,
    },
    cityCode: {
      type: String,
    },
    areaCode: {
      type: String,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    clearable: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      districtList: [],
      districtTree: [],
    }
  },
  computed: {
    value() {
      return this.provinceCode && this.cityCode && this.areaCode
        ? [this.provinceCode, this.cityCode, this.areaCode]
        : []
    },
  },
  created() {
    this.initData()
  },
  methods: {
    initData() {
      this.$http.post('/share/public/biz/district', null, { cacheData: true }).then(res => {
        this.districtList = res
        this.districtTree = this.listToTree(res, '100000')
      })
    },
    listToTree(list, parentId) {
      let nodes = list.filter(i => i.parentAdCode === parentId)
      nodes.forEach(i => {
        let childrenNodes = this.listToTree(list, i['adCode'])
        if (childrenNodes && childrenNodes.length) {
          i.children = childrenNodes
        }
      })
      return nodes
    },
    onValueChange(values) {
      if (values.length === 0) {
        this.$emit('change', {
          provinceCode: '',
          cityCode: '',
          areaCode: '',
          provinceName: '',
          cityName: '',
          areaName: '',
        })
      } else {
        this.$emit('change', {
          provinceCode: values[0],
          cityCode: values[1],
          areaCode: values[2] || '',
          provinceName: this.districtList.find(i => i.adCode === values[0]).name,
          cityName: this.districtList.find(i => i.adCode === values[1]).name,
          areaName: values[2] ? this.districtList.find(i => i.adCode === values[2]).name : '',
        })
      }
    },
  },
}
</script>

<style scoped></style>
