<template>
  <section class="admin-user-list">
    <div class="header-controls">
      <h2 class="heading">회원 관리</h2>
    </div>

    <div class="controls">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="회원 이름으로 검색"
        class="search-input"
      />
      <button class="btn-search" @click="loadUsers">🔍 검색</button>
    </div>

    <table class="user-table">
      <thead>
        <tr>
          <th>회원 ID</th>
          <th>이름</th>
          <th>이메일</th>
          <th>MBTI</th>
          <th>직무 코드</th>
          <th>가입 상태</th>
          <th>가입일</th>
          <th>액션</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="user in  filteredUsers.filter(u => u.role !== 'ADMIN')"
          :key="user.id"
        >
          <td>{{ user.id }}</td>
          <td>{{ user.userName }}</td>
          <td>{{ user.email }}</td>
          <td>{{ user.mbti }}</td>
          <td>{{ user.jobClassCode }}</td>
          <td>{{ user.registStatus }}</td>
          <td>{{ formatDate(user.createdAt) }}</td>
          <td>
            <button
              class="btn-delete"
              @click="confirmDelete(user.userId, user.userName)"
            >
              삭제
            </button>
          </td>
        </tr>
        <tr v-if="!filteredUsers.length">
          <td colspan="8" class="no-data">검색된 회원이 없습니다.</td>
        </tr>
      </tbody>
    </table>
    <br>
    <button class="btn-back" @click="goBack">← 뒤로가기</button>
  </section>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAllUsers, deleteUser } from '@/api/user'

const users = ref([])
const searchQuery = ref('')
const router = useRouter()

// 전체 유저 불러오기
async function loadUsers() {
  try {
    users.value = await getAllUsers()
  } catch (e) {
    console.error('유저 목록 로드 실패', e)
  }
}

// 이름 기준 필터링
const filteredUsers = computed(() => {
  const q = searchQuery.value.trim().toLowerCase()
  if (!q) return users.value
  return users.value
  .filter(u => u.userName.toLowerCase().includes(q)
  )
})

// 날짜 포맷 헬퍼
function formatDate(isoString) {
  return new Date(isoString).toLocaleDateString('ko-KR', {
    year: 'numeric', month: '2-digit', day: '2-digit'
  })
}

// 삭제 확인 후 API 호출
async function confirmDelete(id, name) {
  if (!confirm(`${name} (ID: ${id}) 님을 정말 삭제하시겠습니까?`)) return
  try {
    await deleteUser(id)
    // 목록에서 바로 제거
    users.value = users.value.filter(u => u.userId !== id)
    alert('삭제되었습니다.')
  } catch (e) {
    console.error('삭제 실패', e)
    alert('삭제에 실패했습니다.')
  }
}

onMounted(loadUsers)

function goBack() {
  router.back()
}
</script>

<style scoped>
.admin-user-list {
  padding: 1rem;
}
.header-controls {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}
.btn-back {
  background: transparent;
  border: none;
  color: #555;
  font-size: 0.9rem;
  cursor: pointer;
  margin-right: 0.5rem;
}
.btn-back:hover {
  color: #000;
}
.heading {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
.controls {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}
.search-input {
  flex: 1;
  padding: 0.5rem;
  font-size: 0.95rem;
  border: 1px solid #ccc;
  border-radius: 6px;
}
.btn-search {
  padding: 0 1rem;
  background: #6cd395;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
.btn-search:hover {
  background: #5bbb84;
}

.user-table {
  width: 100%;
  border-collapse: collapse;
}
.user-table th,
.user-table td {
  border: 1px solid #ddd;
  padding: 0.6rem;
  text-align: center;
}
.user-table th {
  background: #f7f7f7;
}
.no-data {
  text-align: center;
  color: #777;
  padding: 1rem 0;
}

.btn-delete {
  background: #e74c3c;
  color: #fff;
  border: none;
  padding: 0.3rem 0.6rem;
  border-radius: 6px;
  cursor: pointer;
}
.btn-delete:hover {
  background: #c0392b;
}
</style>
