<template>
  <div>
    <main class="detail-container">
      <div v-if="loading" class="loading">로딩 중...</div>
      <div v-else>
        <!-- ✅ dungeon이 null이면 렌더링 안되도록 -->
        <DungeonInfo v-if="dungeon" :dungeon="dungeon" :attractions="attractions" />
        <PartyList :dungeonId="dungeonId" />
      </div>
    </main>
  </div>
</template>


<script setup>
import DungeonInfo from '@/components/DungeonInfo.vue'
import PartyList from '@/components/DungeonParty.vue'

import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import {
  fetchDungeonDetail,
  fetchDungeonAttractions
} from '@/api/dungeon'

const route = useRoute()
const dungeonId = Number(route.params.dungeonId)

const dungeon = ref(null)
const attractions = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    dungeon.value     = await fetchDungeonDetail(dungeonId)
    attractions.value = await fetchDungeonAttractions(dungeonId)
  } catch (error) {
    console.error('던전 상세 로드 실패', error)
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.detail-container {
  max-width: 800px;
  margin: 1rem auto;
  padding: 0 1rem;
}
.loading {
  text-align: center;
  padding: 2rem;
  font-size: 1.1rem;
  color: #555;
}
</style>
