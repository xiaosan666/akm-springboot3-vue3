// 注册一个全局自定义指令 `v-focus`
export const focus = {
  // 当被绑定的元素插入到 DOM 中时……
  mounted: function (el) {
    // 聚焦元素
    el.focus()
  },
}
