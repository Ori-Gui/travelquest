<template>
  <section class="chat-box">
    <div class="chat-title">💬 파티 채팅</div>
    <div class="chat-messages" ref="messagesContainer" @scroll.passive="onScrollTop">
      <template v-for="(msg, idx) in messages" :key="msg.id">

        <div
          v-if="msg.system"
          class="system-message"
        >
          {{ msg.content }}
        </div>


        <div
          v-else
          class="chat-message"
          :class="{ me: Number(msg.userId) === currentUserId }"
          :ref="idx === messages.length - 1 ? setLastMessageRef : null"
        >
          <div class="nickname">{{ getJobEmoji(msg.job) }} {{ msg.name }}</div>
          <div class="message">{{ msg.message }}</div>
        </div>
      </template>
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
import { ref, computed, watch, onBeforeUnmount, onMounted, nextTick } from 'vue'
import { getChatMessages } from '@/api/chat'
import { useUserStore } from '@/stores/userStore'
import { connect, disconnect, sendMessage as sendToServer } from '@/service/chatService'

const input = ref('')
const messages = ref([])
const lastMessage = ref(null)
const partyId = 1         // TODO: props 또는 route 파라미터로 변경
const limit = 50          // 한 번에 불러올 메시지 개수
const loadingMore = ref(false)
const allLoaded = ref(false)

const userStore = useUserStore()
const currentUserId = computed(() => Number(userStore.user?.id))
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
const getJobEmoji = job => jobEmojiMap[job] || '🌟'

// 마지막 메시지 DOM에 scrollIntoView
function setLastMessageRef(el) { lastMessage.value = el }

// REST로 초기 메시지 불러오기
async function fetchInitialMessages() {
  try {
    const before = new Date().toISOString()
    console.log(before)
    messages.value = await getChatMessages(partyId, before, limit)
    await nextTick()
    lastMessage.value?.scrollIntoView({ behavior: 'smooth' })
  } catch (err) {
    console.error('초기 메시지 로드 실패', err)
  }
}

// 스크롤이 맨 위에 도달하면 이전 메시지(조인 시점 이후)를 더 불러오기
async function onScrollTop(e) {
  const el = e.target
  if (el.scrollTop === 0 && !loadingMore.value && !allLoaded.value) {
    loadingMore.value = true
    const first = messages.value[0]
    const before = first?.sentAt
    const newMsgs = await getChatMessages(partyId, before, limit)
    if (newMsgs.length < limit) allLoaded.value = true
    messages.value.unshift(...newMsgs)
    loadingMore.value = false
  }
}

// WebSocket 메시지 수신 핸들러
function handleMessage(message) {
  messages.value.push(message)
  nextTick(() => lastMessage.value?.scrollIntoView({ behavior: 'smooth' }))
}

// STOMP 연결
function tryConnect() {
  connect(partyId, handleMessage)
}

// 로그인 토큰 감지 → 초기 메시지 + WS 연결
watch(
  () => userStore.accessToken,
  async token => {
    if (token) {
      await fetchInitialMessages()
      tryConnect()
    }
  },
  { immediate: true }
)

onMounted(() => {
  // 혹시 accessToken이 없을 때 대체 처리
})

onBeforeUnmount(() => {
  disconnect()
})

// 메시지 전송
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

// 내 메시지 클래스
function getMessageClass(msg) {
  return { me: Number(msg.userId) === currentUserId.value }
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