<template>
  <div>
    <h1>파티룸</h1>
    <p>파티 ID: {{ partyId }}</p>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'

const route = useRoute()
const partyId = route.params.id
</script>
