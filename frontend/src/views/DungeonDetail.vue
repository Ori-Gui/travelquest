<template>
  <Header />
  <div class="container">

    <DungeonInfo />

    <div class="party-controls">
      <span>파티 목록</span>
      <button @click="createParty">+ 파티 만들기</button>
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
  </div>
</template>

<script setup>
import Header from '../components/Header.vue';
import DungeonInfo from '../components/DungeonInfo.vue';
import PartyCard from '../components/PartyCard.vue';

const parties = [
  { id: 1, title: "부산 한바퀴", desc: "전사 2/2, 힐러 0/3", currentMembers: 2, maxMembers: 5 },
  { id: 2, title: "학과동기 ㅋㅋㅋ", desc: "전사 1/1, 마법사 0/2, 힐러 1/2", currentMembers: 2, maxMembers: 5 },
  { id: 3, title: "같이 걷자", desc: "직업 자유모집", currentMembers: 1, maxMembers: 2 },
  { id: 4, title: "힐링파티", desc: "직업 자유모집", currentMembers: 2, maxMembers: 3 },
];

const createParty = () => {
  console.log("파티 만들기 클릭됨");
};
</script>

<style scoped>
@import "@/assets/styles/fonts.css";

.container {
  padding: 1rem;
  overflow-x: auto;
}

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
</style>
