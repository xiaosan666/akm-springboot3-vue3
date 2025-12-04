const isNumber = value => {
  return !isNaN(Number(value))
}

const paddingObj = {
  mounted: function (el, binding) {
    const value = binding.value || '12px'
    el.style[binding.name] = isNumber(value) ? value + 'px' : value
  },
}

export const padding = paddingObj
export const paddingLeft = paddingObj
export const paddingRight = paddingObj
export const paddingTop = paddingObj
export const paddingBottom = paddingObj

export const noPadding = {
  mounted: function (el) {
    el.style.setProperty('padding', '0', 'important')
  },
}
