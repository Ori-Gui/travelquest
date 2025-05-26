<!-- src/components/AdminPartyVerification.vue -->
<template>
  <section class="admin-verification">
    <button class="btn-back" @click="goToList">
      ← 파티 목록으로 돌아가기
    </button>
    <h2 class="heading">
      파티 검증 · 파티 ID: {{ partyId }}
    </h2>

    <!-- 파티 멤버 목록 -->
    <div class="member-list">
      <h3>파티 멤버 목록</h3>
      <ul>
        <li v-for="m in members" :key="m.id">
          {{ m.userName }} (ID: {{ m.id }})
        </li>
      </ul>
    </div>

    <table class="verification-table">
      <thead>
        <tr>
          <th>퀘스트 ID</th>
          <th>퀘스트 제목</th>
          <th>설명</th>
          <th>작성자 ID</th>
          <th>작성자 이름</th>
          <th>사진 보기</th>
          <th>검증 상태</th>
          <th>저장</th>
          <th>삭제</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="ver in verifications" :key="ver.id">
          <td>{{ ver.questId }}</td>
          <td>{{ ver.title }}</td>
          <td>{{ ver.description }}</td>
          <td>{{ ver.verifiedByUserId }}</td>
          <td>{{ ver.userName }}</td>
          <td>
            <button class="btn-photo" @click="viewPhoto(ver.photoUrl)">
              🔍 보기
            </button>
          </td>
          <td>
            <select v-model="ver.status">
              <option value="COMPLETED">완료</option>
              <option value="FAILED">실패</option>
            </select>
          </td>
          <td>
            <button class="btn-save" @click="saveStatus(ver)">
              💾 저장
            </button>
          </td>
          <td>
            <button class="btn-delete" @click="deleteVerification(ver.id)">
              🗑 삭제
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <button class="btn-finalize" @click="finalizeParty">
      🎯 파티 상태 확정
    </button>

    <!-- 사진 모달 -->
    <div v-if="viewPhotoUrl" class="photo-modal" @click="viewPhotoUrl = null">
      <img :src="fullPhotoUrl" alt="제출된 사진" />
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, computed, defineProps } from 'vue'
import { useRouter } from 'vue-router'
import { getPartyStatus } from '@/api/party'
import { fetchVerifications, updateVerificationStatus } from '@/api/verification'
import { deleteQuestVerification } from '@/api/quest'
import { fetchQuests } from '@/api/quest'
import { getPartyMembers, updatePartyStatus } from '@/api/party'
import { getUserProfile } from '@/api/user'
import { fetchQuest } from '@/api/quest'

const props = defineProps({
  partyId: { type: Number, required: true }
})

// 라우터 인스턴스
const router = useRouter()

// 뒤로가기 핸들러
function goToList() {
  router.push({ name: 'AdminPartyList' })
}

// 상태
const dungeonId = ref(null)
const totalQuests = ref(0)
const verifications = ref([])
const members = ref([])
const viewPhotoUrl = ref(null)

const fullPhotoUrl = computed(() => {
  if (!viewPhotoUrl.value) return ''
  return viewPhotoUrl.value.startsWith('http')
    ? viewPhotoUrl.value
    : `http://192.168.205.51:8080${viewPhotoUrl.value}`
})

async function loadData() {
  try {
    // 0) 파티 정보에서 dungeonId 가져오기
    const partyInfo = await getPartyStatus(props.partyId)
    dungeonId.value = partyInfo.dungeonId

    // 1) 던전 퀘스트 목록 조회 (verified flag 포함)
    const quests = await fetchQuests(dungeonId.value, props.partyId)
    totalQuests.value = quests.length

    // 2) QuestVerification 목록
    const verifs = await fetchVerifications(props.partyId)

    // 3) 파티 멤버 및 이름 매핑
    const memberList = await getPartyMembers(props.partyId)
    const nameMap = {}
    const memberData = await Promise.all(
      memberList.map(async m => {
        const { data } = await getUserProfile(m.id)
        nameMap[m.id] = data.userName || '알 수 없음'
        return { id: m.id, userName: nameMap[m.id] }
      })
    )
    members.value = memberData

    // 4) 퀘스트 title/description 맵
    const questMap = {}
    await Promise.all(
      Array.from(new Set(verifs.map(v => v.questId))).map(async qid => {
        const data = await fetchQuest(qid)
        questMap[qid] = { title: data.title, description: data.description }
      })
    )

    // 5) 최종 데이터 조합
    verifications.value = verifs.map(v => ({
      ...v,
      userName: nameMap[v.verifiedByUserId] || '알 수 없음',
      title: questMap[v.questId]?.title || '-',
      description: questMap[v.questId]?.description || '-'
    }))
  } catch (e) {
    console.error('데이터 로드 실패', e)
  }
}

async function saveStatus(ver) {
  try {
    await updateVerificationStatus(ver.id, ver.status)
    alert(`퀘스트 ${ver.questId} 상태가 ${ver.status} 로 저장되었습니다.`)
  } catch (e) {
    console.error(e)
    alert('저장 중 오류가 발생했습니다.')
  }
}

async function deleteVerification(id) {
  if (!confirm('정말 해당 인증글을 삭제하시겠습니까?')) return
  try {
    await deleteQuestVerification(id)
    alert('인증글이 삭제되었습니다.')
    await loadData()
  } catch (e) {
    console.error(e)
    alert('삭제 중 오류가 발생했습니다.')
  }
}

function viewPhoto(url) {
  viewPhotoUrl.value = url
}

async function finalizeParty() {
  // 모든 퀘스트 인증글이 제출되었는지 확인
  if (verifications.value.length < totalQuests.value) {
    alert('모든 퀘스트 인증글이 제출되어야 파티를 완료할 수 있습니다.')
    return
  }
  // 하나라도 FAILED 이면 FAILED, 아니면 COMPLETED
  const overall = verifications.value.some(v => v.status === 'FAILED')
    ? 'FAILED'
    : 'COMPLETED'
  try {
    await updatePartyStatus(props.partyId, overall)
    alert(`파티 상태를 ${overall === 'COMPLETED' ? '완료' : '실패'} 로 설정했습니다.`)
  } catch (e) {
    console.error(e)
    alert('파티 상태 확정 중 오류가 발생했습니다.')
  }
}

onMounted(loadData)
</script>

<style scoped>
.admin-verification {
  padding: 1rem;
  background: #f9f9f9;
}
.heading {
  margin-bottom: 1rem;
  font-size: 1.25rem;
  color: #146c43;
}
.member-list {
  margin-bottom: 1rem;
}
.member-list h3 {
  margin-bottom: 0.5rem;
  font-size: 1.1rem;
}
.member-list ul {
  list-style: disc;
  padding-left: 1.5rem;
}
.verification-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
}
.verification-table th,
.verification-table td {
  border: 1px solid #ddd;
  padding: 0.5rem;
  text-align: center;
}
.btn-photo,
.btn-save,
.btn-delete {
  padding: 0.3rem 0.6rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.btn-photo {
  background: #079929;
  color: #fff;
}
.btn-save {
  background: #3498db;
  color: #fff;
}
.btn-delete {
  background: #e74c3c;
  color: #fff;
}
.btn-finalize {
  padding: 0.6rem 1.2rem;
  background: #e74c3c;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;
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
  z-index: 1000;
}
.photo-modal img {
  max-width: 90%;
  max-height: 90%;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0,0,0,0.5);
}
.btn-back {
  margin-bottom: 1rem;
  padding: 0.4rem 0.8rem;
  background: #3498db;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.btn-back:hover {
  background: #217dbb;
}
</style>
