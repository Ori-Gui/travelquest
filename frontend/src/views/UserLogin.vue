<template>
  <div class="login-container">
    <Header />
    <main class="flex-grow flex items-center justify-center w-full">
      <LoginForm />
    </main>
    <Footer />
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
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
