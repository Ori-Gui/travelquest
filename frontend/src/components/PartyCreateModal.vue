<template>
  <div class="modal-overlay" @click.self="close">
    <div class="modal">
      <h3>파티 만들기</h3>

      <input v-model="title" placeholder="파티 제목" />
      <textarea v-model="description" placeholder="설명" />
      <input type="number" v-model.number="maxMember" placeholder="최대 인원 수" min="1" />

      <div class="role-section">
        <h4>직업별 인원 설정</h4>
        <div
          class="role-row"
          v-for="(role, index) in partyRoleRequireRequests"
          :key="index"
        >
          <select v-model="role.jobCode">
            <option value="WARRIOR">🛡️ 전사</option>
            <option value="MAGE">🧙 마법사</option>
            <option value="ROGUE">🧝 도적</option>
            <option value="HEALER">🧚 힐러</option>
            <option value="BARD">🎭 바드</option>
            <option value="RANGER">🏹 레인저</option>
            <option value="MECHANIC">🛠 메카닉</option>
            <option value="TRICKSTER">🦊 트릭스터</option>
          </select>

          <input
            type="number"
            v-model.number="role.maxCount"
            min="1"
            class="count-input"
          />
          <button class="remove-btn" @click="removeRole(index)">❌</button>
        </div>

        <button class="add-role-btn" @click="addRole">+ 역할 추가</button>
      </div>

      <button class="submit-btn" @click="submit">생성</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { createParty } from '@/api/party'

const userStore = useUserStore()

const props = defineProps({
  dungeonId: String
})
const emit = defineEmits(['created', 'close'])

const title = ref('')
const description = ref('')
const maxMember = ref(5)

const partyRoleRequireRequests = ref([
  { jobCode: 'WARRIOR', maxCount: 1 },
  { jobCode: 'HEALER', maxCount: 1 },
])

const submit = async () => {
  const totalAssigned = partyRoleRequireRequests.value.reduce((sum, role) => sum + role.maxCount, 0);
  const jobCodeSet = new Set(partyRoleRequireRequests.value.map(role => role.jobCode));

  if (jobCodeSet.size !== partyRoleRequireRequests.value.length) {
    alert('같은 직업군을 중복해서 설정할 수 없습니다.');
    return;
  }

  if (totalAssigned !== maxMember.value) {
    alert(`직업군 인원의 총합 (${totalAssigned})이 최대 인원수 (${maxMember.value})와 같아야 합니다.`);
    return;
  }

  try {
    await createParty(props.dungeonId, {
      title: title.value,
      description: description.value,
      maxMember: maxMember.value,
      partyRoleRequireRequests: partyRoleRequireRequests.value
    });
    emit('created');
    emit('close');
  } catch (e) {
    console.error('파티 생성 실패', e);
  }
};

const addRole = () => {
  partyRoleRequireRequests.value.push({ jobCode: 'WARRIOR', maxCount: 1 })
}

const removeRole = (index) => {
  partyRoleRequireRequests.value.splice(index, 1)
}

const close = () => emit('close')
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal {
  background-color: #ffffff;
  border: 2px solid #2d2d2d;
  border-radius: 16px;
  padding: 2rem;
  width: 340px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  font-family: 'Pretendard', sans-serif;
}

.modal h3 {
  font-size: 1.3rem;
  margin-bottom: 1rem;
  font-weight: bold;
  color: #2d2d2d;
  text-align: center;
}

.modal input,
.modal textarea,
.modal select {
  width: 100%;
  padding: 0.6rem 0.8rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 0.9rem;
  font-family: inherit;
  resize: none;
}

.role-section h4 {
  margin-bottom: 0.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: #2d2d2d;
}

.role-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.count-input {
  width: 70px;
}

.remove-btn {
  background: none;
  border: none;
  font-size: 1.1rem;
  cursor: pointer;
}

.add-role-btn {
  background-color: #e0e0e0;
  border: 1px dashed #2d2d2d;
  border-radius: 8px;
  padding: 0.4rem;
  font-size: 0.8rem;
  cursor: pointer;
  margin-bottom: 1rem;
  width: 100%;
}

.submit-btn {
  width: 100%;
  background-color: #6cd395;
  border: 2px solid #2d2d2d;
  border-radius: 10px;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  font-weight: bold;
  color: #fff;
  cursor: pointer;
  transition: all 0.2s ease;
}

.submit-btn:hover {
  background-color: #57ba7a;
}
</style>
