// src/api/auth.js
import axiosInstance from '@/lib/axios';
import { useUserStore } from '@/stores/userStore';

export async function refreshToken() {
  const userStore = useUserStore();
  try {
    const { data } = await axiosInstance.post('/auth/refresh');
    userStore.setAccessToken(data.accessToken);
    return data.accessToken;
  } catch (err) {
    userStore.logout();
    throw err;
  }
}
