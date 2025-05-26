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
      <div v-else class="welcome">
        {{ userEmoji }} {{ userName }}님 환영합니다
      </div>

      <button class="btn-hamburger" @click="toggleMenu">
        ☰
      </button>
    </div>

    <aside v-if="isMenuOpen" class="side-menu">
      <ul>
        <li @click="navigateTo('me')">마이페이지</li>
        <li @click="navigateTo('logout')">로그아웃</li>
        <li @click="closeMenu">닫기</li>
      </ul>
    </aside>

    <div v-if="isMenuOpen" class="menu-overlay" @click="closeMenu"></div>
  </header>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { useRouter } from 'vue-router';
import { getUserProfile, logout }  from '@/api/user'

const userStore  = useUserStore()
const isLoggedIn = computed(() => !!userStore.user?.id)
const userName   = ref('')
const userJob    = ref('')

const jobEmojiMap = {
  WARRIOR:   '🛡️',
  MAGE:      '🪄',
  HEALER:    '💉',
  RANGER:    '🏹',
  BARD:      '🎵',
  TRICKSTER: '🃏',
  THIEF:     '🗡️',
  MECHANIC:  '🔧'
}

const userEmoji = computed(() =>
  jobEmojiMap[userJob.value] || '🎲'
)

watch(isLoggedIn, async logged => {
  if (!logged) return
  try {
    const res = await getUserProfile(userStore.user.id)
    userName.value = res.data.userName
    userJob.value  = res.data.jobClassCode
  } catch (e) {
    console.error(e)
  }
})

const router = useRouter();
const isMenuOpen = ref(false);

function toggleMenu() {
  isMenuOpen.value = !isMenuOpen.value;
}

function closeMenu() {
  isMenuOpen.value = false;
}

async function navigateTo(tab) {
  // adjust routes as needed
  if (tab === 'me') router.push('/me');
  else if (tab === 'logout') {
    // 로그아웃 확인 대화상자
    if (window.confirm('정말 로그아웃 하시겠습니까?')) {
      try {
        // 로그아웃 액션 (동기/비동기 상관없이 호출)
        await logout()
        await userStore.logout()
        // 상태 초기화 후 로그인 페이지로 이동
        router.push('/login')
      } catch (err) {
        console.error('로그아웃 실패:', err)
      }
    }
  }
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

.header-actions {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.btn-login {
  padding: 0.3rem 0.8rem;
  background: #6cd395;
  border: 1px solid #ffffff;
  border-radius: 9px;
  font-size: 0.9rem;
  color: #ffffff;
  text-decoration: none;
}

.welcome {
  font-size: 0.95rem;
  color: #333;
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
