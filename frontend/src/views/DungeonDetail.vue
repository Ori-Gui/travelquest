<template>
  <Header />
  <div class="container">
    <DungeonInfo />
    <PartySection />
  </div>
</template>

<script setup>
import Header from '../components/Header.vue';
import DungeonInfo from '../components/DungeonInfo.vue';
import PartySection from '../components/DungeonParty.vue';
</script>

<style scoped>
@import "@/assets/styles/fonts.css";

.container {
  padding: 1rem;
  overflow-x: auto;
}
</style>
