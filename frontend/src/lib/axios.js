import axios from 'axios'
import { useUserStore } from '@/stores/userStore'

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  withCredentials: true
})

axiosInstance.interceptors.request.use(config => {
  const userStore = useUserStore()

  const token = userStore.accessToken
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`
  }
  return config
}, error => {
  return Promise.reject(error)
})

export default axiosInstance
