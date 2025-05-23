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
      path: '/dungeon/:id',
      name: 'DungeonDetail',
      component: () => import('@/views/DungeonDetail.vue'), // Lazy loaded
      props: true
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/UserLogin.vue')
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
    },
    {
      path: '/party/:id',
      name: 'PartyRoom',
      component: () => import('@/views/PartyRoom.vue')
    },
    {
      path: '/me',
      name: 'MyPage',
      component: () => import('@/views/MyPage.vue')
    },
  ],
})

export default router
