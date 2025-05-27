<template>
  <div class="result-container">
    <main class="result-box" v-if="result">
      <p class="welcome-text">
        <span class="highlight">{{ userName }}</span> 님,<br />
        <span class="highlight">Travel Quest</span>에 오신 것을 환영합니다!
      </p>

      <img :src="jobImagePath" alt="직업 이미지" class="job-image" />

      <p class="job-text">
        당신의 직업은 <strong>"{{ result.jobDisplayName }}"</strong>입니다!
      </p>
      <p class="job-desc">{{ result.mbtiDescription }}</p>

      <button class="start-button" @click="goToMap">
        🗺 여정으로 가기
      </button>
    </main>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from '@/lib/axios'
import { refreshToken } from '@/api/auth'    // 👈 방금 만든 함수

const router = useRouter()

const route = useRoute()
const userName = route.query.name || '여행자'
const mbtiCode = route.params.mbtiCode || 'ENTP'

const result = ref(null)

const jobImagePath = computed(() => {
  if (!result.value) return ''
  return `../../public/images/jobs/${result.value.jobCode.toLowerCase()}.png`
})

onMounted(async () => {
  try {
    const res = await axios.get(`/api/v1/user/mbti/${mbtiCode}`)
    result.value = res.data
  } catch (e) {
    console.error('MBTI 결과 불러오기 실패:', e)
  }
})

async function goToMap() {
  try {
    // 1) 토큰 리프레시 시도
    await refreshToken()
  } catch (e) {
    console.error('토큰 리프레시 실패:', e)
    // 필요하면 로그인 페이지로 리다이렉트
    return router.push('/login')
  }
  // 2) 리프레시 성공했으면 맵으로 이동
  router.push('/map').then(() => {
    window.location.reload();
  });
}

</script>

<style scoped>

.result-container {
  background-color: #e6fff2;
  min-height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.result-box {
  margin: 2rem auto;
  background-color: white;
  border: 3px solid #2d2d2d;
  box-shadow: 4px 4px 0 #2d2d2d;
  border-radius: 16px;
  padding: 2rem;
  width: 90%;
  max-width: 360px;
  text-align: center;
}

.welcome-text {
  font-size: 0.8rem;
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

.highlight {
  font-weight: bold;
  color: #146c43;
}

.job-image {
  width: 100px;
  height: auto;
  margin: 1rem auto;
}

.job-text {
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.job-desc {
  font-size: 0.8rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.start-button {
  display: inline-block;
  padding: 0.75rem 1.2rem;
  font-size: 0.9rem;
  font-weight: bold;
  color: white;
  background-color: #6cd395;
  border: 2px solid #2d2d2d;
  border-radius: 12px;
  text-decoration: none;
  transition: background-color 0.2s ease;
}

.start-button:hover {
  background-color: #58c386;
}
</style>
