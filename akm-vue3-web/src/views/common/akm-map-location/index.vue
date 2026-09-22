<!--地图定位、获取位置组件 （高德）-->
<template>
  <div class="map-location">
    <div v-if="config.showSearch !== false" class="search-warp">
      <input
        :id="'searchInput' + config.id"
        link
        class="search"
        placeholder="输入关键字搜索"
        clearable
      />
      <div class="button">
        <el-button type="primary" @click="getLocation">搜索</el-button>
      </div>
    </div>
    <div :id="'searchOutput' + config.id" class="searchOutput"></div>
    <div :id="'map' + config.id" class="map-container"></div>
  </div>
</template>

<script>
const AMap = window.AMap
export default {
  name: 'AkmMapLocation',
  props: {
    config: {
      type: Object,
      default() {
        return {
          id: '1', // 当同个页面加载多个地图时，需要指定一个id来区分
          showSearch: true, // 是否显示查询输入框
          location: {
            lng: '', // 初始化经度
            lat: '', // 初始化纬度
          },
          click: true, // 是否可以点击地图切换点
        }
      },
    },
  },
  data() {
    return {
      map: null,
      mapGeocoder: null,
      mapAutoComplete: null,
    }
  },
  watch: {
    'config.location': {
      immediate: true,
      handler(location) {
        this.drawMarker(location.lng, location.lat)
      },
    },
  },
  mounted() {
    this.loadMap()
    this.loadPlugin()
  },
  methods: {
    // 地图初始化
    loadMap() {
      this.map = new AMap.Map('map' + this.config.id, {
        zoom: 14,
        center: [113.264385, 23.129112],
      })
      this.map.on('complete', () => {
        // 绘制位置
        let location = this.config.location
        this.drawMarker(location.lng, location.lat)
        // 判断是否可以点击地图改变标注位置
        if (this.config.click !== false) {
          this.map.on('click', e => {
            this.drawMarker(e.lnglat.getLng(), e.lnglat.getLat())
            this.getAddress(e.lnglat.getLng(), e.lnglat.getLat())
          })
        }
      })
    },
    loadPlugin() {
      // 异步同时加载多个插件
      // 注意：JS API 2.0 中输入提示插件名由 AMap.Autocomplete 变更为 AMap.AutoComplete（大写 C）
      AMap.plugin(['AMap.Geocoder', 'AMap.AutoComplete', 'AMap.ToolBar'], () => {
        // 地理编码与逆地理编码插件
        // https://lbs.amap.com/api/javascript-api/guide/services/geocoder/
        this.mapGeocoder = new AMap.Geocoder({
          city: '全国',
          extensions: 'base',
        })

        // 插件：输入提示与POI搜索
        // https://lbs.amap.com/api/javascript-api-v2/guide/services/autocomplete
        this.mapAutoComplete = new AMap.AutoComplete({
          city: '全国',
          input: 'searchInput' + this.config.id,
          output: 'searchOutput' + this.config.id,
        })

        // 2.0 推荐使用实例的 on/off 绑定事件
        this.mapAutoComplete.on('select', e => {
          let location = e.poi.location
          if (location) {
            this.drawMarker(location.lng, location.lat)
            this.getAddress(location.lng, location.lat)
          } else {
            this.$helper.errorMessage('该位置没有坐标')
          }
        })

        this.map.addControl(
          new AMap.ToolBar({
            liteStyle: true,
          })
        )
      })
    },
    // 根据坐标绘制点
    drawMarker(lng, lat) {
      // 注意：config.location 的 immediate watcher 会在 mounted 之前触发，
      // 此时 this.map 还是 null（地图尚未初始化），必须先返回，否则 clear() 会报
      // TypeError: Cannot read properties of null (reading 'clearMap')
      // 初始位置的绘制由 map 的 complete 事件负责，不会漏掉。
      if (!this.map || !lng || !lat) {
        return
      }
      this.clear()
      let position = new AMap.LngLat(lng, lat)
      this.marker = new AMap.Marker({
        map: this.map,
        position: position,
      })
      this.map.setCenter(position)
    },
    // 根据坐标获取详细地址
    getAddress(lng, lat) {
      let position = new AMap.LngLat(lng, lat)
      this.mapGeocoder.getAddress(position, (status, result) => {
        if (status === 'complete' && result.info === 'OK') {
          let address = result.regeocode.formattedAddress
          this.publicLocation(lng, lat, address)
        } else {
          this.publicLocation(lng, lat)
        }
      })
    },
    getLocation() {
      debugger
      let address = document.getElementById('searchInput' + this.config.id).value
      this.mapGeocoder.getLocation(address, (status, result) => {
        if (status === 'complete' && result.info === 'OK') {
          debugger
          let res = result.geocodes[0]
          this.drawMarker(res.location.lng, res.location.lat)
          this.publicLocation(res.location.lng, res.location.lat, res.formattedAddress)
        } else {
          // 高德服务类插件鉴权失败（如 USERKEY_PLAT_NOMATCH / INVALID_USER_SCODE）时
          // status 为 error 且 result 可能为 undefined，这里提示出来，避免“点搜索没反应”
          this.$helper.errorMessage('地址解析失败：' + ((result && result.info) || status))
        }
      })
    },
    publicLocation(lng, lat, address = '') {
      this.$emit('location-change', { lng, lat, address })
    },
    clear() {
      this.map.clearMap() // 删除地图上所有的覆盖物
    },
  },
}
</script>

<style scoped lang="scss">
.map-location {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  .search-warp {
    flex: 0 0 1;
    display: flex;
    > .button {
      margin-left: 2px;
    }
  }
  .map-container {
    width: 100%;
    height: 100%;
    border: 1px solid #e1e1e1;
  }
  .search {
    background-color: #fff;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    height: 40px;
    line-height: 40px;
    outline: none;
    padding: 0 15px;
    width: 100%;
    margin-bottom: 10px;
    &:focus {
      border-color: #407bdb;
    }
  }
}
</style>
<style lang="scss">
.searchOutput {
  visibility: hidden;
  position: absolute;
  margin-top: 39px;
  background: #fff;
  height: 300px;
  z-index: 1;
  border: 1px solid #ddd;
  > div {
    font-size: 14px;
    padding: 6px;
  }
}
</style>
