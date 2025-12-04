<template>
  <div>
    <el-divider content-position="left">直接显示地图</el-divider>
    <akm-map-location
      style="width: 800px; height: 500px"
      :config="mapConfig"
      @location-change="onLocationChange"
    ></akm-map-location>
    <div v-if="data">位置改变：{{ data }}</div>
    <el-divider content-position="left">使用dialog显示地图</el-divider>
    <el-button type="primary" @click="openDialog">选择位置</el-button>
    <div v-if="dialogMapData">位置改变：{{ dialogMapData }}</div>
    <akm-dialog :config="dialogConfig" @cancel="dialogConfig.visible = false" @confirm="confirm">
      <akm-map-location
        style="width: 100%; height: 500px"
        :config="dialogMapConfig"
        @location-change="dialogMapLocationChange"
      ></akm-map-location>
    </akm-dialog>
  </div>
</template>

<script>
import AkmMapLocation from '@/views/common/akm-map-location/index.vue'

export default {
  name: 'DemoMap',
  components: { AkmMapLocation },
  data() {
    return {
      mapConfig: {
        id: 1,
        showSearch: true, // 是否显示查询输入框
        location: {
          lng: '116.394276', // 初始化经度
          lat: '39.937028', // 初始化纬度
        },
        click: true, // 是否可以点击地图切换点
      },
      data: null,

      dialogMapConfig: {
        id: 2,
        showSearch: true, // 是否显示查询输入框
        location: {
          lng: '', // 初始化经度
          lat: '', // 初始化纬度
        },
        click: true, // 是否可以点击地图切换点
      },
      dialogConfig: {
        visible: false,
        loading: false,
        title: '选择位置',
        width: 800,
      },
      dialogMapData: null,
    }
  },
  mounted() {
    // 初始化“我的”位置
    this.mapConfig.location = {
      lng: '113.33178',
      lat: '23.131939',
    }
  },
  methods: {
    // 位置改变
    onLocationChange(data) {
      this.data = data
    },
    // 位置改变
    dialogMapLocationChange(data) {
      this.dialogMapData = data
    },
    openDialog() {
      this.dialogConfig.visible = true
    },
    confirm() {
      if (!this.dialogMapData) {
        this.$helper.warningMessage('您未选择新地址！')
        return
      }
      this.$helper.successMessage('已选位置：' + JSON.stringify(this.dialogMapData))
      this.dialogConfig.visible = false
    },
  },
}
</script>
