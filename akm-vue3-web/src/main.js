import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './assets/main.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'dayjs/locale/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import router from './router'
import stores from './stores'
import { registerGlobalComponents } from '@/components'
import { registerDirectives } from '@/directive'
import { initProviders } from '@/providers/index'
import '@/router/interceptor' // 路由拦截（页面缓存，鉴权）
import { setupErrorHandle } from './error-handle'

const app = createApp(App)
// import '@/providers'
app.use(router)
app.use(stores)

app.use(ElementPlus, {
  locale: zhCn,
})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 注册全局组件
registerGlobalComponents(app)

// 注册全局指令
registerDirectives(app)

// 初始化全局工具类
initProviders(app)

// 全局异常处理模块
setupErrorHandle(app)

app.mount('#app')

export default app
