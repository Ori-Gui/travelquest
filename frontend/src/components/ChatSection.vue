<template>
  <section class="chat-box">
    <div class="chat-title">💬 파티 채팅</div>
    <div class="chat-messages">
      <div
        class="chat-message"
        v-for="(msg, idx) in messages"
        :key="msg.id"
        :class="getMessageClass(msg)"
        :ref="idx === messages.length - 1 ? setLastMessageRef : null"
      >
        <div class="nickname">{{ getJobEmoji(msg.job) }} {{ msg.name }}</div>
        <div class="message">{{ msg.message }}</div>
      </div>
    </div>
    <div class="chat-input">
      <input
        v-model="input"
        type="text"
        placeholder="메시지를 입력하세요."
        @keyup.enter="sendMessage"
      />
      <button class="send-button" @click="sendMessage">전송</button>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, watch, onBeforeUnmount, nextTick } from 'vue'
import { useUserStore } from '@/stores/userStore'
import { connect, disconnect, sendMessage as sendToServer } from '@/service/chatService'

const input = ref('')
const messages = ref([])
const lastMessage = ref(null)
const partyId = 1 // TODO: props or route에서 받아오도록 변경 가능

const userStore = useUserStore()
const currentUserId = computed(() => userStore.user?.id)
const currentUserJob = computed(() => userStore.user?.jobClassCode)
const currentUserName = computed(() => userStore.user?.userName || '익명')

const jobEmojiMap = {
  WARRIOR: '🛡️',
  MAGE: '🪄',
  HEALER: '💉',
  RANGER: '🌾',
  BARD: '🎶',
  TRICKSTER: '💋',
  WIZARD: '🔮',
  THIEF: '🗡️',
  MECHANIC: '🔧',
  ROGUE: '🧙'
}
const getJobEmoji = (jobCode) => jobEmojiMap[jobCode] || '🌟'

// ref setter (마지막 메시지에만 바인딩됨)
function setLastMessageRef(el) {
  lastMessage.value = el
}

function handleMessage(message) {
  console.log('[🔥 handleMessage 호출됨]', message) // ← 이거 먼저 확인

  messages.value.push(message)

  nextTick(() => {
    if (lastMessage.value) {
      lastMessage.value.scrollIntoView({ behavior: 'smooth' })
    }
  })
}


function tryConnect() {
  connect(partyId, handleMessage)
}

watch(
  () => userStore.accessToken,
  (token) => {
    if (token) tryConnect()
  },
  { immediate: true }
)

onBeforeUnmount(() => {
  disconnect()
})

function sendMessage() {
  if (!input.value.trim() || !currentUserId.value) return

  const message = {
    id: Date.now(),
    userId: currentUserId.value,
    name: currentUserName.value,
    job: currentUserJob.value,
    message: input.value.trim(),
    sentAt: new Date().toISOString()
  }

  sendToServer(partyId, message)
  input.value = ''
}

function getMessageClass(msg) {
  return {
    me: Number(msg.userId) === Number(currentUserId.value)
  }
}

</script>

<style scoped>
.chat-box {
  background: #fff;
  border: 2px solid #2d2d2d;
  border-radius: 16px;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  height: 400px;
  max-height: 80vh;
}

.chat-title {
  font-weight: bold;
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
  color: #2d2d2d;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding-right: 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
}

.chat-message {
  align-self: flex-start;
  max-width: 100%;
  word-break: break-word;
}

.chat-message.me {
  align-self: flex-end;
  text-align: right;
}

.nickname {
  font-weight: bold;
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 0.2rem;
}

.message {
  background-color: #f1f1f1;
  padding: 0.5rem 0.75rem;
  border-radius: 12px;
  display: inline-block;
  font-size: 0.9rem;
  color: #333;
  max-width: 100%;
  white-space: pre-wrap;
}

.chat-message.me .message {
  background-color: #d1f5d3;
  color: #2d2d2d;
}

.chat-input {
  display: flex;
  gap: 0.5rem;
}

.chat-input input {
  flex: 1;
  padding: 0.6rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 0.9rem;
}

.send-button {
  background-color: #6cd395;
  border: none;
  padding: 0.6rem 1rem;
  border-radius: 8px;
  font-weight: bold;
  font-size: 0.9rem;
  color: white;
  cursor: pointer;
  transition: background 0.2s;
}

.send-button:hover {
  background-color: #57ba7a;
}
</style>