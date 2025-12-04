<template>
  <div>
    <akm-province-city-cascade
      style="width: 400px"
      :provinceCode="provinceCode"
      :cityCode="cityCode"
      :areaCode="areaCode"
      clearable
      @change="onValueChange"
    ></akm-province-city-cascade>
    <p>{{ result }}</p>
    <p style="color: red">
      注：若业务表需要记录省市区数据，建议业务表保存6个字段，方便查询与显示
      <br />
      6个字段分别为：provinceCode、 cityCode、 areaCode、 provinceName、 cityName、 areaName
    </p>
  </div>
</template>

<script>
import AkmProvinceCityCascade from '@/views/common/akm-province-city-cascade/index.vue'
export default {
  name: 'DemoDistrict',
  components: { AkmProvinceCityCascade },
  data() {
    return {
      provinceCode: '440000',
      cityCode: '441800',
      areaCode: '441821',
      result: '',
    }
  },
  methods: {
    onValueChange(data) {
      if (!data.provinceCode) {
        this.$helper.warningMessage('清空了')
      } else {
        this.$helper.successMessage(data.areaName || data.cityName)
      }
      this.result = data
    },
  },
}
</script>

<style scoped></style>
