<template>
  <section class="quest-section">
    <div class="quest-card">
      <header class="quest-header">
        <span class="icon">🤖</span>
        <h2>AI 생성 퀘스트</h2>
      </header>

      <ul class="quest-list">
        <li v-for="(q, i) in quests" :key="q.id" class="quest-item">
          <div class="quest-title">QUEST {{ i + 1 }}: {{ q.title }}</div>
          <div class="quest-desc">{{ q.description }}</div>
          <button class="proof-btn" @click="openModal(q.id)">인증 사진 업로드</button>
        </li>
      </ul>
    </div>

    <QuestVerificationModal
      :visible="showModal"
      :questId="currentQuestId"
      :partyId="partyId"
      @close="closeModal"
      @submitted="onSubmitted"
    />
  </section>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue'
import QuestVerificationModal from '@/components/QuestVerificationModal.vue'
import { fetchQuests } from '@/api/quest'
import { useRoute } from 'vue-router'

const props = defineProps({
  partyId: {
    type: Number,
    required: true
  }
})

const route = useRoute()
const dungeonId = Number(route.params.dungeonId)

const quests = ref([])
const showModal = ref(false)
const currentQuestId = ref(null)
const partyId = props.partyId

onMounted(async () => {
  try {
    quests.value = await fetchQuests(dungeonId)
  } catch (e) {
    console.error('퀘스트 로드 실패', e)
  }
})

function openModal(questId) {
  currentQuestId.value = questId
  showModal.value = true
}

function closeModal() {
  showModal.value = false
}

function onSubmitted() {
  alert('인증이 제출되었습니다!')
  // 필요시 목록 다시 로드
}
</script>

<style scoped>
.quest-section {
  flex: 1;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 1rem;
}

.quest-card {
  width: 100%;
  border: 2px solid #000;
  background: #fff;
  border-radius: 8px;
  padding: 1rem;
}

.quest-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  background: #b3f7c3;
  padding: 0.5rem;
  border-radius: 6px;
}

.icon {
  font-size: 1.2rem;
}

.quest-list {
  list-style: none;
  padding: 0;
  margin: 1rem 0;
}

.quest-item {
  margin-bottom: 1.2rem;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 1.2rem;
}

.quest-title {
  font-weight: bold;
  font-size: 1.15rem;
  line-height: 1.3;
}

.quest-desc {
  margin-top: 0.3rem;
  font-size: 1rem;
  line-height: 1.4;
  color: #333;
}

.proof-btn {
  margin: 0 auto;
  padding: 0.6rem 1.2rem;
  background: #079929;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  align-self: flex-end;
}

.quest-footnote {
  margin-top: 0.75rem;
  font-size: 0.85rem;
  text-align: center;
  color: #555;
}
</style>
