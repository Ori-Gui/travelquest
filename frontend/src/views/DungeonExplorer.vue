<template>
  <div>
    <Header />

    <main class="main">
      <div class="filters">
        <label>
          탐험기간
          <input type="date" v-model="startDate" /> ~
          <input type="date" v-model="endDate" />
        </label>

        <!-- 시도 선택 -->
        <select v-model="selectedSido">
          <option value="">시도 선택</option>
          <option
            v-for="s in sidos"
            :key="s.sidoCode"
            :value="s.sidoCode"
          >
            {{ s.sidoName }}
          </option>
        </select>

        <!-- 구군 선택 (시도 선택 후에 값이 채워짐) -->
        <select v-model="selectedGugun" :disabled="!guguns.length">
          <option value="">구군 선택</option>
          <option
            v-for="g in guguns"
            :key="g.gugunCode"
            :value="g.gugunCode"
          >
            {{ g.gugunName }}
          </option>
        </select>

        <!-- 콘텐츠 타입 선택 -->
        <select v-model="selectedContentType">
          <option value="">테마 선택</option>
          <option
            v-for="c in contentTypes"
            :key="c.contentTypeId"
            :value="c.contentTypeId"
          >
            {{ c.contentTypeName }}
          </option>
        </select>

        <!-- 키워드 입력 -->
        <input
          type="text"
          v-model="keyword"
          placeholder="키워드 입력"
          class="keyword-input"
        />

        <button class="menu-button" @click="searchDungeon">
          🔍 던전탐색
        </button>
      </div>

      <MapView :markers="dungeonMarkers" />
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from '@/lib/axios'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import MapView from '../components/MapView.vue'

// 날짜, 키워드
const startDate = ref('')
const endDate = ref('')
const keyword = ref('')

// API로부터 받아올 목록들
const sidos = ref([])
const guguns = ref([])
const contentTypes = ref([])

// 사용자가 선택한 값
const selectedSido = ref('')
const selectedGugun = ref('')
const selectedContentType = ref('')

// 던전 검색 후 대표 여행지 마커로 찍을 배열
const dungeonList = ref([])
const dungeonMarkers = ref([])

// 컴포넌트가 마운트되면 시도 목록과 콘텐츠 타입 불러오기
onMounted(async () => {
  try {
    const [sidoRes, ctRes] = await Promise.all([
      axios.get('/api/v1/attractions/sidos'),
      axios.get('/api/v1/attractions/content-types')
    ])
    sidos.value = sidoRes.data
    contentTypes.value = ctRes.data
  } catch (err) {
    console.error('초기 데이터 로드 실패', err)
  }
})

// selectedSido가 바뀌면 구군 API 호출
watch(selectedSido, async (newCode) => {
  selectedGugun.value = ''
  if (!newCode) {
    guguns.value = []
    return
  }
  try {
    const res = await axios.get('/api/v1/attractions/guguns', {
      params: { sidoCode: newCode }
    })
    guguns.value = res.data
  } catch (err) {
    console.error('구군 로드 실패', err)
  }
})

// 검색 버튼 클릭
const searchDungeon = async () => {
  const condition = {
    startDate: startDate.value,
    endDate: endDate.value,
    sidoCode: selectedSido.value,
    gugunCode: selectedGugun.value,
    contentTypeId: selectedContentType.value,
    keyword: keyword.value
  }
  console.log('검색 조건:', condition)
  try {
    // 1) 던전 검색
    const { data: dungeons } = await axios.post(
      '/api/v1/dungeons/search',
      condition
    )
    dungeonList.value = dungeons
    console.log('검색 결과:', dungeonList)

    // 2) 던전별 대표 여행지 호출
    const firstCalls = dungeonList.value.map(d =>
      axios
        .get(`/api/v1/dungeons/${d.id}/attractions/first`)
        .then(r => r.data)
        .catch(() => null)
    )
    const attractions = await Promise.all(firstCalls)

    // 3) dungeonList 와 attractions 를 합쳐 마커 데이터 생성
    dungeonMarkers.value = dungeons.map((d, i) => ({
      id: d.id,
      title: d.title,
      first: attractions[i]?.title,
      lat: attractions[i]?.latitude,
      lng: attractions[i]?.longitude,
      startDate: d.startDate,
      endDate: d.endDate,
      difficulty: d.difficulty,
      maxPartySize: d.maxPartySize,
      status: d.status
    }))
  } catch (err) {
    console.error('검색 실패', err)
  }
}
</script>

<style scoped>
.main { padding: 1rem; }
.filters {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
  min-width: 140px;
  flex-shrink: 0;
}
.menu-button {
  background-color: #6cd395;
  border: 2px solid #2d2d2d;
  padding: 0.4rem 0.8rem;
  border-radius: 8px;
  font-size: 0.6rem;
  color: #fff;
  cursor: pointer;
}
</style>
