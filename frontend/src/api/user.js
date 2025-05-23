// src/api/user.js
import axios from '@/lib/axios'

export const registerUser = async (userData) => {
  return axios.post('/api/v1/user/register', {
    userName: userData.nickname,
    email: userData.email,
    birthday: userData.birthdate,
  })
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