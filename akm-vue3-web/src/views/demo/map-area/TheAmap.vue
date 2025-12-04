<template>
  <div id="container" class="map-warp"></div>
</template>

<script>
const gzGeoJSON = require('../../../assets/json/440100_full.json')
const gzBoundaryJSON = require('../../../assets/json/440100.json')
const AMap = window.AMap
let map = null
export default {
  name: 'TheAmap',
  data() {
    return {}
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      map = new AMap.Map('container', {
        mapStyle: 'amap://styles/dark', // 设置地图的显示样式
        features: ['bg', 'point'],
        zoom: 9.5,
        center: [113.52, 23.13],
        pitch: 15,
        viewMode: '3D',
      })
      AMap.plugin(['AMap.GeoJSON', 'AMap.DistrictSearch'], () => {
        let geojson1 = new AMap.GeoJSON({
          geoJSON: gzBoundaryJSON,
          getPolygon: function (geojson, lnglats) {
            // 外多边形坐标数组和内多边形坐标数组
            let outer = [
              new AMap.LngLat(-360, 90, true),
              new AMap.LngLat(-360, -90, true),
              new AMap.LngLat(360, -90, true),
              new AMap.LngLat(360, 90, true),
            ]
            let pathArray = [outer]
            pathArray.push.apply(pathArray, lnglats[0])
            return new AMap.Polygon({
              path: pathArray,
              strokeColor: '#00eeff',
              strokeWeight: 1,
              fillColor: '#71B3ff',
              fillOpacity: 0.5,
            })
          },
        })
        map.add(geojson1)

        let geojson = new AMap.GeoJSON({
          geoJSON: gzGeoJSON,
          getPolygon: function (geojson, lnglats) {
            return new AMap.Polygon({
              path: lnglats,
              fillOpacity: 0,
              // strokeColor: '#4ECEE6',
              // fillColor: '#4ECEE6'
            })
          },
        })
        map.add(geojson)
        //
        //
        // var outer = [
        //   new AMap.LngLat(-360,90,true),
        //   new AMap.LngLat(-360,-90,true),
        //   new AMap.LngLat(360,-90,true),
        //   new AMap.LngLat(360,90,true),
        // ];
        //
        // var  holes = gzGeoJSON.features[0].geometry.coordinates[0]
        // var pathArray = [
        //   outer
        // ];
        // pathArray.push.apply(pathArray,holes)
        // var polygon = new AMap.Polygon( {
        //   path:pathArray,
        //   strokeColor: '#00eeff',
        //   strokeWeight: 1,
        //   fillColor: '#71B3ff',
        //   fillOpacity: 0.5
        // });
        //
        // map.add(polygon)
      })
    },
  },
}
</script>

<style scoped lang="scss">
.map-warp {
  width: 100%;
  height: 100%;
}
</style>
