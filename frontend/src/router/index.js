import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/map',
      name: 'DungeonExplorer',
      component: () => import('@/views/DungeonExplorer.vue') // Lazy loaded
    },
    {
      path: '/dungeon',
      name: 'DungeonDetail',
      component: () => import('@/views/DungeonDetail.vue') // Lazy loaded
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/UserLogin.vue')  // Lazy loading
    },
    {
      path: '/oauth/callback',
      name: 'OAuthCallback',
      component: () => import('@/components/OAuthCallback.vue')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/UserRegister.vue')
    },
    {
      path: '/mbti',
      name: 'MBTITEST',
      component: () => import('@/views/MbtiTest.vue')
    },
    {
      path: '/mbti/result/:mbtiCode',
      name: 'MbtiResult',
      component: () => import('@/views/MbtiResult.vue')
    }
  ],
})

export default router
