<template>
  <form class="register-form" @submit.prevent="onSubmit">
    <h2 class="form-title">회원가입</h2>

    <!-- 프로필 사진 업로드 -->
    <div class="form-group avatar-group">
      <label for="avatar">프로필 사진</label>
      <div v-if="previewUrl" class="avatar-preview">
        <img :src="previewUrl" alt="Avatar Preview" />
      </div>
      <input
        id="avatar"
        type="file"
        accept="image/*"
        class="file-input"
        @change="onFileChange"
      />
      <label for="avatar" class="file-btn">사진 선택</label>
    </div>

    <input
      type="email"
      v-model="form.email"
      placeholder="이메일"
      required
    />
    <input
      type="text"
      v-model="form.nickname"
      placeholder="이름"
      required
    />
    <input
      type="date"
      v-model="form.birthdate"
      placeholder="생년월일"
      required
    />

    <button type="submit" class="submit-button">직업 검사</button>
  </form>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { registerUser } from '@/api/user'
import { uploadImage } from '@/api/image'

const router = useRouter()

const form = ref({
  email: '',
  nickname: '',
  birthdate: '',
  avatarFile: null
})
const previewUrl = ref('')

function onFileChange(event) {
  const file = event.target.files[0]
  if (!file) return
  form.value.avatarFile = file
  previewUrl.value = URL.createObjectURL(file)
}

async function onSubmit() {
  try {
    // 1) 이미지 업로드
    let avatarUrl = ''
    if (form.value.avatarFile) {
      const imageForm = new FormData()
      imageForm.append('file', form.value.avatarFile)
      const { data: uploadRes } = await uploadImage(imageForm)
      avatarUrl = uploadRes.url
      console.log(uploadRes);
      
    }

    // 2) 회원가입 API 호출
    const payload = {
      email: form.value.email,
      userName: form.value.nickname,
      birthday: form.value.birthdate,
      profileImageUrl: avatarUrl
    }
    console.log(payload);
    
    await registerUser(payload)

    // 3) 다음 단계로 이동
    router.push('/mbti')
  } catch (err) {
    console.error('회원가입 실패', err)
    alert('회원가입에 실패했습니다. 다시 시도해주세요.')
  }
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

.register-form input[type="email"],
.register-form input[type="text"],
.register-form input[type="date"] {
  width: 100%;
  height: 40px;
  padding: 0.5rem;
  border: 2px solid #ccc;
  border-radius: 8px;
  font-size: 0.9rem;
}

/* 아바타 업로드 스타일 */
.avatar-group {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  position: relative;
}
.avatar-preview img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #6cd395;
}
.file-input {
  position: absolute;
  width: 0.1px;
  height: 0.1px;
  opacity: 0;
  overflow: hidden;
  z-index: -1;
}
.file-btn {
  display: inline-block;
  padding: 0.4rem 0.8rem;
  border: 1px solid #6cd395;
  border-radius: 6px;
  background: white;
  color: #6cd395;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.2s ease;
}
.file-btn:hover {
  background: #f0fef8;
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