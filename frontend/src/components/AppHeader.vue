<template>
  <header class="app-header">
    <router-link to="/map">
      <img src="@/assets/travelquest_logo2.png" alt="Travel Quest" class="logo" />
    </router-link>
    <button class="btn-hamburger" @click="toggleMenu">
      ☰
    </button>

    <!-- Side menu panel -->
    <aside v-if="isMenuOpen" class="side-menu">
      <ul>
        <li @click="navigateTo('me')">마이페이지</li>
        <li @click="navigateTo('quest')">퀘스트</li>
        <li @click="navigateTo('party')">파티</li>
        <li @click="navigateTo('chat')">채팅</li>
        <li @click="closeMenu">닫기</li>
      </ul>
    </aside>

    <!-- Optional overlay to close menu when clicking outside -->
    <div v-if="isMenuOpen" class="menu-overlay" @click="closeMenu"></div>
  </header>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const isMenuOpen = ref(false);

function toggleMenu() {
  isMenuOpen.value = !isMenuOpen.value;
}

function closeMenu() {
  isMenuOpen.value = false;
}

function navigateTo(tab) {
  // adjust routes as needed
  if (tab === 'me') router.push('/me');
  else if (tab === 'quest') router.push('/quest');
  else if (tab === 'party') router.push('/party');
  else if (tab === 'chat') router.push('/chat');
  closeMenu();
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
  cursor: pointer;
}

.side-menu {
  position: absolute;
  top: 56px;
  right: 1rem;
  width: 180px;
  background: #fff;
  border: 2px solid #6cd395;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 1001;
}

.side-menu ul {
  list-style: none;
  margin: 0;
  padding: 0.5rem 0;
}

.side-menu li {
  padding: 0.5rem 1rem;
  cursor: pointer;
  font-weight: 500;
  color: #333;
  transition: background 0.2s;
}

.side-menu li:hover {
  background: #e6fff2;
}

.menu-overlay {
  position: fixed;
  top: 56px;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.2);
  z-index: 1000;
}
</style>