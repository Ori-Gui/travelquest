import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import '@/assets/styles/main.css';
import { useUserStore } from '@/stores/userStore'
import { jwtParser } from '@/utils/jwtParser'

const app = createApp(App)

app.use(createPinia())
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