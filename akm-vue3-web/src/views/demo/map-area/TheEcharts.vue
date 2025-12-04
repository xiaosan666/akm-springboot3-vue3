<template>
  <div class="map-warp">
    <div id="gzMap" class="gz"></div>
    <img src="../../../assets/icon/arrow.png" alt="" class="map-arrow" />
    <div id="gzDetailMap" class="gz-detail"></div>
  </div>
</template>

<script>
// 按需引入echarts
require('echarts/lib/chart/map')
let echarts = require('echarts/lib/echarts')
const gzGeoJSON = require('../../../assets/json/440100_full.json')
const detailGeoJSON = require('../../../assets/json/440100_detail.json')
export default {
  name: 'TheEcharts',
  mounted() {
    this.initGzMap()
    this.initGzDetailMap()
  },
  methods: {
    initGzMap() {
      echarts.registerMap('guangzhou', gzGeoJSON)
      let myChart = echarts.init(document.getElementById('gzMap'))
      let data = [
        {
          name: '天河区',
          label: {
            normal: {
              show: false,
              color: '#000',
            },
          },
        },
        {
          name: '越秀区',
          label: {
            normal: {
              show: false,
            },
          },
        },
        {
          name: '荔湾区',
          label: {
            normal: {
              show: false,
            },
          },
        },
        {
          name: '海珠区',
          label: {
            normal: {
              show: false,
            },
          },
        },
      ]
      myChart.setOption(this.getOptions('guangzhou', data))
    },
    initGzDetailMap() {
      echarts.registerMap('guangzhouDetail', detailGeoJSON)
      let myChart = echarts.init(document.getElementById('gzDetailMap'))
      myChart.setOption(this.getOptions('guangzhouDetail', []))
    },
    getOptions(mapName, seriesData) {
      return {
        backgroundColor: '#2a2a2a',
        geo: {
          aspectScale: 0.9,
          // 作为底图，设置地图外围边框
          map: mapName,
          top: '20px',
          bottom: '20px',
          itemStyle: {
            // 定义样式
            normal: {
              // 普通状态下的样式
              borderColor: 'rgba(192,245,249,.8)',
              borderWidth: 3,
              shadowColor: 'rgba(0, 0, 0, 0.5)',
              shadowBlur: 1,
              shadowOffsetX: 12,
              shadowOffsetY: 12,
            },
          },
          silent: true,
        },
        series: [
          {
            aspectScale: 0.9,
            top: '20px',
            bottom: '20px',
            type: 'map',
            map: mapName,
            emphasis: {
              label: {
                show: true,
                color: '#fff',
              },
            },
            itemStyle: {
              normal: {
                color: '#131C38',
                borderColor: '#4ECEE6',
                borderWidth: 1,
                label: {
                  show: true,
                },
                areaColor: {
                  type: 'linear-gradient',
                  x: 0,
                  y: 300,
                  x2: 0,
                  y2: 0,
                  colorStops: [
                    {
                      offset: 0,
                      color: 'RGBA(37,108,190,1)', // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: 'RGBA(15,169,195,1)', // 50% 处的颜色
                    },
                  ],
                  global: true, // 缺省为 false
                },
              },
              emphasis: {
                areaColor: {
                  type: 'linear-gradient',
                  x: 0,
                  y: 300,
                  x2: 0,
                  y2: 0,
                  colorStops: [
                    {
                      offset: 0,
                      color: 'RGBA(37,108,190,1)', // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: 'RGBA(15,169,195,1)', // 50% 处的颜色
                    },
                  ],
                  global: true, // 缺省为 false
                },
                shadowColor: 'rgba(0, 255, 255, 0.7)',
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowOffsetY: 1,
              },
            },
            label: {
              normal: {
                textStyle: {
                  fontSize: 14,
                  fontWeight: 'bold',
                  color: '#111',
                },
              },
            },
            select: false,
            data: seriesData,
          },
        ],
      }
    },
  },
}
</script>

<style scoped lang="scss">
.map-warp {
  width: 100%;
  height: 100%;
  position: relative;

  .gz {
    width: 100%;
    height: 100%;
  }

  .gz-detail {
    width: 32%;
    height: 32%;
    position: absolute;
    left: 0;
    bottom: 10%;
  }

  .map-arrow {
    position: absolute;
    left: 27%;
    bottom: 36%;
    z-index: 1;
  }
}
</style>
