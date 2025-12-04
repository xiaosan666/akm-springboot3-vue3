import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// https://vite.dev/config/
export default defineConfig(({ command, mode }) => {
  // 根据当前工作目录中的 `mode` 加载 .env 文件
  const env = loadEnv(mode, process.cwd())

  // 解析代理配置
  const createProxy = (proxyList) => {
    const proxy = {}
    try {
      const list = JSON.parse(proxyList)
      list.forEach(([prefix, target]) => {
        proxy[prefix] = {
          target: target,
          changeOrigin: true,
          rewrite: (path) => path.replace(new RegExp('^' + prefix), ''),
        }
      })
    } catch (error) {
      console.error('代理配置解析失败:', error)
    }
    return proxy
  }

  return {
    // Vite 配置
    plugins: [
      vue(),
      vueDevTools(),
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    server: {
      host: true,
      port: env.VITE_PORT || 3000,
      open: true,
      cors: true,
      proxy: env.VITE_PROXY ? createProxy(env.VITE_PROXY) : {},
    },
    build: {
      outDir: 'dist',
      sourcemap: command === 'serve',
      // 生产环境移除 console
      minify: 'esbuild',
    },
  }
})
