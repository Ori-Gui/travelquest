import { fileURLToPath, URL } from 'node:url'
import { defineConfig }    from 'vite'
import vue                 from '@vitejs/plugin-vue'
import vueDevTools         from 'vite-plugin-vue-devtools'

export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  define: {
    global: 'globalThis',
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      // /uploads/** 요청을 백엔드 8080으로 포워딩
      '/uploads': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path,
      },
    },
  },
})
