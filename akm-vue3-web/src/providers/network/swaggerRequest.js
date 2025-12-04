import axios from 'axios'
import helper from '@/providers/helper'

const swaggerRequest = axios.create({
  baseURL: import.meta.env.VITE_BASE_API,
  timeout: 20000,
  headers: { 'Content-Type': 'application/json;charset=UTF-8' },
  responseType: 'json',
  withCredentials: false,
})

// 响应拦截器
swaggerRequest.interceptors.response.use(
  response => {
    if (response && response.status === 200) {
      return response.data
    } else {
      return Promise.reject(response)
    }
  },
  error => {
    let response = error.response
    if (!response) {
      helper.errorMessage('网络出错或请求超时')
      return Promise.reject(error)
    }
    return Promise.reject(error)
  }
)

export default swaggerRequest
