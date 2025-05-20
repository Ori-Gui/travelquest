<template>
  <div class="register-container">
    <Header />

    <main class="form-wrapper">
      <RegisterForm @submitted="handleSubmit" />
    </main>

    <Footer />
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
import RegisterForm from '@/components/RegisterForm.vue'
import { useRouter } from 'vue-router'
import { registerUser } from '@/api/user'

const router = useRouter()

const handleSubmit = async (formData) => {
  try {
    await registerUser(formData)
    router.push('/mbti') // MBTI 검사 페이지로 이동
  } catch (error) {
    console.error('회원가입 실패:', error)
    alert('회원가입에 실패했습니다.')
  }
}
</script>


<style scoped>
.register-container {
  background-color: #d9ffeb;
  min-height: 70vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.form-wrapper {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
