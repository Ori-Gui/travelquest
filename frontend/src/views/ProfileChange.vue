<template>
  <div class="profile-change-container">
    <h2 class="title">프로필 수정</h2>
    <form @submit.prevent="onSubmit" class="profile-form">
      <!-- 아바타 업로드 -->
      <div class="form-group avatar-group">
        <label>아바타</label>
        <!-- 미리보기: 파일 선택 위에 표시 -->
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
        <label for="avatar" class="file-btn">파일 선택</label>
      </div>

      <!-- 닉네임 -->
      <div class="form-group">
        <label for="userName">닉네임</label>
        <input
          id="userName"
          v-model="form.userName"
          type="text"
          required
        />
      </div>

      <!-- 이메일 -->
      <div class="form-group">
        <label for="email">이메일</label>
        <input
          id="email"
          v-model="form.email"
          type="email"
          required
        />
      </div>

      <!-- 생년월일 -->
      <div class="form-group">
        <label for="birthday">생년월일</label>
        <input
          id="birthday"
          v-model="form.birthday"
          type="date"
          required
        />
      </div>

      <!-- 버튼 그룹 -->
      <div class="form-actions">
        <button type="button" class="btn cancel" @click="onCancel">취소</button>
        <button type="submit" class="btn save">저장</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { getUserProfile, updateUserProfile } from '@/api/user'
import { uploadImage } from '@/api/image'  // 이미지 업로드 API
import { refreshToken } from '@/api/auth'

const router = useRouter()
const userStore = useUserStore()
const userId = userStore.user.id

// 폼 데이터
const form = reactive({
  userName: '',
  email: '',
  birthday: '',
  avatarFile: null,
  avatarUrl: ''  // 서버에 저장된 이미지 경로
})
const previewUrl = ref('')

// 초기 데이터 로드
onMounted(async () => {
  try {
    const { data } = await getUserProfile(userId)
    form.userName = data.userName
    form.email = data.email
    form.birthday = data.birthday ? data.birthday.slice(0, 10) : ''
    form.avatarUrl = data.profileImageUrl
    previewUrl.value = data.profileImageUrl
  } catch (err) {
    console.error('프로필 로드 실패', err)
  }
})

// 파일 변경 핸들러
function onFileChange(event) {
  const file = event.target.files[0]
  if (!file) return
  form.avatarFile = file
  previewUrl.value = URL.createObjectURL(file)
}

// 폼 제출
async function onSubmit() {
  try {
    let avatarPath = form.avatarUrl
    // 새로운 파일이 선택된 경우, 먼저 업로드
    if (form.avatarFile) {
      const imageForm = new FormData()
      imageForm.append('file', form.avatarFile)
      const { data: uploadRes } = await uploadImage(imageForm)
      avatarPath = uploadRes.url  // 서버에서 반환한 이미지 URL
    }

    // 프로필 업데이트 호출 (JSON payload)
    const payload = {
      userName: form.userName,
      email: form.email,
      birthday: form.birthday,
      profileImageUrl: avatarPath
    }
    await updateUserProfile(userId, payload)
    await refreshToken()
    router.push('/me').then(() => {
    window.location.reload();
  });
  } catch (err) {
    console.error('프로필 업데이트 실패', err)
    alert('프로필 수정에 실패했습니다. 다시 시도해주세요.')
  }
}

function onCancel() {
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

.form-group input[type="text"],
.form-group input[type="email"],
.form-group input[type="date"] {
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

.avatar-group {
  display: flex;
  flex-direction: column;
  align-items: center; /* 중앙 정렬 */
}

.avatar-preview {
  margin-bottom: 0.75rem;
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
  padding: 0.6rem 1.2rem;
  border: 1px solid #6cd395;
  border-radius: 6px;
  background: white;
  color: #6cd395;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s ease;
  text-align: center; /* 중앙 정렬 */
}

.file-btn:hover {
  background: #f0fef8;
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
