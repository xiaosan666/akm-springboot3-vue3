import { defineStore } from 'pinia'
import http from '@/providers/http'
import helper from '@/providers/helper'

export const useSwaggerApiStore = defineStore('swaggerApi', {
  state: () => ({
    tree: [],
  }),
  actions: {
    async initSwaggerApiData() {
      let newTree = []
      let docs = await http.get('/akm-docs/swagger-config', null, {}, 'swagger')
      let urls = docs.urls
      for (let i = 0, len = urls.length; i < len; i++) {
        const it = urls[i]
        const id = String(i + 1)
        let res = await http.get(it.url, null, {}, 'swagger')
        let apiList = []
        let methods = ['post', 'get', 'delete', 'put']
        for (const path of Object.keys(res.paths)) {
          let data = res.paths[path]
          for (let method of methods) {
            let info = data[method]
            if (info) {
              apiList.push({
                uri: path,
                name: info.summary,
                method,
                tags: info.tags,
                remark: info.description,
                orders: info['x-order'] || 1,
              })
            }
          }
        }
        let tagApi = {}
        for (let api of apiList) {
          let tags = api.tags
          if (!tags || tags.length === 0) {
            helper.errorMessage('接口: ' + api.uri + ' 未设置tags，请设置！')
            return
          }
          for (let tag of tags) {
            tagApi[tag] ? tagApi[tag].push(api) : (tagApi[tag] = [api])
          }
        }
        for (const tag of Object.keys(tagApi)) {
          tagApi[tag].sort((a, b) => a.orders - b.orders)
        }
        let tagList = []
        let keys = Object.keys(tagApi)
        for (let i = 0, len = keys.length; i < len; i++) {
          let key = keys[i]
          let tagId = id + '_' + (i + 1)
          let apis = tagApi[key]
          for (let j = 0; j < apis.length; j++) {
            let api = apis[j]
            api.id = tagId + '_' + (j + 1)
            api.parentId = tagId
            api.type = 2
          }
          tagList.push({
            id: tagId,
            parentId: id,
            name: key,
            type: 1,
            children: apis,
          })
        }
        newTree.push({
          id: id,
          parentId: '0',
          name: it.name,
          uri: it.url,
          type: 1,
          children: tagList,
        })
      }
      this.tree = newTree
    },
  },
})
