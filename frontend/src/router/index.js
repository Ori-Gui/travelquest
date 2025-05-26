import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/userStore'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Main',
      component: () => import('@/views/DungeonExplorer.vue')
    },
    {
      path: '/map',
      name: 'DungeonExplorer',
      component: () => import('@/views/DungeonExplorer.vue')
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
    {
      path: '/me/profile',
      name: 'ProfileChange',
      component: () => import('@/views/ProfileChange.vue')
    },
    {
      path: '/me/password',
      name: 'PasswordChange',
      component: () => import('@/views/PasswordChange.vue')
    },
    {
      path: '/admin/parties',
      name: 'AdminPartyList',
      component: () => import('@/views/AdminPartyList.vue'),
      meta: { requiresAdmin: true }
    },
    {
      path: '/admin/parties/:partyId/verification',
      name: 'AdminPartyVerification',
      component: () => import('@/components/AdminPartyVerification.vue'),
      props: route => ({ partyId: Number(route.params.partyId) }),
      meta: { requiresAdmin: true }
    }
  ]
})

router.beforeEach((to, from) => {
  const userStore = useUserStore()

  if (to.matched.some(r => r.meta?.requiresAdmin) && userStore.user?.role !== 'ADMIN') {
    return { name: 'Login' }
  }

  if (to.name === 'Main' && userStore.user?.role === 'ADMIN') {
    return { name: 'AdminPartyList' }
  }
})

export default router
