<template>
  <div class="travel-quest-container">
    <AppHeader />
    <main class="main-wrapper">
      <DungeonInfo />
      <nav class="tabs">
        <button :class="{ active: activeTab === 'info' }" @click="activeTab = 'info'">정보</button>
        <button :class="{ active: activeTab === 'quest' }" @click="activeTab = 'quest'">퀘스트</button>
        <button :class="{ active: activeTab === 'party' }" @click="activeTab = 'party'">파티</button>
        <button :class="{ active: activeTab === 'chat' }" @click="activeTab = 'chat'">채팅</button>
      </nav>
      <ChatSection v-if="activeTab === 'chat'" />
      <PartySection :partyId="partyId" v-if="activeTab === 'party'" />
    </main>
    <AppFooter />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import AppHeader from '@/components/AppHeader.vue';
import AppFooter from '@/components/AppFooter.vue';
import DungeonInfo from '@/components/InfoSection.vue';
import ChatSection from '@/components/ChatSection.vue';
import PartySection from '@/components/PartySection.vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const partyId = Number(route.params.id);

const activeTab = ref('chat');
</script>

<style scoped>
.travel-quest-container {
  background-color: #e6fff2;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-wrapper {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: 1rem;
  justify-content: flex-start;
}

.tabs {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.tabs button {
  padding: 0.4rem 0.8rem;
  border-radius: 8px;
  border: 1px solid #444;
  background: white;
  cursor: pointer;
}

.tabs .active {
  background-color: #b3f7c3;
  font-weight: bold;
}
</style>