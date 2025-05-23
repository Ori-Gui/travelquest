<template>
  <div class="travel-quest-container">
    <main class="main-wrapper">
      <nav class="tabs">
        <button :class="{ active: activeTab === 'info' }" @click="activeTab = 'info'">정보</button>
        <button
          v-if="explorationStarted"
          :class="{ active: activeTab === 'quest' }"
          @click="activeTab = 'quest'"
        >퀘스트</button>
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
        <!-- TODO 01: 임시 섹션 -> 파티 상태 변경 + 변경에 따라 퀘스트 탭 활성화 -->
        <section
          v-if="activeTab === 'info' && !explorationStarted"
          class="start-exploration"
        >
          <div class="warning">⚠️ 경고!</div>
          <p>탐험을 시작하면 돌이킬 수 없습니다.</p>
          <button class="btn-start" @click="startExploration">
            던전 탐험 시작
          </button>
        </section>
        <section
          v-else-if="activeTab === 'info' && explorationStarted"
          class="start-exploration started"
        >
          <div class="checkmark">✅</div>
          <p>탐험이 시작되었습니다!</p>
          <p>퀘스트 탭을 눌러 수행할 퀘스트를 확인하세요!</p>
        </section>
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
const explorationStarted  = ref(false) // '던전탐험시작' 버튼 임시 플래그

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

function startExploration() {
  explorationStarted.value = true // '던전탐험시작' 버튼 임시조작
}
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

.start-exploration {
  text-align: center;
  padding: 1.5rem;
  margin: 1rem 0;
  background-color: #e6fff2;
  border-radius: 8px;
}

.start-exploration .warning {
  font-weight: bold;
  color: #c0392b;
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.start-exploration p {
  margin: 0.25rem 0 1rem;
  color: #333;
}

.btn-start {
  background-color: #e74c3c;
  color: #fff;
  border: 2px solid #000;
  border-radius: 8px;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  cursor: pointer;
  transition: transform 0.1s ease;
}

.btn-start:hover {
  transform: translateY(-2px);
}

.start-exploration.started {
  border-color: #2ecc71;
}
.start-exploration.started .checkmark {
  font-size: 2rem;
  color: #27ae60;
  margin-bottom: 0.5rem;
}
.start-exploration.started p {
  font-weight: bold;
  color: #27ae60;
}
</style>
