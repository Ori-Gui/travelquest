import { fileURLToPath, URL } from 'node:url'
import { defineConfig }    from 'vite'
import vue                 from '@vitejs/plugin-vue'
// import vueDevTools         from 'vite-plugin-vue-devtools'

export default defineConfig({
  plugins: [
    vue(),
    // vueDevTools(),
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
      '/images': {
        target: 'http://192.168.205.51:8080',
        changeOrigin: true,
        rewrite: (path) => path,
      },
    },
  },
})
