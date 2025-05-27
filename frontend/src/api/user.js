// src/api/user.js
import axios from '@/lib/axios'

export const registerUser = async (userData) => {
  return axios.post('/api/v1/user/register', {
    userName: userData.userName,
    email: userData.email,
    birthday: userData.birthday,
    profileImageUrl: userData.profileImageUrl
  })
}

export const logout = async() => {
  return axios.post(`/api/v1/user/logout`);
}

export function getUserProfile(userId) {
  return axios.get(`/api/v1/user/${userId}`);
}

export function getUserMissions(userId) {
  return axios.get(`/api/v1/user/${userId}/missions`);
}

export function getClearedDungeons(userId) {
  return axios.get(`/api/v1/user/${userId}/clear/dungeon`)
}

export function updateUserProfile(userId, payload) {
  return axios.put(`/api/v1/user/${userId}`, payload);
}

// 전체 유저 조회 (관리자)
export const getAllUsers = async () => {
  const response = await axios.get('/api/v1/user')
  return response.data
}

// 특정 유저 삭제 (관리자)
export const deleteUser = async (userId) => {
  await axios.delete(`/api/v1/user/${userId}`)
}
