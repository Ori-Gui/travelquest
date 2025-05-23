<template>
  <div class="travel-quest-container">
    <main class="main-wrapper">
      <nav class="tabs">
        <button :class="{ active: activeTab === 'info' }" @click="activeTab = 'info'">정보</button>
        <button :class="{ active: activeTab === 'quest' }" @click="activeTab = 'quest'">퀘스트</button>
        <button :class="{ active: activeTab === 'party' }" @click="activeTab = 'party'">파티</button>
        <button
          v-if="isMember"
          :class="{ active: activeTab === 'chat' }"
          @click="activeTab = 'chat'"
        >채팅</button>
      </nav>
      <DungeonInfo v-if="activeTab === 'info' && dungeon"
          :dungeon="dungeon"
          :attractions="attractions"
        />
      <QuestSection v-if="activeTab === 'quest'"/>
      <ChatSection v-if="activeTab === 'chat' && isMember" :partyId="partyId"/>
      <PartySection
        v-if="activeTab === 'party' && userReady"
        :partyId="partyId"
      />
    </main>
  </div>
</template>

<script setup>
import { ref, computed, watchEffect, onMounted } from 'vue';
import DungeonInfo from '@/components/DungeonInfo.vue';
import ChatSection from '@/components/ChatSection.vue';
import QuestSection from '@/components/QuestSection.vue'
import PartySection from '@/components/PartySection.vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/userStore';
import { getPartyMembers } from '@/api/party';
import {
  fetchDungeonDetail,
  fetchDungeonAttractions
} from '@/api/dungeon'

const route = useRoute();
const dungeonId = Number(route.params.dungeonId)
const partyId   = Number(route.params.partyId)
const activeTab = ref('info');

const userStore = useUserStore();
const userReady = computed(() => !!userStore.user?.id);

const partyMembers = ref([]);

watchEffect(async () => {
if (partyId && userReady.value) {
    partyMembers.value = await getPartyMembers(partyId);
}
});
const isMember = computed(() =>
  partyMembers.value.some(m => String(m.id) === String(userStore.user?.id))
);

const dungeon = ref(null)
const attractions = ref([])

onMounted(async () => {
  try {
    // 헬퍼 함수로 대체
    const [d, at] = await Promise.all([
      fetchDungeonDetail(dungeonId),
      fetchDungeonAttractions(dungeonId)
    ])
    dungeon.value     = d
    attractions.value = at
  } catch (err) {
    console.error('던전 정보 로드 실패', err)
  }
})
</script>

<style scoped>
.travel-quest-container {
  background-color: #e6fff2;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-wrapper {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: 1rem;
  justify-content: flex-start;
}

.tabs {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.tabs button {
  padding: 0.4rem 0.8rem;
  border-radius: 8px;
  border: 1px solid #444;
  background: white;
  cursor: pointer;
}

.tabs .active {
  background-color: #b3f7c3;
  font-weight: bold;
}
</style>
