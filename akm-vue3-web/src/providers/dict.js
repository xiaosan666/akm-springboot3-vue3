import utils from '@/providers/utils'
import http from '@/providers/http'
import sessionStorage from '@/providers/sessionStorage'

/**
 * 缓存数据字典数据
 */
let Data = []

/**
 * 根据字典类型获取字典列表。获取顺序：内存》sessionStorage》数据库
 * @param typeArr 字典类型数组
 * @returns {Promise<any>}
 */
const getListByTypes = typeArr => {
  return new Promise(resolve => {
    if (!typeArr) {
      throw new Error('getListByTypes参数不能为空')
    }
    const types = utils.isArray(typeArr) ? typeArr : typeArr.split(',')
    if (Data.length === 0) {
      // 从sessionStorage获取
      let cache = sessionStorage.get(sessionStorage.keys.dictData)
      if (cache && utils.isArray(cache)) {
        Data = cache
      }
    }
    let list = Data.filter(item => types.includes(item.type))
    if (list.length > 0 && types.length === 1) {
      resolve(list)
      return
    } else {
      // 当参数类型为多个，需要判断获取的数据是否包含所有类型
      let typeNameArr = list.reduce((pre, item) => {
        return pre.includes(item.type) ? pre : pre.concat(item.type)
      }, [])
      if (utils.assertArraysEqual(types, typeNameArr)) {
        resolve(list)
        return
      }
    }
    // 从后台获取
    http.post('/auth/sys/dict/public/findByTypes', types).then(res => {
      resolve(res || [])
      Data = Data.filter(item => !types.includes(item.type)).concat(res)
      sessionStorage.set(sessionStorage.keys.dictData, Data)
    })
  })
}

/**
 * 根据类型和值获取名称
 * @param type
 * @param value
 * @returns {string}
 */
const translateNameByTypeAndValue = (type, value) => {
  let data = Data.filter(item => item.type === type && String(item.value) === String(value))
  return data && data.length === 1 ? data[0].name || data[0].label : ''
}

/**
 * 根据字典类型与 code 确定字典值
 *
 * @param type
 * @param code
 * @returns {string}
 */
const translateValueByTypeAndCode = (type, code) => {
  let dict = Data.find(item => item.type === type && item.code === code)
  return dict.value || ''
}

/**
 * 初始化加载常用字典类型
 */
const initTypes = () => {
  getListByTypes(window.INIT_DICT_TYPES).then()
}

/**
 * 清空字典
 */
const clear = type => {
  if (!type) {
    Data = []
    return
  }
  Data = Data.filter(item => item.type !== type)
}

export default {
  getListByTypes, // 根据字典类型获取字典列表并缓存，获取顺序：内存>sessionStorage>数据库
  translateNameByTypeAndValue, // 根据类型和值翻译名称
  translateValueByTypeAndCode, // 根据字典类型与 code 确定字典值
  initTypes, // 系统初始化时加载常用字典类型
  clear, // 清空缓存的所有字典数据，退出登录会清空
}
