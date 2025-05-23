<template>
  <!-- user가 준비된 후에만 페이지 렌더링 -->
  <div v-if="userReady" class="my-page-container">
    <!-- 공통 헤더 -->
    <AppHeader />

    <!-- 프로필 섹션 -->
    <section class="profile-section">
      <div class="avatar">
        <img :src="profile?.avatarUrl || defaultAvatar" alt="Avatar" />
      </div>
      <div class="profile-details">
        <div class="role-level">
          <span class="job-emoji">{{ getJobEmoji(profile?.jobClassCode) }}</span>
          <span class="job-text">{{ jobNameMap[profile?.jobClassCode] }}</span>
        </div>
        <div class="nickname">{{ profile?.userName }}</div>
        <div class="explorer-badge">{{ explorerTitle }}</div>
        <div class="progress-bar">
          <div class="progress-fill" :style="{ width: progressPercent + '%' }"></div>
        </div>
      </div>
      <button class="btn-edit" @click="onEditProfile">프로필 수정</button>
    </section>

    <!-- 탭 네비게이션 -->
    <nav class="mypage-tabs">
      <button
        v-for="tab in tabs"
        :key="tab.name"
        :class="{ active: activeTab === tab.name }"
        @click="activeTab = tab.name"
      >
        <span class="tab-icon">{{ tab.icon }}</span>
        {{ tab.label }}
      </button>
    </nav>

    <!-- 탭 내용 -->
    <section class="tab-content">
      <div v-if="activeTab === 'missions'">
        <ul class="missions-list">
          <li v-for="(m, i) in missions" :key="m.id">
            <span class="mission-index">{{ i + 1 }}.</span>
            <span class="mission-title">{{ m.title }}</span>
          </li>
        </ul>
      </div>
      <div v-else-if="activeTab === 'achievements'">
        <!-- 달성 기록 섹션 -->
      </div>
      <div v-else-if="activeTab === 'partyChat'">
        <!-- 파티 채팅 링크 -->
      </div>
      <div v-else-if="activeTab === 'quests'">
        <!-- 퀘스트 목록 섹션 -->
      </div>
      <div v-else-if="activeTab === 'settings'">
        <!-- 설정 섹션 -->
      </div>
    </section>
  </div>

  <div v-else class="loading-container">
    로딩 중…
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useUserStore } from '@/stores/userStore';
import AppHeader from '@/components/AppHeader.vue';
import { getUserProfile } from '@/api/user';

// 기본 아바타 URL 생성 (Vite asset import)
const defaultAvatar = new URL('../assets/default-avatar.png', import.meta.url).href;

// 로그인된 유저의 기본 정보 (store)
const userStore = useUserStore();
const rawUser = userStore.user;
const userReady = computed(() => Boolean(rawUser && rawUser.id));

// 서버에서 받아올 프로필 데이터
const profile = ref(null);
const missions = ref([]);

// 프로필을 가져오는 함수 분리
const fetchProfile = async () => {
  console.log('fetchProfile called, userId=', rawUser.id);
  try {
    const response = await getUserProfile(rawUser.id)
    console.log('profile data:', response.data);
    profile.value = response.data;
    missions.value = data.missions || [];
  } catch (err) {
    console.error('프로필 불러오기 실패', err);
  }
};

// userReady가 true가 될 때와 컴포넌트 마운트 시 즉시 실행
watch(userReady, (ready) => {
  if (ready) fetchProfile();
}, { immediate: true });

// 탭 설정
const tabs = [
  { name: 'missions', label: '미션 포트', icon: '🏅' },
  { name: 'achievements', label: '달성 기록', icon: '🏆' },
  { name: 'partyChat', label: '파티 채팅', icon: '💬' },
  { name: 'quests', label: '퀘스트', icon: '🗺️' },
  { name: 'settings', label: '정보 변경', icon: '⚙️' }
];
const activeTab = ref('missions');

// 탐험가 칭호 및 경험치 바
const explorerTitle = computed(() => profile.value ? `${profile.value.userName}의 탐험가` : '');
const progressPercent = computed(() => profile.value ? Math.min((profile.value.exp / profile.value.nextLevelExp) * 100, 100) : 0);

// 직업 매핑
const jobNameMap = {
  WARRIOR: '전사', MAGE: '마법사', HEALER: '치유사', RANGER: '궁수',
  BARD: '음유시인', TRICKSTER: '트릭스터', WIZARD: '마법사', THIEF: '도적', MECHANIC: '기계공'
};
const jobEmojiMap = {
  WARRIOR: '🛡️', MAGE: '🪄', HEALER: '💉', RANGER: '🏹',
  BARD: '🎵', TRICKSTER: '🃏', WIZARD: '🔮', THIEF: '🗡️', MECHANIC: '🔧'
};
function getJobEmoji(job) { return jobEmojiMap[job] || '🎯'; }

// 프로필 수정 탭 이동
function onEditProfile() { activeTab.value = 'settings'; }
</script>

<style scoped>
/* 스타일은 변경되지 않음 */
.my-page-container { background-color: #e6fff2; min-height: 100vh; }
.profile-section { display: flex; align-items: center; padding: 1rem; gap: 1rem; background: #fff; border-bottom: 2px solid #a0d8a0; }
.avatar img { width: 64px; height: 64px; border-radius: 50%; object-fit: cover; border: 2px solid #6cd395; }
.profile-details { flex: 1; }
.role-level { display: flex; align-items: center; gap: 0.5rem; font-weight: bold; font-size: 1.1rem; }
.nickname { margin-top: 0.25rem; font-size: 1rem; color: #333; }
.explorer-badge { display: inline-block; margin-top: 0.5rem; background: #ffecb3; padding: 0.25rem 0.75rem; border-radius: 12px; font-size: 0.85rem; color: #6b4f01; }
.progress-bar { margin-top: 0.5rem; background: #f0f0f0; height: 8px; border-radius: 4px; overflow: hidden; }
.progress-fill { height: 100%; background: #6cd395; transition: width 0.3s; }
.btn-edit { background: white; border: 1px solid #6cd395; border-radius: 8px; padding: 0.5rem 1rem; cursor: pointer; font-weight: bold;	color: #6cd395; }
.mypage-tabs { display: flex; gap: 0.5rem; padding: 0.75rem 1rem; background: #fff; border-bottom: 1px solid #ccc; flex-wrap: nowrap; overflow: hidden; }
.mypage-tabs button { flex: 1 1 auto; min-width: 0; white-space: nowrap; font-size: 0.8rem; padding: 0.4rem; display: flex; align-items: center; justify-content: center; gap: 0.25rem; background: transparent; border: none; cursor: pointer; color: #666; }
.mypage-tabs button.active { color: #2d5f2e; font-weight: bold; border-bottom: 2px solid #2d5f2e; }
.tab-content { padding: 1rem; }
.missions-list { list-style: none; padding: 0; margin: 0; }
.missions-list li { display: flex; align-items: center; gap: 0.5rem; padding: 0.5rem 0; border-bottom: 1px dashed #ddd; }
.mission-index { font-weight: bold; }
.loading-container { display: flex; justify-content: center; align-items: center; height: 100vh; color: #666; }
</style>
