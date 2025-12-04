import { dialogDrag } from './dialogDrag'
import { has } from './permission'

// 注册一个全局自定义指令 `v-focus`
const focus = {
  // 当被绑定的元素插入到 DOM 中时……
  mounted: function (el) {
    // 聚焦元素
    el.focus()
  },
}

const noMargin = {
  mounted: function (el) {
    el.style.margin = 0
  },
}

const noPadding = {
  mounted: function (el) {
    el.style.setProperty('padding', '0', 'important')
  },
}

const createDirective = name => {
  return {
    mounted: function (el, binding) {
      const value = binding.value || '12px'
      el.style[name] = !isNaN(Number(value)) ? value + 'px' : value
    },
  }
}

// 文本居中显示
export const textCenter = {
  mounted: function (el) {
    el.style.textAlign = 'center'
  },
}

// 文本居左显示
export const textLeft = {
  mounted: function (el) {
    el.style.textAlign = 'left'
  },
}

// 文本居右显示
export const textRight = {
  mounted: function (el) {
    el.style.textAlign = 'right'
  },
}

// 导出所有指令
export const directives = [
  'margin',
  'margin-left',
  'margin-right',
  'margin-top',
  'margin-bottom',
  'padding',
  'padding-left',
  'padding-right',
  'padding-top',
  'padding-bottom',
]

// 注册指令的函数
export function registerDirectives(app) {
  directives.forEach(key => {
    app.directive(key, createDirective(key))
  })
  app.directive('focus', focus)
  app.directive('no-margin', noMargin)
  app.directive('no-padding', noPadding)
  app.directive('text-center', textCenter)
  app.directive('text-left', textLeft)
  app.directive('text-right', textRight)
  app.directive('dialogDrag', dialogDrag)
  app.directive('has', has)
}
