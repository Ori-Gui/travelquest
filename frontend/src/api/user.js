// src/api/user.js
import axios from '@/lib/axios'

export const registerUser = async (userData) => {
  return axios.post('/api/v1/user/register', {
    userName: userData.nickname,
    email: userData.email,
    birthday: userData.birthdate,
  })
}