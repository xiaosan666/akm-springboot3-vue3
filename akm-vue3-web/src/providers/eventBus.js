import mitt from 'mitt'

const eventBus = mitt()
/**
 * Event Key 集合
 */
eventBus.keys = {
  updatePassword: 'event:update-password', // 登录修改密码事件
  pageRefresh: 'event:page-refresh', // 页面刷新事件
  mainRefresh: 'event:main-refresh', // 页面内容区域刷新事件
  heightChange: 'event:height-change', // 页面高度改变事件
  unReadMessageCountChange: 'event:unReadMessageCountChange', // 未读消息总数改变事件
}
export default eventBus
