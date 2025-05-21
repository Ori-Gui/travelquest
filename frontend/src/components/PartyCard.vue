<template>
  <div class="party-card">
    <div class="party-title">{{ title }}</div>
    <div class="party-desc">{{ desc }}</div>

    <div class="party-roles" v-if="roles && roles.length">
      <div class="role" v-for="role in roles" :key="role.job">
        {{ role.job }}: {{ role.current }}/{{ role.max }}
      </div>
    </div>

    <div class="party-meta">
      <span>👥 {{ currentMembers }}/{{ maxMembers }}</span>
      <button class="join-button" @click="joinParty">참가</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const { partyId, title, desc, currentMembers, maxMembers, roles } = defineProps({
  partyId: Number,
  title: String,
  desc: String,
  currentMembers: Number,
  maxMembers: Number,
  roles: {
    type: Array,
    default: () => []
  }
})

const joinParty = () => {
  router.push(`/party/${partyId}`)
}
</script>

<style scoped>

.party-card {
  background-color: #fff;
  border: 1px solid #aaa;
  border-radius: 10px;
  padding: 1rem;
  margin-bottom: 0.8rem;
  font-size: 1rem;
  overflow-x: auto;
}

.party-title {
  font-weight: bold;
  margin-bottom: 0.5rem;
  font-size: 1.2rem;
}

.party-desc {
  margin-bottom: 0.5rem;
}

.party-roles {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 0.6rem;
  font-size: 0.9rem;
  color: #444;
}

.role {
  background-color: #f2f2f2;
  padding: 0.2rem 0.6rem;
  border-radius: 6px;
  border: 1px solid #ddd;
}

.party-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.join-button {
  background-color: #f9b44d;
  border: 1px solid #2d2d2d;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  font-size: 0.8rem;
  cursor: pointer;
}
</style>