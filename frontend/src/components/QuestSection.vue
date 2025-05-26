<template>
  <section class="quest-section">
    <div class="quest-card">
      <header class="quest-header">
        <span class="icon">🤖</span>
        <h2>AI 생성 퀘스트</h2>
      </header>

      <ul class="quest-list">
        <li
          v-for="(q, i) in quests"
          :key="q.id"
          class="quest-item"
        >
          <div class="quest-title">
            QUEST {{ i + 1 }}: {{ q.title }}
          </div>
          <div class="quest-desc">{{ q.description }}</div>

          <template v-if="submittedMap.has(q.id)">
            <span class="submitted-text">인증 사진 제출 완료</span>
            <button
              class="view-btn"
              @click="viewPhoto(submittedMap.get(q.id))"
            >사진 보기</button>
          </template>
          <button
            v-else
            class="proof-btn"
            @click="openModal(q.id)"
          >
            인증 사진 업로드
          </button>
        </li>
      </ul>
    </div>

    <!-- 검증 모달 -->
    <QuestVerificationModal
      :visible="showModal"
      :questId="currentQuestId"
      :partyId="partyId"
      :verifiedByUserId="currentUserId"
      @close="closeModal"
      @submitted="onSubmitted"
    />

    <!-- 사진 뷰어 모달 -->
    <div v-if="viewPhotoUrl" class="photo-modal" @click="closePhoto">
      <img :src="viewPhotoUrl" alt="제출된 사진" />
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue'
import { useRoute } from 'vue-router'
import QuestVerificationModal from '@/components/QuestVerificationModal.vue'
import { fetchQuests } from '@/api/quest'
import { fetchVerifications } from '@/api/verification'
import { useUserStore } from '@/stores/userStore'

// Props, Params, User (변경 없음)
const props = defineProps({ partyId: Number })
const partyId = props.partyId
const route = useRoute()
const dungeonId = Number(route.params.dungeonId)
const userStore = useUserStore()
const currentUserId = userStore.user.id

// State 변경: Map과 뷰어 URL 추가
const quests = ref([])
const submittedMap = ref(new Map())  // questId → photoUrl
const showModal = ref(false)
const currentQuestId = ref(null)
const viewPhotoUrl = ref(null)

// 데이터 로딩
async function loadData() {
  try {
    const [qs, vs] = await Promise.all([
      fetchQuests(dungeonId),
      fetchVerifications(partyId)
    ])
    quests.value = qs
    // Map 생성
    submittedMap.value = new Map(vs.map(v => [v.questId, v.photoUrl]))
    console.log(submittedMap);
    
  } catch (e) {
    console.error('데이터 로드 실패', e)
  }
}
onMounted(loadData)

// 모달 열기/닫기
function openModal(id) {
  currentQuestId.value = id
  showModal.value = true
}
function closeModal() {
  showModal.value = false
}

// 제출 후 재로드
async function onSubmitted() {
  alert('인증이 제출되었습니다! 🎉')
  await loadData()
  closeModal()
}

// 사진 보기/닫기
function viewPhoto(url) {
  viewPhotoUrl.value = url
}
function closePhoto() {
  viewPhotoUrl.value = null
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

.submitted-text {
  display: inline-block;
  padding: 0.6rem 1.2rem;
  color: #555;
  font-weight: bold;
  border-radius: 6px;
  background: #e0e0e0;
}

.view-btn {
  margin-left: 0.5rem;
  padding: 0.4rem 0.8rem;
  background: #fff;
  border: 1px solid #079929;
  color: #079929;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
}

.photo-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}
.photo-modal img {
  max-width: 90%;
  max-height: 90%;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0,0,0,0.5);
}
</style>
