<template>
  <div class="app-container">
    <Header />

    <!-- 던전 정보 영역 -->
    <section class="dungeon-info">
      <div>📅 기간: 5.22 ~ 5.24</div>
      <div>📍 던전명: No.2, 제주 명소 탐방</div>
      <div class="tags">
        <span class="tag">명소</span>
        <span class="tag">자연</span>
        <span class="tag">사진</span>
      </div>
    </section>

    <!-- 탭 메뉴 -->
    <nav class="tabs">
      <button :class="{ active: activeTab === 'info' }" @click="activeTab = 'info'">정보</button>
      <button :class="{ active: activeTab === 'quest' }" @click="activeTab = 'quest'">퀘스트</button>
      <button :class="{ active: activeTab === 'party' }" @click="activeTab = 'party'">파티</button>
      <button :class="{ active: activeTab === 'chat' }" @click="activeTab = 'chat'">채팅</button>
    </nav>

    <!-- 각 탭의 콘텐츠 -->
    <section v-if="activeTab === 'chat'" class="chat-box">
      <div class="chat-title">💬 파티 채팅</div>
      <div class="chat-messages">
        <div class="chat-message">
          <div class="nickname">용사군</div>
          <div class="message">안녕하세요!!</div>
        </div>
        <div class="chat-message">
          <div class="nickname">마법사짱</div>
          <div class="message">일정 조율할게요~</div>
        </div>
      </div>
      <div class="chat-input">
        <input type="text" placeholder="메시지를 입력하세요." />
        <button class="send-button">전송</button>
      </div>
    </section>

    <section v-if="activeTab === 'party'" class="party-info">
      <div class="dungeon-info">
        <div>📅 기간: 5.25 ~ 5.27</div>
        <div>📍 던전명: No.1, 강릉트레킹 던새사진</div>
        <div class="tags">
          <span class="tag">하이킹</span>
          <span class="tag">자연</span>
          <span class="tag">사진</span>
          <span class="tag">산도전</span>
        </div>
      </div>

      <div class="party-members">
        <div class="member"><b>용사군</b> <span class="role">(리더)</span> - Warrior • Lv.3 • ENFJ</div>
        <div class="member">마법사짱 - Mage • Lv.3 • INTP</div>
        <div class="member">힐러왕 - Healer • Lv.4 • ISFJ</div>
      </div>

      <button class="join-button">파티에 참가하기</button>

      <div class="required-jobs">
        <div>필요한 직업군</div>
        <ul>
          <li>전사 (ESTJ, ESTP)</li>
          <li>마법사 (INTJ, INTP)</li>
          <li>힐러 (ISFJ, ESFJ)</li>
          <li>레인저 (STP, ENFP)</li>
        </ul>
      </div>
    </section>

    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';

const activeTab = ref('chat');
</script>

<style scoped>
.app-container {
  background-color: #c2f5d6;
  font-family: 'Arial';
  padding: 1rem;
}

.dungeon-info {
  background-color: white;
  border: 2px solid black;
  padding: 0.5rem;
  margin-bottom: 1rem;
}

.tags .tag {
  background-color: #ffeb3b;
  padding: 0.2rem 0.5rem;
  margin-right: 0.3rem;
  border-radius: 5px;
}

.tabs button {
  margin: 0.2rem;
  padding: 0.4rem 0.8rem;
  border-radius: 8px;
  border: 1px solid #444;
  background: white;
  cursor: pointer;
}
.tabs .active {
  background-color: #b3f7c3;
  font-weight: bold;
}

.chat-box {
  background: white;
  border: 2px solid black;
  padding: 0.5rem;
  margin-top: 1rem;
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

.party-info {
  margin-top: 2rem;
}

.party-members .member {
  background-color: #d6fbd6;
  padding: 0.4rem;
  margin-bottom: 0.3rem;
  border-radius: 4px;
}

.join-button {
  width: 100%;
  background-color: yellow;
  padding: 0.5rem;
  border: 1px solid black;
  font-weight: bold;
  margin: 1rem 0;
}

.required-jobs ul {
  list-style: none;
  padding: 0;
}

.required-jobs li {
  margin: 0.3rem 0;
}
</style>