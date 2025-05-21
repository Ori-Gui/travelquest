<template>
  <section class="chat-box">
    <div class="chat-title">💬 파티 채팅</div>
    <div class="chat-messages">
      <div class="chat-message" v-for="msg in messages" :key="msg.id">
        <div class="nickname">{{ msg.name }}</div>
        <div class="message">{{ msg.content }}</div>
      </div>
    </div>
    <div class="chat-input">
      <input v-model="input" type="text" placeholder="메시지를 입력하세요." />
      <button class="send-button" @click="sendMessage">전송</button>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'

const input = ref('')
const messages = ref([
  { id: 1, name: '용사군', content: '안녕하세요!!' },
  { id: 2, name: '마법사짱', content: '일정 조율할게요~' }
])

function sendMessage() {
  if (input.value.trim()) {
    messages.value.push({
      id: Date.now(),
      name: '나',
      content: input.value
    })
    input.value = ''
  }
}
</script>

<style scoped>
.chat-box {
  background: white;
  border: 2px solid black;
  padding: 0.5rem;
}

.chat-title {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.chat-message {
  margin-bottom: 0.3rem;
}

.nickname {
  font-weight: bold;
}

.message {
  background-color: #f5f5f5;
  padding: 0.4rem;
  border-radius: 8px;
}

.chat-input {
  display: flex;
  margin-top: 1rem;
}

.chat-input input {
  flex: 1;
  padding: 0.4rem;
}

.send-button {
  background-color: green;
  color: white;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  margin-left: 0.4rem;
}
</style>