/* components/QuestVerificationModal.vue */
<template>
  <div v-if="visible" class="modal-overlay">
    <div class="modal">
      <h3>퀘스트 인증 사진 업로드</h3>
      <input type="file" accept="image/*" @change="onFileChange" />
      <div class="modal-actions">
        <button @click="submit">제출</button>
        <button @click="$emit('close')">취소</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref } from 'vue'

const props = defineProps({
  visible: { type: Boolean, required: true },
  questId:  { type: [Number, String], required: true },
  partyId:  { type: [Number, String], required: true }
})
const emit = defineEmits(['close', 'submitted'])
const selectedFile = ref(null)

function onFileChange(event) {
  const file = event.target.files[0]
  if (file) selectedFile.value = file
}

async function submit() {
  if (!selectedFile.value) {
    alert('사진을 선택해주세요.')
    return
  }
  const formData = new FormData()
  formData.append('questId', props.questId)
  formData.append('partyId', props.partyId)
  formData.append('photo', selectedFile.value)

  try {
    await fetch(`/api/v1/quest-verifications`, {
      method: 'POST',
      body: formData
    })
    emit('submitted')
    emit('close')
  } catch (e) {
    console.error('인증 제출 실패', e)
    alert('인증 제출에 실패했습니다.')
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.modal {
  background: #fff;
  padding: 1.5rem;
  border-radius: 8px;
  width: 90%; max-width: 400px;
  text-align: center;
}
.modal-actions {
  margin-top: 1rem;
  display: flex;
  justify-content: space-around;
}
.modal-actions button {
  padding: 0.6rem 1.2rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
.modal-actions button:first-child {
  background: #079929;
  color: #fff;
}
.modal-actions button:last-child {
  background: #ccc;
}
</style>
