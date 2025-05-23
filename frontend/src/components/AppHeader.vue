<template>
  <header class="app-header">
    <router-link to="/map">
      <img src="@/assets/travelquest_logo2.png" alt="Travel Quest" class="logo" />
    </router-link>
    <div class="header-actions">
      <router-link
        v-if="!isLoggedIn"
        to="/login"
        class="btn-login"
      >
        로그인
      </router-link>
      <router-link
        v-else
        to="/mypage"
        class="btn-mypage"
      >
        👤
      </router-link>

      <button class="btn-hamburger" @click="onMenu">
        ☰
      </button>
    </div>
  </header>
</template>

<script setup>
import { computed } from 'vue'
import { useUserStore } from '@/stores/userStore'

const userStore  = useUserStore()
const isLoggedIn = computed(() => !!userStore.user?.id)

function onMenu() {
  // 메뉴 열기 로직
}
</script>

<style scoped>
.app-header {
  position: sticky;
  top: 0;
  height: 56px;
  background: #b8f5c3;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1rem;
  border-bottom: 2px solid #6cd395;
  z-index: 1000;
}
.logo {
  height: 32px;
  object-fit: contain;
}
.btn-hamburger {
  width: 32px;
  height: 32px;
  font-size: 1.2rem;
  line-height: 1;
  background: #6cd395;
  border: none;
  border-radius: 6px;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
