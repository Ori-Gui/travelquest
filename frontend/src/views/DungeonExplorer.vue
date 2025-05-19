<template>
  <div>
    <Header />

    <main>
      <div class="filters">
        <label>
          탐험기간
          <input type="date" v-model="startDate"> ~
          <input type="date" v-model="endDate">
        </label>

        <select v-model="region">
          <option>탐험지역</option>
          <option>경기</option>
          <option>서울</option>
          <option>대전</option>
        </select>

        <select v-model="theme">
          <option>주요활동 테마</option>
          <option>자연</option>
          <option>액티비티</option>
          <option>먹방</option>
        </select>

        <button class="menu-button" @click="searchDungeon">🔍 던전탐색</button>
      </div>

      <MapView />
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Header from '../components/Header.vue';
import Footer from '../components/Footer.vue';
import MapView from '../components/MapView.vue';

const startDate = ref('');
const endDate = ref('');
const region = ref('');
const theme = ref('');

const searchDungeon = () => {
  console.log(`탐험기간: ${startDate.value} ~ ${endDate.value}, 지역: ${region.value}, 테마: ${theme.value}`);
};
</script>

<style scoped>
@import "@/assets/styles/fonts.css";
.main {
  padding: 1rem;
}

.filters {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
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
