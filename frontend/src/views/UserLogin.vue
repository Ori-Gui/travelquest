<template>
  <div class="login-container">
    <AppHeader />
    <main class="flex-grow flex items-center justify-center w-full">
      <LoginForm />
    </main>
    <AppFooter />
  </div>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
import LoginForm from '@/components/LoginForm.vue'
</script>

<style scoped>
.bg-mint {
  background-color: #e6fff2;
}
.login-container {
  min-height: 100vh;
}
</style>
