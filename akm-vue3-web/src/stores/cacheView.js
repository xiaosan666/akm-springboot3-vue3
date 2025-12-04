import { defineStore } from 'pinia'

export const useCacheViewStore = defineStore('cacheView', {
  state: () => ({
    cachedViews: [],
    showBackButton: false,
    clickBackButton: false,
  }),
  actions: {
    addCachedView(viewName) {
      if (this.cachedViews.includes(viewName)) return
      this.cachedViews.push(viewName)
    },
    delCachedView(viewName) {
      const index = this.cachedViews.findIndex(name => name === viewName)
      if (index !== -1) {
        this.cachedViews.splice(index, 1)
      }
    },
    clearCachedView() {
      this.cachedViews = []
    },
    setShowBackButton(bool) {
      this.showBackButton = bool
    },
    setClickBackButton(bool) {
      this.clickBackButton = bool
    },
  },
})
