<template>
  <section class="chat-box">
    <div class="chat-title">파티 채팅</div>
    <div class="chat-messages" ref="messagesContainer" @scroll.passive="onScrollTop">
      <template v-for="(msg, idx) in messages" :key="msg.id">
        <!-- 시스템 메시지 -->
        <div v-if="msg.system" class="system-message">
          {{ msg.content }}
        </div>

        <!-- 일반 메시지 -->
        <div
          v-else
          class="chat-message"
          :class="{ me: Number(msg.userId) === currentUserId }"
        >
          <!-- 다른 사람 메시지에만 프로필 사진 표시 -->
          <img
            v-if="msg.profileImage && Number(msg.userId) !== currentUserId"
            :src="msg.profileImage"
            alt="avatar"
            class="avatar"
          />
          <!-- 메시지 내용 -->
          <div class="message-content">
            <div class="nickname">{{ msg.name }}</div>
            <div class="message">{{ msg.message }}</div>
          </div>
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
import { ref, computed, watch, onBeforeUnmount, onMounted, nextTick, defineProps } from 'vue';
import { getChatMessages } from '@/api/chat';
import { useUserStore } from '@/stores/userStore';
import { connect, disconnect, sendMessage as sendToServer } from '@/service/chatService';

const input = ref('');
const messages = ref([]);
const messagesContainer = ref(null)
const props = defineProps({ partyId: { type: [String, Number], required: true } });
const partyId = props.partyId;
const limit = 200;
const loadingMore = ref(false);
const allLoaded = ref(false);

const userStore = useUserStore();
const currentUserId = computed(() => Number(userStore.user?.id));

// 초기 메시지 로드
async function fetchInitialMessages() {
  try {
    const before = new Date().toISOString();
    messages.value = await getChatMessages(partyId, before, limit);
    await nextTick();
    const el = messagesContainer.value
    if (el) {
      // 맨 아래로 부드럽게 스크롤
      el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' })
    }
  } catch (err) {
    console.error('초기 메시지 로드 실패', err);
  }
}

// 스크롤 맨 위에서 추가 로드
async function onScrollTop(e) {
  const el = e.target;
  if (el.scrollTop === 0 && !loadingMore.value && !allLoaded.value) {
    loadingMore.value = true;
    const first = messages.value[0];
    const before = first?.sentAt;
    const newMsgs = await getChatMessages(partyId, before, limit);
    if (newMsgs.length < limit) allLoaded.value = true;
    messages.value.unshift(...newMsgs);
    loadingMore.value = false;
  }
}

 // WebSocket 메시지 핸들러
 function handleMessage(message) {
   messages.value.push(message);
   nextTick(() => {
    const el = messagesContainer.value;
    if (el) {
      el.scrollTo({ top: el.scrollHeight, behavior: 'smooth' });
    }
   });
 }

// STOMP 연결
function tryConnect() {
  connect(partyId, handleMessage);
}

watch(
  () => userStore.accessToken,
  async token => {
    if (token) {
      await fetchInitialMessages();
      tryConnect();
    }
  },
  { immediate: true }
);

onBeforeUnmount(() => {
  disconnect();
});

// 메시지 전송
function sendMessage() {
  if (!input.value.trim() || !currentUserId.value) return;
  const message = {
    id: Date.now(),
    userId: currentUserId.value,
    name: userStore.user.userName || '익명',
    profileImage: userStore.user.profileImage,
    job: userStore.user.jobClassCode,
    message: input.value.trim(),
    sentAt: new Date().toISOString(),
  };
  sendToServer(partyId, message);
  input.value = '';
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
  text-align: center;
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

.system-message {
  text-align: center;
  font-size: 0.85rem;
  color: #999;
}

.chat-message {
  display: flex;
  align-items: flex-start;
}

.chat-message.me {
  flex-direction: row-reverse;
}

.avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 0.5rem;
}

.chat-message.me .avatar {
  margin-left: 0.5rem;
  margin-right: 0;
}

.message-content {
  display: flex;
  flex-direction: column;
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
  align-items: center;
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
  white-space: nowrap;
  flex-shrink: 0;
}

.send-button:hover {
  background-color: #57ba7a;
}
</style>
