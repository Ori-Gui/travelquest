<template>
  <div class="dungeon-layout">
    <main class="dungeon-main">
      <router-view />
    </main>
  </div>
</template>

<script setup>
</script>

<style scoped>
.dungeon-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #e6fff2;
}

.dungeon-main {
  flex: 1;
  max-width: 800px;
  margin: 1rem auto;
  padding: 0 1rem;
}
</style>
