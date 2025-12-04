<template>
  <div class="map-warp">
    <!-- eharts   -->
    <the-echarts></the-echarts>
    <!--  高德地图 -->
    <the-amap></the-amap>
  </div>
</template>

<script>
import TheEcharts from '@/views/demo/map-area/TheEcharts.vue'
import TheAmap from '@/views/demo/map-area/TheAmap.vue'

export default {
  name: 'DemoMapArea',
  components: { TheAmap, TheEcharts },
}
</script>

<style scoped>
.map-warp {
  height: 100%;
  display: flex;
}
</style>
