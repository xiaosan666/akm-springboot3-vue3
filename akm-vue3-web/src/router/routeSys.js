export default [
  {
    path: '/sys/tenant',
    name: 'SysTenant',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/tenant/index.vue'),
    meta: { title: '租户管理' },
  },
  {
    path: '/sys/api',
    name: 'SysApi',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/api/index.vue'),
    meta: { title: 'Api接口管理' },
  },
  {
    path: '/sys/menu',
    name: 'SysMenu',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/menu/index.vue'),
    meta: { title: '菜单管理' },
  },
  {
    path: '/sys/role',
    name: 'SysRole',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/role/index.vue'),
    meta: { title: '角色管理' },
  },
  {
    path: '/sys/user',
    name: 'SysUser',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/user/index.vue'),
    meta: { title: '用户管理' },
  },
  {
    path: '/sys/dict',
    name: 'SysDict',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/dict/index.vue'),
    meta: { title: '数据字典管理' },
  },
  {
    path: '/sys/org',
    name: 'SysOrg',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/org/index.vue'),
    meta: { title: '组织机构管理' },
  },
  {
    path: '/sys/log/login',
    name: 'LoginLog',
    component: () => import(/* webpackChunkName: "log" */ '@/views/sys/log/login-log/index.vue'),
    meta: { title: '登录日志' },
  },
  {
    path: '/sys/log/login/view/:id',
    name: 'LoginLogView',
    component: () =>
      import(/* webpackChunkName: "log" */ '@/views/sys/log/login-log-view/index.vue'),
    meta: { title: '登录日志详情' },
  },
  {
    path: '/sys/log/exception',
    name: 'ExceptionLog',
    component: () =>
      import(/* webpackChunkName: "log" */ '@/views/sys/log/exception-log/index.vue'),
    meta: { title: '异常日志' },
  },
  {
    path: '/sys/log/exception/view/:id',
    name: 'ExceptionLogView',
    component: () =>
      import(/* webpackChunkName: "log" */ '@/views/sys/log/exception-log-view/index.vue'),
    meta: { title: '异常日志详情' },
  },
  {
    path: '/sys/log/request',
    name: 'RequestLog',
    component: () => import(/* webpackChunkName: "log" */ '@/views/sys/log/request-log/index.vue'),
    meta: { title: '请求日志' },
  },
  {
    path: '/sys/log/request/view/:id',
    name: 'RequestLogView',
    component: () =>
      import(/* webpackChunkName: "log" */ '@/views/sys/log/request-log-view/index.vue'),
    meta: { title: '请求日志详情' },
  },
  {
    path: '/sys/user/online',
    name: 'OnlineUser',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/user-online/index.vue'),
    meta: { title: '在线用户' },
  },
  {
    path: '/sys/message',
    name: 'SysMessage',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/message/index.vue'),
    meta: { title: '消息/公告管理' },
  },
  {
    path: '/sys/message/view/:messageId',
    name: 'SysMessageView',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/message/addOrEdit.vue'),
    meta: { title: '消息查看', isView: true, isEdit: false },
  },
  {
    path: '/sys/message/add/:messageId?',
    name: 'SysMessageAdd',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/message/addOrEdit.vue'),
    meta: { title: '消息新增', isView: false, isEdit: false },
  },
  {
    path: '/sys/message/edit/:messageId',
    name: 'SysMessageEdit',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/message/addOrEdit.vue'),
    meta: { title: '消息编辑', isView: false, isEdit: true },
  },
  {
    path: '/sys/attachment',
    name: 'SysAttachment',
    component: () => import(/* webpackChunkName: "sys" */ '@/views/sys/attachment/index.vue'),
    meta: { title: '附件管理' },
  },
]
