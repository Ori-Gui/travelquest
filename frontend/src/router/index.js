import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/dungeon',
      name: 'DungeonExplorer',
      component: () => import('@/views/DungeonDetail.vue') // Lazy loaded
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/LoginView.vue')  // Lazy loading
    }
  ],
})

export default router
