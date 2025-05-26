<template>
  <div class="login-container">
    <main class="flex-grow flex items-center justify-center w-full">
      <LoginForm />
    </main>
  </div>
</template>

<script setup>
import LoginForm from '@/components/LoginForm.vue'
</script>

<style scoped>
.bg-mint {
  background-color: #e6fff2;
}
.login-container {
  padding-top: 3rem;
  min-height: 100vh;
}
</style>
