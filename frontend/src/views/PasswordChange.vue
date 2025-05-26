<template>
  <div class="profile-change-container">
    <h2 class="title">비밀번호 변경</h2>
    <form @submit.prevent="onSubmit" class="profile-form">
      <!-- 현재 비밀번호 -->
      <div class="form-group">
        <label for="currentPassword">현재 비밀번호</label>
        <input
          id="currentPassword"
          v-model="currentPassword"
          type="password"
          required
        />
      </div>

      <!-- 새 비밀번호 -->
      <div class="form-group">
        <label for="newPassword">새 비밀번호</label>
        <input
          id="newPassword"
          v-model="newPassword"
          type="password"
          required
        />
      </div>

      <!-- 새 비밀번호 확인 -->
      <div class="form-group">
        <label for="confirmPassword">새 비밀번호 확인</label>
        <input
          id="confirmPassword"
          v-model="confirmPassword"
          type="password"
          required
        />
      </div>

      <!-- 에러 및 성공 메시지 -->
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>

      <!-- 버튼 그룹 -->
      <div class="form-actions">
        <button type="button" class="btn cancel" @click="onCancel">취소</button>
        <button type="submit" class="btn save">변경하기</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axiosInstance from '@/lib/axios'

const router = useRouter()

const currentPassword = ref('')
const newPassword = ref('')
const confirmPassword = ref('')
const errorMessage = ref('')
const successMessage = ref('')

const onSubmit = async () => {
  errorMessage.value = ''
  successMessage.value = ''

  if (newPassword.value !== confirmPassword.value) {
    errorMessage.value = '새 비밀번호와 확인이 일치하지 않습니다.'
    return
  }

  try {
    await axiosInstance.post('/api/v1/user/password', {
      currentPassword: currentPassword.value,
      newPassword: newPassword.value
    })
    successMessage.value = '비밀번호가 성공적으로 변경되었습니다.'
    currentPassword.value = ''
    newPassword.value = ''
    confirmPassword.value = ''
  } catch (err) {
    errorMessage.value = err.response?.data?.message || '비밀번호 변경에 실패했습니다.'
  }
}

const onCancel = () => {
  router.back()
}
</script>

<style scoped>
.profile-change-container {
  width: 90%;
  max-width: 420px;
  margin: 2rem auto;
  padding: 2rem;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.title {
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #2d5f2e;
}

.profile-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-group input {
  padding: 0.6rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.2s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #6cd395;
}

.error-message {
  color: #e53e3e;
  font-size: 0.875rem;
}

.success-message {
  color: #38a169;
  font-size: 0.875rem;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.btn {
  padding: 0.6rem 1.2rem;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.btn.save {
  background: #6cd395;
  color: #fff;
}

.btn.save:hover {
  background: #5bb382;
}

.btn.cancel {
  background: #f0f0f0;
  color: #555;
}

.btn.cancel:hover {
  background: #e0e0e0;
}
</style>