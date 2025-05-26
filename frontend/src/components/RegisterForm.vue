<template>
  <form class="register-form" @submit.prevent="onSubmit">
    <h2 class="form-title">회원가입</h2>

    <input type="text" v-model="form.email" placeholder="이메일" required />
    <input type="text" v-model="form.nickname" placeholder="이름" required />
    생년월일 <input type="date" v-model="form.birthdate" placeholder="생년월일" required />

    <button type="submit" class="submit-button">직업 검사</button>
  </form>
</template>

<script setup>
import { ref } from 'vue'
const emit = defineEmits(['submitted'])

const form = ref({
  email: '',
  nickname: '',
  birthdate: '',
})

const onSubmit = () => {
  emit('submitted', { ...form.value })
}
</script>

<style scoped>
.register-form {
  background-color: #ffffff;
  border: 3px solid #2d2d2d;
  box-shadow: 4px 4px 0 #2d2d2d;
  border-radius: 16px;
  padding: 2rem;
  width: 90%;
  max-width: 360px;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  align-items: center;
}

.form-title {
  color: #146c43;
  font-size: 1.1rem;
  margin-bottom: 1rem;
}

.register-form input {
  width: 100%;
  height: 40px;
  padding: 0.5rem;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 0.9rem;
}

.submit-button {
  margin-top: 1rem;
  width: 100%;
  height: 45px;
  background-color: #6cd395;
  color: white;
  font-weight: bold;
  border: 2px solid #2d2d2d;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit-button:hover {
  background-color: #58c386;
}
</style>
