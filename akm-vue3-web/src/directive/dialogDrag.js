// v-dialogDrag: 弹窗拖拽
export const dialogDrag = {
  mounted: function (el) {
    // 初始非全屏
    let isFullScreen = false

    // 当前宽高
    let nowWidth = 0
    // let nowHight = 0

    // 当前顶部高度
    let nowMarginTop = 0
    // 获取弹框头部（这部分可双击全屏）
    const dialogHeaderEl = el.querySelector('.el-dialog__header')

    // 弹窗
    const dragDom = el.querySelector('.el-dialog')
    // 给弹窗加上overflow auto；不然缩小时框内的标签可能超出dialog；
    dragDom.style.overflow = 'auto'

    // 头部加上可拖动cursor
    dialogHeaderEl.style.cursor = 'move'

    // 获取原有属性 ie dom元素.currentStyle 火狐谷歌 window.getComputedStyle(dom元素, null);
    const sty = dragDom.currentStyle || window.getComputedStyle(dragDom, null)

    let moveDown = e => {
      // 鼠标按下，计算当前元素距离可视区的距离
      const disX = e.clientX - dialogHeaderEl.offsetLeft
      const disY = e.clientY - dialogHeaderEl.offsetTop

      // 获取到的值带px 正则匹配替换
      let styL
      let styT

      // 注意在ie中 第一次获取到的值为组件自带50% 移动之后赋值为px
      if (sty.left.includes('%')) {
        styL = Number(document.body.clientWidth) * (Number(sty.left.replace(/%/g, '')) / 100)
        styT = Number(document.body.clientHeight) * (Number(sty.top.replace(/%/g, '')) / 100)
      } else {
        styL = Number(sty.left.replace(/px/g, ''))
        styT = Number(sty.top.replace(/px/g, ''))
      }

      document.onmousemove = function (ev) {
        // 通过事件委托，计算移动的距离
        const l = ev.clientX - disX
        const t = ev.clientY - disY

        // 移动当前元素
        dragDom.style.left = `${l + styL}px`
        dragDom.style.top = `${t + styT}px`

        // 将此时的位置传出去
        // binding.value({x:e.pageX,y:e.pageY})
      }

      document.onmouseup = function () {
        document.onmousemove = null
        document.onmouseup = null
      }
    }

    dialogHeaderEl.onmousedown = moveDown

    // 双击头部全屏效果
    dialogHeaderEl.ondblclick = () => {
      if (isFullScreen === false) {
        // nowHight = dragDom.clientHeight

        nowWidth = dragDom.clientWidth

        nowMarginTop = dragDom.style.marginTop

        dragDom.style.left = 0

        dragDom.style.top = 0

        dragDom.style.height = '100VH'

        dragDom.style.width = '100VW'

        dragDom.style.margin = 0

        isFullScreen = true

        dialogHeaderEl.style.cursor = 'initial'

        dialogHeaderEl.onmousedown = null
      } else {
        dragDom.style.height = 'auto'

        dragDom.style.width = nowWidth + 'px'

        dragDom.style.margin = `${nowMarginTop} auto 0`

        isFullScreen = false

        dialogHeaderEl.style.cursor = 'move'

        dialogHeaderEl.onmousedown = moveDown
      }
    }

    dragDom.onmousemove = function () {
      dragDom.onmousedown = e => {
        const clientX = e.clientX

        const clientY = e.clientY

        let elW = dragDom.clientWidth

        let EloffsetLeft = dragDom.offsetLeft

        let EloffsetTop = dragDom.offsetTop

        dragDom.style.userSelect = 'none'

        // 判断点击的位置是不是为头部
        if (
          clientX > EloffsetLeft &&
          clientX < EloffsetLeft + elW &&
          clientY > EloffsetTop &&
          clientY < EloffsetTop + 100
        ) {
          // 如果是头部在此就不做任何动作，以上有绑定dialogHeaderEl.onmousedown = moveDown;
        } else {
          // 拉伸结束
          document.onmouseup = function () {
            document.onmousemove = null
            document.onmouseup = null
          }
        }
      }
    }
  },
}
