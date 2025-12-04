const routeDemo = [
  {
    path: '/demo',
    name: 'Demo',
    meta: { title: 'Demo' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/index.vue'),
  },
  {
    path: '/demo/user',
    name: 'DemoUser',
    meta: { title: '用户管理demo' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/user/index.vue'),
  },
  {
    path: '/demo/global-component',
    name: 'DemoGlobalComponent',
    meta: { title: '全局组件demo' },
    component: () =>
      import(/* webpackChunkName: "demo" */ '@/views/demo/global-component/index.vue'),
  },
  {
    path: '/demo/file',
    name: 'DemoFile',
    meta: { title: '文件上传下载' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/file/index.vue'),
  },
  {
    path: '/demo/excel',
    name: 'DemoExcel',
    meta: { title: 'Excel导入导出' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/excel/index.vue'),
  },
  {
    path: '/demo/map',
    name: 'DemoMap',
    meta: { title: '地图定位' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/map/index.vue'),
  },
  {
    path: '/demo/district',
    name: 'DemoDistrict',
    meta: { title: '地图定位' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/district/index.vue'),
  },
  {
    path: '/demo/mapArea',
    name: 'DemoMapArea',
    meta: { title: '行政区域地图' },
    component: () => import(/* webpackChunkName: "demo" */ '@/views/demo/map-area/index.vue'),
  },
  {
    path: '/demo/demoDynamicCondition',
    name: 'DemoDynamicCondition',
    meta: { title: '动态查询条件' },
    component: () =>
      import(/* webpackChunkName: "demo" */ '@/views/demo/dynamic-condition/index.vue'),
  },
]

/**
 * 服务端是生产环境则不加载demo route
 */
export default import.meta.env.VITE_NODE_ENV !== 'production' ? routeDemo : []
