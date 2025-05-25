<template>
  <div v-if="userReady" class="my-page-container">
    <!-- 완료된 던전 퀘스트 섹션 -->
    <section class="quests-section">
      <h2 class="section-title">완료된 던전 퀘스트</h2>
      <ul class="quests-list">
        <li v-for="(q, i) in quests" :key="q.id" class="quest-item">
          <div class="quest-index">{{ i + 1 + currentPage * pageSize }}</div>
          <div class="quest-content">
            <h3 class="quest-title">{{ q.title }}</h3>
            <p class="quest-description">{{ q.description }}</p>
          </div>
        </li>
      </ul>
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 0">이전</button>
        <span>Page {{ currentPage + 1 }}</span>
        <button @click="nextPage" :disabled="!hasMore">다음</button>
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
import { getMyCompletedQuests } from '@/api/question';

// 로그인된 유저 정보
const userStore = useUserStore();
const rawUser = userStore.user;
const userReady = computed(() => Boolean(rawUser && rawUser.id));

// 페이지네이션 설정
const pageSize = 5;
const currentPage = ref(0);
const quests = ref([]);
const hasMore = ref(false);

// offset 기반 요청: pageIndex를 offset으로 변환
const fetchQuests = async (pageIndex = 0) => {
  try {
    const offset = pageIndex * pageSize;
    const data = await getMyCompletedQuests(rawUser.id, offset, pageSize);
    quests.value = data.content ?? data;
    // hasMore 계산
    if (data.last !== undefined) {
      hasMore.value = !data.last;
    } else {
      hasMore.value = data.length === pageSize;
    }
  } catch (err) {
    console.error('완료된 퀘스트 조회 실패', err);
  }
};

const prevPage = () => {
  if (currentPage.value > 0) {
    currentPage.value--;
    fetchQuests(currentPage.value);
  }
};
const nextPage = () => {
  if (hasMore.value) {
    currentPage.value++;
    fetchQuests(currentPage.value);
  }
};

watch(userReady, (ready) => {
  if (ready) fetchQuests(0);
}, { immediate: true });
</script>

<style scoped>
.my-page-container {
  background-color: #f4f9f4;
  height: 100vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}
.quests-section {
  width: 100%;
  max-width: 600px;
  height: 90vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
.section-title {
  margin-bottom: 1rem;
  font-size: 1.5rem;
  font-weight: 700;
  color: #2d5f2e;
  text-align: center;
}
.quests-list {
  list-style: none;
  padding: 0;
  margin: 0;
  flex: 1;
  overflow-y: auto;
}
.quest-item {
  display: flex;
  align-items: flex-start;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  padding: 1rem;
  margin-bottom: 1rem;
  transition: transform 0.2s;
}
.quest-item:hover {
  transform: translateY(-2px);
}
.quest-index {
  flex-shrink: 0;
  width: 32px;
  height: 32px;
  background-color: #6cd395;
  color: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-right: 1rem;
}
.quest-content {
  flex: 1;
}
.quest-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
  color: #333;
}
.quest-description {
  margin-top: 0.5rem;
  font-size: 0.95rem;
  color: #555;
  line-height: 1.4;
}
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  padding: 0.5rem 0;
}
.pagination button {
  background: #6cd395;
  border: none;
  color: #fff;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background 0.2s;
}
.pagination button:disabled {
  background: #ccc;
  cursor: default;
}
.pagination span {
  font-weight: 600;
  color: #333;
}
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  color: #666;
  font-size: 1.1rem;
}
</style>