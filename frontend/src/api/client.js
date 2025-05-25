// src/api/client.js
import axios from 'axios'
import { useUserStore } from '@/stores/userStore'

const userStore = useUserStore()

// 1) Axios 인스턴스 생성
const api = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL,
  withCredentials: true, // refreshToken을 쿠키로 쓰는 경우
  headers: {
    'Content-Type': 'application/json'
  }
})

// 2) 요청 인터셉터: accessToken을 헤더에 붙여 보냄
api.interceptors.request.use(config => {
  const token = userStore.accessToken
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 3) 응답 인터셉터: 401 발생 시 refreshToken으로 재발급
let isRefreshing = false
let failedQueue = []

const processQueue = (error, token = null) => {
  failedQueue.forEach(prom => {
    if (error) {
      prom.reject(error)
    } else {
      prom.resolve(token)
    }
  })
  failedQueue = []
}

api.interceptors.response.use(
  res => res,
  err => {
    const { config, response } = err
    if (response?.status === 401 && !config._retry) {
      if (isRefreshing) {
        // 이미 refresh 중인 경우 요청을 대기열에 추가
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject })
        })
          .then(token => {
            config.headers.Authorization = `Bearer ${token}`
            return api(config)
          })
      }

      config._retry = true
      isRefreshing = true

      // refreshToken 으로 토큰 재발급
      return new Promise(async (resolve, reject) => {
        try {
          const { data } = await axios.post(
            '/auth/refresh',
            {},              // body가 필요 없을 수도 있고
            { withCredentials: true } // 쿠키 기반 refreshToken 사용 시
          )

          const newToken = data.accessToken
          userStore.setAccessToken(newToken) // Pinia나 Vuex에 저장

          processQueue(null, newToken)

          // 원래 요청 재시도
          config.headers.Authorization = `Bearer ${newToken}`
          resolve(api(config))
        } catch (refreshError) {
          processQueue(refreshError, null)
          userStore.logout()   // 리프레시 실패 시 로그아웃 처리
          reject(refreshError)
        } finally {
          isRefreshing = false
        }
      })
    }

    return Promise.reject(err)
  }
)

export default api
