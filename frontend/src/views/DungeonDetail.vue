<template>
  <div>
    <AppHeader />
    <main class="detail-container">
      <div v-if="loading" class="loading">로딩 중...</div>
      <div v-else>
        <!-- ✅ dungeon이 null이면 렌더링 안되도록 -->
        <DungeonInfo v-if="dungeon" :dungeon="dungeon" :attractions="attractions" />
        <PartyList :dungeonId="dungeonId" />
      </div>
    </main>
    <AppFooter />
  </div>
</template>


<script setup>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import DungeonInfo from '@/components/DungeonInfo.vue'
import PartyList from '@/components/DungeonParty.vue'

import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from '@/lib/axios'

const route = useRoute()
const dungeonId = Number(route.params.id)

const dungeon = ref(null)
const attractions = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    // 던전 기본 정보 조회
    const { data: dungeonData } = await axios.get(
      `/api/v1/dungeons/${dungeonId}`
    )
    dungeon.value = dungeonData

    // 연결된 여행지 목록 조회
    const { data: attrList } = await axios.get(
      `/api/v1/dungeons/${dungeonId}/attractions`
    )
    attractions.value = attrList
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
