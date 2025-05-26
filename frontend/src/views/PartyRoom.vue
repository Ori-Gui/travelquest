<template>
  <div class="travel-quest-container">
    <main class="main-wrapper">
      <nav class="tabs">
        <button :class="{ active: activeTab === 'info' }" @click="activeTab = 'info'">정보</button>
        <button
          v-if="!isMatching"
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

      <!-- 정보 탭 -->
      <DungeonInfo
        v-if="activeTab === 'info' && dungeon"
        :dungeon="dungeon"
        :attractions="attractions"
      />

      <!-- 매칭중: 탐험 시작 버튼 -->
      <section
        v-if="activeTab === 'info' && isMatching"
        class="start-exploration"
      >
        <div class="warning">⚠️ 경고!</div>
        <p>탐험을 시작하면 돌이킬 수 없습니다.</p>
        <button class="btn-start" @click="startExploration">
          던전 탐험 시작
        </button>
      </section>

      <!-- 진행중 -->
      <section
        v-else-if="activeTab === 'info' && isInProgress"
        class="start-exploration started"
      >
        <div class="checkmark">✅</div>
        <p>탐험이 시작되었습니다!</p>
        <p>퀘스트 탭을 눌러 수행할 퀘스트를 확인하세요!</p>
      </section>

      <!-- 완료 -->
      <section
        v-else-if="activeTab === 'info' && isCompleted"
        class="start-exploration completed"
      >
        <div class="checkmark">🏁</div>
        <p>해당 파티는 탐험을 완료했습니다!</p>
      </section>

      <!-- 실패 -->
      <section
        v-else-if="activeTab === 'info' && isFailed"
        class="start-exploration failed"
      >
        <div class="checkmark">❌</div>
        <p>해당 파티는 탐험을 실패했습니다!</p>
      </section>

      <!-- 퀘스트, 채팅, 파티 섹션 -->
      <QuestSection v-if="activeTab === 'quest'" :partyId="partyId" />
      <ChatSection   v-if="activeTab === 'chat'  && isMember" :partyId="partyId" />
      <PartySection  v-if="activeTab === 'party' && userReady"  :partyId="partyId" />
    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import DungeonInfo   from '@/components/DungeonInfo.vue'
import QuestSection  from '@/components/QuestSection.vue'
import PartySection  from '@/components/PartySection.vue'
import ChatSection   from '@/components/ChatSection.vue'
import { useUserStore } from '@/stores/userStore'
import { getPartyStatus, updatePartyStatus } from '@/api/party'
import { getPartyMembers } from '@/api/party'
import { fetchDungeonDetail, fetchDungeonAttractions } from '@/api/dungeon'

// 라우터 & props
const route     = useRoute()
const dungeonId = Number(route.params.dungeonId)
const partyId   = Number(route.params.partyId)

// 탭 관리
const activeTab = ref('info')

// 유저 & 파티 멤버 조회
const userStore    = useUserStore()
const userReady    = computed(() => !!userStore.user?.id)
const partyMembers = ref([])
async function loadPartyMembers() {
  if (userReady.value) {
    partyMembers.value = await getPartyMembers(partyId)
  }
}
const isMember = computed(() =>
  partyMembers.value.some(m => String(m.id) === String(userStore.user.id))
)

// 던전 정보
const dungeon     = ref(null)
const attractions = ref([])
async function loadDungeon() {
  const [d, at] = await Promise.all([
    fetchDungeonDetail(dungeonId),
    fetchDungeonAttractions(dungeonId)
  ])
  dungeon.value     = d
  attractions.value = at
}

// 파티 상태
const party    = ref(null)
const isMatching   = computed(() => party.value?.status === 'MATCHING')
const isInProgress = computed(() => party.value?.status === 'IN_PROGRESS')
const isCompleted  = computed(() => party.value?.status === 'COMPLETED')
const isFailed     = computed(() => party.value?.status === 'FAILED')

async function loadParty() {
  party.value = await getPartyStatus(partyId)
}

// 탐험 시작 버튼 핸들러
async function startExploration() {
  await updatePartyStatus(partyId, 'IN_PROGRESS')
  await loadParty()
  activeTab.value = 'info'
}

// 컴포넌트 로드 시
onMounted(async () => {
  await Promise.all([
    loadDungeon(),
    loadParty(),
    loadPartyMembers()
  ])
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
