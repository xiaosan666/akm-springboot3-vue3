import { v4 as uuidv4 } from 'uuid'
import validate from '@/providers/validate'

/**
 * utils.js:与业务无关的工具方法
 * helper.js:与业务或框架有关的工具方法
 */

/**
 * 将日期类型转为日期字符串，可指定输入格式
 * @param date 需要格式化的日期参数，可以通过new Date()转为日期对象的参数
 * @param sFormat 输出格式,默认为yyyy-MM-dd                         年：y，月：M，日：d，时：h，分：m，秒：s
 * @example  formatDate(new Date())                               "2017-02-28"
 * @example  formatDate(Date.now())                               "2017-02-28"
 * @example  formatDate('2017-02-28 15:24:00','yyyy-MM-dd')       "2017-02-28"
 * @example  formatDate(new Date(),'yyyy-MM-dd HH:mm:ss')         "2017-02-28 15:24:00"
 * @example  formatDate(new Date(),'HH:mm')                       "15:24"
 * @example  formatDate(new Date(),'yyyy-MM-ddTHH:mm:ss+08:00')   "2017-02-28T15:24:00+08:00"
 * @returns {string}
 */
const formatDate = (date, sFormat = 'yyyy-MM-dd') => {
  if (!date) {
    return ''
  }
  // iOS系统new Date('2017-02-28 15:24:00')会报错，针对形如yyyy-MM-dd HH:mm:ss的字符额外处理
  if (typeof date === 'string' && date.length === 19 && !date.includes('T')) {
    date = date.replace(/-/g, '/')
  }

  date = new Date(date)
  if (isNaN(date.getTime())) {
    return ''
  }
  let time = {
    Year: 0,
    TYear: '0',
    Month: 0,
    TMonth: '0',
    Day: 0,
    TDay: '0',
    Hour: 0,
    THour: '0',
    hour: 0,
    Thour: '0',
    Minute: 0,
    TMinute: '0',
    Second: 0,
    TSecond: '0',
    Millisecond: 0,
  }
  time.Year = date.getFullYear()
  time.TYear = String(time.Year).substr(2)
  time.Month = date.getMonth() + 1
  time.TMonth = time.Month < 10 ? '0' + time.Month : String(time.Month)
  time.Day = date.getDate()
  time.TDay = time.Day < 10 ? '0' + time.Day : String(time.Day)
  time.Hour = date.getHours()
  time.THour = time.Hour < 10 ? '0' + time.Hour : String(time.Hour)
  time.hour = time.Hour < 13 ? time.Hour : time.Hour - 12
  time.Thour = time.hour < 10 ? '0' + time.hour : String(time.hour)
  time.Minute = date.getMinutes()
  time.TMinute = time.Minute < 10 ? '0' + time.Minute : String(time.Minute)
  time.Second = date.getSeconds()
  time.TSecond = time.Second < 10 ? '0' + time.Second : String(time.Second)
  time.Millisecond = date.getMilliseconds()

  return sFormat
    .replace(/yyyy/gi, String(time.Year))
    .replace(/yyy/gi, String(time.Year))
    .replace(/yy/gi, time.TYear)
    .replace(/y/gi, time.TYear)
    .replace(/MM/g, time.TMonth)
    .replace(/M/g, String(time.Month))
    .replace(/dd/gi, time.TDay)
    .replace(/d/gi, String(time.Day))
    .replace(/HH/g, time.THour)
    .replace(/H/g, String(time.Hour))
    .replace(/hh/g, time.Thour)
    .replace(/h/g, String(time.hour))
    .replace(/mm/g, time.TMinute)
    .replace(/m/g, String(time.Minute))
    .replace(/ss/gi, time.TSecond)
    .replace(/s/gi, String(time.Second))
    .replace(/fff/gi, String(time.Millisecond))
}

/**
 * 开始日期和结束日期如果相等，则返回一个日期，否则返回日期范围
 * @example  formatDateRange('2017-02-28','2017-02-28')     2017-02-28
 * @example  formatDateRange('2017-02-28','2017-02-29')     2017-02-28 ~ 2017-02-29
 */
const formatDateRange = (startDate, endDate) => {
  let start = formatDate(startDate)
  let end = formatDate(endDate)
  return start === end ? start : start + ' ~ ' + end
}

/**
 * 去除字符串两边空格
 */
const trim = str => {
  if (str === null || str === undefined) {
    return ''
  }
  if (typeof str !== 'string') {
    return str
  }
  return String(str).replace(/(^\s+)|(\s+$)/g, '')
}

/**
 * 生成 uuid 形如：29cbeabba2894d04ac2e3ea99200d937
 * 注： uuid 每个字符都是0-9或a-f范围内的一个十六进制的字符
 */
const uuid = () => {
  return uuidv4().replace(/-/g, '')
}

/**
 * 克隆对象
 * 当对象属性有函数或者还需要原型链属性时，此方法不适用
 */
const clone = obj => {
  if (obj && obj instanceof Object) {
    return JSON.parse(JSON.stringify(obj))
  }
  return obj
}

/**
 * 把数组转为tree结构
 * 处理数据量大时，存在性能问题，建议设置copy参数为false
 */
const listToTree = (
  list = [],
  parentId = '0',
  childrenProp = 'children',
  parentProp = 'parentId',
  idProp = 'id',
  copy = true
) => {
  let newList = copy ? clone(list) : list
  let nodes = newList.filter(item => item[parentProp] === parentId)
  nodes.forEach(item => {
    if (!item[idProp]) {
      throw new Error(idProp + '属性不允许为空')
    }
    let childrenNodes = listToTree(newList, item[idProp], childrenProp, parentProp, idProp, false)
    if (childrenNodes && childrenNodes.length) {
      item[childrenProp] = childrenNodes
    }
  })
  return nodes
}

/**
 * 把树结构转为数组
 * 处理数据量大时，存在性能问题，建议设置copy参数为false
 */
const treeToList = (tree, childrenProp = 'children', copy = true) => {
  if (!isArray(tree)) {
    tree = [tree]
  }
  let list = []
  let newTree = copy ? clone(tree) : tree
  newTree.forEach(item => {
    list.push(item)
    const childrenNodes = item[childrenProp]
    if (childrenNodes && childrenNodes.length) {
      list = [...list, ...treeToList(childrenNodes, childrenProp, false)]
    }
  })
  list.forEach(item => {
    delete item[childrenProp]
  })
  return list
}

const stringToJson = str => {
  if (!str) {
    return null
  }
  try {
    return JSON.parse(str)
  } catch (e) {
    return null
  }
}

const isJsonString = str => {
  if (!str) {
    return false
  }
  try {
    if (typeof JSON.parse(str) === 'object') {
      return true
    }
  } catch (e) {
    return false
  }
  return false
}

const isNumber = value => {
  return !isNaN(Number(value))
}

const isString = str => {
  return typeof str === 'string' || str instanceof String
}

const isArray = arg => {
  return Object.prototype.toString.call(arg) === '[object Array]'
}

const isBlank = str => {
  return str === null || str === undefined || trim(str) === ''
}

const isNotBlank = str => {
  return !isBlank(str)
}

/**
 * 判断两个数组数据是否相等
 */
const assertArraysEqual = (arr1, arr2) => {
  if (!isArray(arr1) || !isArray(arr2)) {
    throw new Error('非法参数，非数组')
  }
  if (arr1.length !== arr2.length) {
    return false
  }
  return JSON.stringify(arr1.sort()) === JSON.stringify(arr2.sort())
}

/**
 * 文件下载
 * @param blob 文件流数据
 * @param fileName 文件名,包含后缀 如：abc.xlsx
 */
const downloadBlob = (blob, fileName) => {
  try {
    const href = window.URL.createObjectURL(blob) // 创建下载的链接
    if (window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob, fileName)
    } else {
      // 谷歌浏览器 创建a标签 添加download属性下载
      const downloadElement = document.createElement('a')
      downloadElement.href = href
      downloadElement.target = '_blank'
      downloadElement.download = fileName
      document.body.appendChild(downloadElement)
      downloadElement.click() // 点击下载
      document.body.removeChild(downloadElement) // 下载完成移除元素
      window.URL.revokeObjectURL(href) // 释放掉blob对象
    }
  } catch (e) {
    throw new Error(e)
  }
}

/**
 * 创建可取消的 Promise，包装Promise添加cancel方法
 * 场景：当第一次请求时长大于第二次时，为了避免第一次结果覆盖第二次，则应该在请求第二次发送前，调用cancel()取消第一次请求
 */
const makeCancelable = promise => {
  let hasCanceled_ = false
  const wrappedPromise = new Promise((resolve, reject) => {
    promise.then(val => (hasCanceled_ ? reject({ isCanceled: true }) : resolve(val)))
    promise.catch(error => (hasCanceled_ ? reject({ isCanceled: true }) : reject(error)))
  })
  return {
    promise: wrappedPromise,
    cancel() {
      hasCanceled_ = true
    },
  }
}

/**
 * 根据文件名判断是否为图片
 * @param fileName 文件名
 * @return {boolean}
 */
const isImage = fileName => {
  if (!fileName) {
    return false
  }
  return /\.(jpg|jpeg|png|gif|webp|tif|bmp|dwg)$/.test(fileName.toLowerCase())
}

/**
 * 根据文件size返回语义化单位的size
 * @param size 字节（Byte）
 * @example formatFileSize(1024*22)         '22KB'
 * @example formatFileSize(1024*1024*10)    '10MB'
 * @example formatFileSize(1024*1024*10.11) '10.11MB'
 */
const formatFileSize = size => {
  if (size < 1024) {
    return size + 'B'
  }
  if (size < 1024 * 1024) {
    return Math.round((size / 1024) * 100) / 100 + 'KB'
  }
  if (size < 1024 * 1024 * 1024) {
    return Math.round((size / 1024 / 1024) * 100) / 100 + 'MB'
  }
  if (size < 1024 * 1024 * 1024 * 1024) {
    return Math.round((size / 1024 / 1024 / 1024) * 100) / 100 + 'GB'
  }
  return size
}

/**
 * 根据身份证号码获取生日、性别、年龄
 * @example getUserInfoByIdCard('130928198905281793')   {birth: "1989-05-28", gender: 0, age: 31}
 */
const getUserInfoByIdCard = idCard => {
  if (!validate.regex.idCard.test(idCard)) {
    window.DEBUG && alert('身份证号码不合法')
    return {}
  }
  const birth =
    idCard.substring(6, 10) + '-' + idCard.substring(10, 12) + '-' + idCard.substring(12, 14)
  // 0男 1女
  const gender = parseInt(idCard.substr(16, 1), 10) % 2 === 1 ? 0 : 1

  let myDate = new Date()
  let month = myDate.getMonth() + 1
  let day = myDate.getDate()
  let birthMonth = parseInt(idCard.substring(10, 12), 10)
  let birthDay = parseInt(idCard.substring(12, 14), 10)
  let age = myDate.getFullYear() - parseInt(idCard.substring(6, 10), 10)

  if (month < birthMonth || (month === birthMonth && day < birthDay)) {
    age--
  }
  return {
    birth,
    gender,
    age,
  }
}

/**
 * 函数防抖（当持续触发事件结束后等待wait毫秒调用一次fn）
 * @example
 input() {
      this.$utils.debounce(() => {
        console.log(1)
      }, 1000)
    }
 */
const debounce = (fn, wait) => {
  let timeout = null
  return function (...args) {
    if (timeout !== null) clearTimeout(timeout)
    timeout = setTimeout(() => {
      fn.apply(this, args)
    }, wait)
  }
}

/**
 * 函数节流（当持续触发事件每隔delay毫秒调用一次fn）
 * @example
 input() {
      this.$utils.throttle(() => {
        console.log(1)
      }, 1000)
    }
 */
const throttle = (fn, delay) => {
  let prev = 0
  return function (...args) {
    let now = Date.now()
    if (now - prev > delay) {
      fn.apply(this, args)
      prev = now
    }
  }
}

/**
 * 获取地址栏参数
 * @param variable 参数名称
 * @return {string|boolean}
 * @example
 url:  http://test.com?id=1&image=awesome.jpg
 调用 getQueryVariable("id") 返回 1
 调用 getQueryVariable("image") 返回 "awesome.jpg"
 */
const getQueryVariable = variable => {
  const params = new URLSearchParams(window.location.search)
  return params.get(variable)
}

export default {
  formatDate, // 将日期类型转为日期字符串，可指定输入格式
  formatDateRange, // 开始日期和结束日期如果相等，则返回一个日期，否则返回日期范围
  trim, // 去除字符串两边空格
  uuid, // 生成 uuid 形如：29cbeabba2894d04ac2e3ea99200d937
  clone, // 克隆对象
  listToTree, // 把数组转为tree结构
  treeToList, // 把树结构转为数组
  stringToJson, // 将字符串转为JSON
  isJsonString, // 判断参数是否为JSON字符串
  isNumber, // 判断参数是否Number类型
  isString, // 判断参数是否String类型
  isArray, // 判断参数是否Array类型
  isBlank, // 判断参数是否为空白内容
  isNotBlank, // 判断参数是否为非空白内容
  assertArraysEqual, // 简单判断数组是否相等
  downloadBlob, // 根据Blob参数触发浏览器下载功能
  makeCancelable, // 创建可取消的 Promise，包装Promise添加cancel方法
  isImage, // 根据文件名判断是否为图片
  formatFileSize, // 根据文件size返回语义化单位的size
  getUserInfoByIdCard, // 根据身份证号码获取生日、性别、年龄
  debounce, // 函数防抖
  throttle, // 函数节流
  getQueryVariable, // 获取地址栏参数
}
