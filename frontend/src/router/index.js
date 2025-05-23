import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Main',
      component: () => import('@/views/DungeonExplorer.vue') // Lazy loaded
    },
    {
      path: '/map',
      name: 'DungeonExplorer',
      component: () => import('@/views/DungeonExplorer.vue') // Lazy loaded
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
      path: '/dungeon/:dungeonId',
      component: () => import('@/views/DungeonLayout.vue'),
      props: true,
      children: [
        {
          path: '',
          name: 'DungeonDetail',
          component: () => import('@/views/DungeonDetail.vue'),
          props: true
        },
        {
          path: 'party/:partyId',
          name: 'PartyRoom',
          component: () => import('@/views/PartyRoom.vue'),
          props: true
        }
      ]
    },
    {
      path: '/me',
      name: 'MyPage',
      component: () => import('@/views/MyPage.vue')
    },
  ],
})

export default router
