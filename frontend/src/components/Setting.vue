<template>
  <div class="settings-container">
    <!-- 계정 관리 섹션 -->
    <section class="settings-section">
      <h2 class="section-header">계정 관리</h2>
      <div class="setting-item">
        <span>프로필 수정</span>
        <button class="btn" @click="goToProfileChange">수정하기</button>
      </div>
      <div class="setting-item">
        <span>비밀번호 변경</span>
        <button class="btn" @click="goToPasswordChange">변경하기</button>
      </div>
      <div class="setting-item">
        <span>회원 탈퇴</span>
        <button class="btn danger" @click="goToWithdrawal">탈퇴하기</button>
      </div>
    </section>

    <!-- 보안 · 알림 섹션 -->
    <section class="settings-section">
      <h2 class="section-header">보안 · 알림</h2>
      <div class="setting-item">
        <span>이메일 알림</span>
        <input type="checkbox" class="toggle" />
      </div>
      <div class="setting-item">
        <span>푸시 알림</span>
        <input type="checkbox" class="toggle" />
      </div>
      <div class="setting-item">
        <span>2단계 인증</span>
        <input type="checkbox" class="toggle" />
      </div>
    </section>

    <!-- UI 환경 설정 섹션 -->
    <section class="settings-section">
      <h2 class="section-header">UI 환경 설정</h2>
      <div class="setting-item">
        <span>다크 모드</span>
        <input type="checkbox" class="toggle" />
      </div>
      <div class="setting-item">
        <span>글자 크기</span>
        <select class="select">
          <option>작게</option>
          <option selected>보통</option>
          <option>크게</option>
        </select>
      </div>
      <div class="setting-item">
        <span>언어 선택</span>
        <select class="select">
          <option selected>한국어</option>
          <option>English</option>
        </select>
      </div>
    </section>

    <!-- 앱 정보 섹션 -->
    <section class="settings-section">
      <h2 class="section-header">앱 정보</h2>
      <div class="setting-item">
        <span>버전</span>
        <span>v1.0.0</span>
      </div>
      <div class="setting-item">
        <span>이용 약관</span>
        <button class="btn link" @click="goToTerms">보기</button>
      </div>
      <div class="setting-item">
        <span>개인정보 처리방침</span>
        <button class="btn link" @click="goToPrivacy">보기</button>
      </div>
    </section>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

function goToProfileChange() {
  router.push({ name: 'ProfileChange' });
}

function goToPasswordChange() {
  router.push({ name: 'PasswordChange' });
}

function goToWithdrawal() {
  router.push({ name: 'Withdrawal' });
}

function goToTerms() {
  router.push({ name: 'Terms' });
}

function goToPrivacy() {
  router.push({ name: 'PrivacyPolicy' });
}
</script>

<style scoped>
.settings-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.settings-section {
  background: #fff;
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
}

.section-header {
  font-size: 1.1rem;
  font-weight: bold;
  margin-bottom: 0.75rem;
}

.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0;
  border-bottom: 1px dashed #ddd;
}
.setting-item:last-child {
  border-bottom: none;
}

.btn {
  background: white;
  border: 1px solid #6cd395;
  border-radius: 8px;
  padding: 0.4rem 0.8rem;
  font-size: 0.85rem;
  cursor: pointer;
  color: #6cd395;
}
.btn.danger {
  border-color: #e74c3c;
  color: #e74c3c;
}
.btn.link {
  background: transparent;
  border: none;
  color: #2d5f2e;
  text-decoration: underline;
}

.toggle {
  width: 40px;
  height: 20px;
  cursor: pointer;
}

.select {
  padding: 0.3rem 0.6rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  background: white;
}
</style>
