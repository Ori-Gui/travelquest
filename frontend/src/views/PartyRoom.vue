<template>
  <div class="party-room">
    <Header />

    <main class="content">
      <section class="party-header">
        <div class="meta">
          <span>📅 {{ startDate }} ~ {{ endDate }}</span>
          <span>No.{{ partyId }}</span>
          <span>{{ location }}</span>
        </div>
        <div class="theme">테마: {{ themeList.join(' · ') }}</div>
      </section>

      <section class="button-group">
        <button class="btn-orange">숙소 예약하기</button>
        <button class="btn-orange">교통편 예약하기</button>
        <button class="btn-orange">여행 계획 짜기</button>
      </section>

      <section class="action-buttons">
        <button class="btn-pink">💬 채팅하기</button>
        <button class="btn-red">👋 파티 나가기</button>
      </section>

      <section class="exploration-status" v-if="status === 'ready'">
        <h2 class="red">준비 완료!</h2>
        <p>탐험을 시작하면 모두에게 길이 열립니다.</p>
        <button class="btn-start">탐험 시작하기</button>
        <p class="note">※ 탐험이 시작되면 파티원들의 위치가 공유됩니다.</p>
      </section>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'

const route = useRoute()
const partyId = route.params.id

// 임시 데이터
const startDate = '5/25'
const endDate = '5/27'
const location = '강화섬 비밀의 숲'
const themeList = ['자연', '사진', '식도락']
const status = 'ready'
</script>

<style scoped>
.party-room {
  background-color: #ddf5e1;
  font-family: 'DungGeunMo', sans-serif;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.content {
  flex: 1;
  padding: 1rem;
}

.party-header {
  text-align: center;
  margin-bottom: 1rem;
}

.meta {
  font-size: 0.9rem;
  margin-bottom: 0.3rem;
}

.theme {
  font-size: 0.85rem;
  color: #555;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 0.6rem;
  margin: 1rem 0;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 0.6rem;
  margin-bottom: 2rem;
}

.btn-orange {
  background-color: #ffb733;
  border: none;
  border-radius: 8px;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  cursor: pointer;
}

.btn-pink {
  background-color: #f8a8a8;
  border: none;
  border-radius: 8px;
  padding: 0.5rem 1rem;
  font-size: 0.85rem;
  cursor: pointer;
}

.btn-red {
  background-color: #f75c5c;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0.5rem 1rem;
  font-size: 0.85rem;
  cursor: pointer;
}

.exploration-status {
  text-align: center;
  color: #d12b2b;
}

.red {
  color: #d12b2b;
  font-weight: bold;
}

.btn-start {
  background-color: white;
  border: 2px solid #d12b2b;
  border-radius: 12px;
  padding: 0.5rem 1.2rem;
  font-weight: bold;
  color: #d12b2b;
  margin: 1rem auto;
  font-size: 1rem;
}

.note {
  font-size: 0.7rem;
  color: #555;
}
</style>