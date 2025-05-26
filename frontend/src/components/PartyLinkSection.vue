<template>
  <section class="party-link-section">
    <h2>💬 나의 파티 목록</h2>
    <br>
    <div v-if="parties.length">
      <ul class="party-list">
        <li v-for="party in parties" :key="party.partyId" class="party-card">
          <div class="party-info">
            <h3 class="party-title">{{ party.title }}</h3>
            <p class="party-status">상태: {{ party.status }}</p>
          </div>
          <button class="btn-go" @click="goToParty(party.dungeonId, party.partyId)">이동</button>
        </li>
      </ul>
    </div>

    <div v-else class="empty-state">
      현재 속한 파티가 없습니다.
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { getMyParties } from '@/api/party'

const router = useRouter()
const userStore = useUserStore()

const parties = ref([])

watch(
() => userStore.user?.id,
async (userId) => {
    if (!userId) return;
    try {
    const response = await getMyParties(userId);
    parties.value = response || [];
    console.log(response)
    } catch (err) {
        console.error('파티 목록 로딩 실패', err);
    }
},
{ immediate: true }
);

function goToParty(dungeonId, partyId) {
  router.push(`/dungeon/${dungeonId}/party/${partyId}`) // 이 경로는 실제 라우터에 맞게 조정
}
</script>

<style scoped>
.party-link-section {
  padding: 1rem;
}

.party-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.party-card {
  background: #f0faff;
  border: 1px solid #cce0f5;
  padding: 1rem;
  border-radius: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.party-title {
  font-size: 1rem;
  font-weight: bold;
}

.party-status {
  font-size: 0.85rem;
  color: #555;
}

.btn-go {
  background: #6cd395;
  border: none;
  color: white;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  cursor: pointer;
}

.empty-state {
  text-align: center;
  padding: 2rem;
  color: #888;
}
</style>
