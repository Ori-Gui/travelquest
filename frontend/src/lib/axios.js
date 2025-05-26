// lib/axios.js
import axios from 'axios'
import { useUserStore } from '@/stores/userStore'

const axiosInstance = axios.create({
  baseURL: 'http://192.168.205.51:8080',
  withCredentials: true
})

// 요청 인터셉터: accessToken 헤더에 추가
axiosInstance.interceptors.request.use(config => {
// ── 리프레시 전용 요청이면 Authorization 헤더를 완전히 지운다
if (config.url?.endsWith('/auth/refresh')) {
  delete config.headers['Authorization']
  return config
}

// 그 외 요청엔 정상적으로 accessToken 헤더 추가
const userStore = useUserStore()
const token = userStore.accessToken
if (token) {
  config.headers['Authorization'] = `Bearer ${token}`
}
  return config
}, error => {
  return Promise.reject(error)
})

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

// 응답 인터셉터: 401 나오면 refreshToken으로 재발급
axiosInstance.interceptors.response.use(
  res => res,
  err => {
    const { config, response } = err;
    if (response?.status === 401 && !config._retry) {
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        }).then(token => {
          config.headers['Authorization'] = `Bearer ${token}`;
          return axiosInstance(config);
        });
      }

      config._retry = true;
      isRefreshing = true;
      const userStore = useUserStore();

      return new Promise((resolve, reject) => {
        axiosInstance.post('/auth/refresh')
          .then(({ data }) => {
            const newToken = data.accessToken;
            userStore.setAccessToken(newToken);
            processQueue(null, newToken);

            config.headers['Authorization'] = `Bearer ${newToken}`;
            resolve(axiosInstance(config));
          })
          .catch(refreshError => {
            processQueue(refreshError, null);
            userStore.logout();
            reject(refreshError);
          })
          .finally(() => {
            isRefreshing = false;
          });
      });
    }

    return Promise.reject(err);
  }
);


export default axiosInstance
