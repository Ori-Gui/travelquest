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
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAllParties } from '@/api/party'

const parties = ref([])
const router = useRouter()

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
.clickable {
  cursor: pointer;
}
.clickable:hover {
  background: #f0f0f0;
}
</style>
