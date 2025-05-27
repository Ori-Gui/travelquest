<!-- src/views/AdminPartyList.vue -->
<template>
  <section class="admin-party-list">
    <h2 class="heading">전체 파티 목록</h2>
    <table class="party-table">
      <thead>
        <tr>
          <th>파티 ID</th>
          <th>던전 ID</th>
          <th>파티 제목</th>
          <th>리더 ID</th>
          <th>상태</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="party in parties" :key="party.id" @click="goToVerification(party.id)" class="clickable">
          <td>{{ party.id }}</td>
          <td>{{ party.dungeonId }}</td>
          <td>{{ party.title }}</td>
          <td>{{ party.leaderId }}</td>
          <td>{{ party.status }}</td>
        </tr>
      </tbody>
    </table>
    <br>
    <button class="btn-user-manage" @click="goToUserManagement">회원 관리</button>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAllParties } from '@/api/party'

const parties = ref([])
const router = useRouter()

// 회원관리 페이지로 이동
function goToUserManagement() {
  router.push({ name: 'AdminUserList' })
}

onMounted(loadParties)

async function loadParties() {
  try {
    const data = await getAllParties()
    parties.value = data
  } catch (e) {
    console.error('파티 목록 로드 실패', e)
  }
}

function goToVerification(partyId) {
  router.push({ name: 'AdminPartyVerification', params: { partyId } })
}

onMounted(loadParties)
</script>

<style scoped>
.admin-party-list {
  padding: 1rem;
}
.heading {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
.party-table {
  width: 100%;
  border-collapse: collapse;
}
.party-table th,
.party-table td {
  border: 1px solid #ddd;
  padding: 0.5rem;
  text-align: center;
}
btn-user-manage {
  background: transparent;
  color: #2d2d2d;
  border: 2px solid #2d2d2d;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.2s ease;
}
.btn-user-manage:hover {
  background: #2d2d2d;
  color: #fff;
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
.clickable {
  cursor: pointer;
}
.clickable:hover {
  background: #f0f0f0;
}
</style>
