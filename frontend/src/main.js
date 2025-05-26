import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import '@/assets/styles/main.css';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { useUserStore } from '@/stores/userStore'
import { jwtParser } from '@/utils/jwtParser'

const app = createApp(App)
// 1) Pinia 인스턴스 생성
const pinia = createPinia()
// 2) Persistedstate 플러그인 등록
pinia.use(piniaPluginPersistedstate)
// 3) Vue에 Pinia 적용
app.use(pinia)
app.use(router)

app.mount('#app')

const userStore = useUserStore()

const token = document.cookie
  .split('; ')
  .find(cookie => cookie.startsWith('accessToken='))?.split('=')[1]

if (token && !userStore.user) {
  const claims = jwtParser(token)
  userStore.setAccessToken(token)
}