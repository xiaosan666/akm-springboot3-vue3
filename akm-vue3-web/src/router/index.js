import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/login/index.vue'
import Layout from '@/layout/index.vue'
import UserCenter from '@/views/user-center/index.vue'
import RouteDemo from './routeDemo.js'
import RouteSys from './routeSys.js'

const LOGIN_PATH = '/login'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    // {
    //   path: '/user/center',
    //   name: 'home',
    //   component: UserCenter,
    // },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: LOGIN_PATH,
      name: 'login',
      component: LoginView,
    },
    {
      path: '',
      // name: 'Layout', // 这里不需要name，否则浏览器控制台会输出告警
      component: Layout,
      children: [
        ...RouteDemo, // 添加demo路由，打包后不生效
        ...RouteSys, // 系统管理模块路由
        {
          path: '/user/center',
          name: 'UserCenter',
          component: UserCenter,
          meta: { title: '个人中心' },
        },
        {
          path: '/message',
          name: 'UserMessage',
          meta: { title: '消息中心' },
          component: () => import(/* webpackChunkName: "user" */ '@/views/user-message/index.vue'),
          children: [
            {
              path: '/message/myMessage',
              name: 'MyMessage',
              meta: { title: '消息通知' },
              component: () =>
                import(/* webpackChunkName: "user" */ '@/views/user-message/my-message/index.vue'),
            },
            {
              path: '/message/MyNotice',
              name: 'MyNotice',
              meta: { title: '系统公告' },
              component: () =>
                import(/* webpackChunkName: "user" */ '@/views/user-message/my-notice/index.vue'),
            },
          ],
        },
      ],
    },
    {
      path: '/log/run',
      name: 'RunLog',
      component: () =>
        import(/* webpackChunkName: "log" */ '@/views/sys/log/run-log/index.vue'),
      meta: { title: '运行日志', logFileName: 'all_log.log' },
    },
    {
      path: '/log/error',
      name: 'ErrorLog',
      component: () =>
        import(/* webpackChunkName: "log" */ '@/views/sys/log/run-log/index.vue'),
      meta: { title: '错误日志', logFileName: 'err_log.log' },
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import(/* webpackChunkName: "status" */ '@/views/common/ThePage404.vue'),
      meta: { title: '404', breadcrumb: false },
    },
  ],
})

router.LOGIN_PATH = LOGIN_PATH

export default router
