<template>
  <section class="cleared-dungeons">
    <h2>🗺️ 클리어한 던전 목록</h2>

    <div v-if="dungeons.length">
      <ul class="dungeon-list">
        <li v-for="dungeon in dungeons" :key="dungeon.id" class="dungeon-card">
          <h3 class="dungeon-title">{{ dungeon.title }}</h3>
          <p class="dungeon-location">{{ dungeon.region }} · {{ dungeon.city }}</p>
          <p class="dungeon-dates">여행 기간: {{ dungeon.startDate }} ~ {{ dungeon.endDate }}</p>
          <p class="dungeon-status">상태: ✅ 클리어 완료</p>
        </li>
      </ul>
    </div>

    <div v-else class="empty-state">
      아직 클리어한 던전이 없습니다.
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getClearedDungeons } from '@/api/user'
import { useUserStore } from '@/stores/userStore';

// 더미 데이터 (테스트용)
const dungeons = ref([]);
const userStore = useUserStore();

// 예시 로딩
onMounted(async () => {
  try {
    const response = await getClearedDungeons(userStore.user?.id); // ⬅ await 필수
    dungeons.value = response.data;
  } catch (err) {
    console.error("던전 클리어 정보 불러오기 실패", err);
  }
});
</script>

<style scoped>
.cleared-dungeons {
  padding: 1rem;
}

h2 {
  font-size: 1.2rem;
  margin-bottom: 1rem;
  color: #2d5f2e;
}

.dungeon-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.dungeon-card {
  background: #f4fff4;
  border: 1px solid #cceccc;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
}

.dungeon-title {
  font-weight: bold;
  font-size: 1rem;
  margin-bottom: 0.25rem;
}

.dungeon-location,
.dungeon-dates,
.dungeon-status {
  font-size: 0.85rem;
  color: #555;
}

.empty-state {
  text-align: center;
  color: #999;
  padding: 2rem;
}
</style>
