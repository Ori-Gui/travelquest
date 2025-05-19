<template>
  <header>
    <div class="logo">Travel Quest</div>
    <button class="menu-button">☰</button>
  </header>
  <br>
</template>

<script setup>
</script>

<style scoped>
header {
  position: sticky;
  top: 0;
  background-color: #b8f5c3;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 2px solid #6cd395;
}

.logo {
  font-size: 1.3rem;
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
