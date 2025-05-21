<template>
  <div>
    <div class="party-controls">
      <span>파티 목록</span>
      <button @click="showModal = true">+ 파티 만들기</button>
    </div>

    <div class="party-list">
      <PartyCard
        v-for="party in parties"
        :key="party.id"
        :title="party.title"
        :desc="party.desc"
        :currentMembers="party.currentMembers"
        :maxMembers="party.maxMembers"
      />
    </div>

    <PartyModal
      v-if="showModal"
      :dungeonId="dungeonId"
      @created="fetchParties"
      @close="showModal = false"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import PartyCard from './PartyCard.vue'
import PartyModal from './PartyCreateModal.vue'
import { getPartiesByDungeonId } from '@/api/party'

const props = defineProps({
  dungeonId: String
})

const parties = ref([])
const showModal = ref(false)

const fetchParties = async () => {
  try {
    const data = await getPartiesByDungeonId(props.dungeonId)
    parties.value = data.map(p => ({
      id: p.partyId,
      title: p.title,
      desc: p.description,
      currentMembers: 0,
      maxMembers: p.maxMember
    }))
  } catch (err) {
    console.error('파티 불러오기 실패', err)
  }
}

onMounted(fetchParties)
</script>

<style scoped>
.party-controls {
  display: flex;
  justify-content: space-between;
  margin: 1rem 0 0.5rem;
  font-size: 1rem;
}

.party-controls button {
  background-color: #6cd395;
  border: 2px solid #2d2d2d;
  border-radius: 8px;
  padding: 0.4rem 0.8rem;
  font-size: 0.8rem;
  cursor: pointer;
}

.party-list {
  max-height: 500px; /* 스크롤 높이 */
  overflow-y: auto;
  padding-right: 0.5rem;
}
</style>