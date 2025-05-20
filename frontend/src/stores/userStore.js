import { defineStore } from 'pinia'
import { jwtParser } from '@/utils/jwtParser'

export const useUserStore = defineStore('user', {
  state: () => ({
    accessToken: null,
    user: null, // { id, role }
  }),
  actions: {
    setAccessToken(token) {
      this.accessToken = token
      const claims = jwtParser(token)
      this.user = {
        id: claims.sub,
        role: claims.role,
        registStatus: claims.registStatus,
      }
    },
    logout() {
      this.accessToken = null
      this.user = null
    },
  },
  persist: true,
})
