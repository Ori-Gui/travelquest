<template>
  <div class="min-h-screen flex flex-col items-center justify-between bg-mint px-4 py-6">
    <!-- 헤더 -->
    <Header />

    <!-- 본문 -->
    <main class="flex-grow flex items-center justify-center w-full">
      <div class="login-box">
        <h1 class="title">Travel Quest</h1>

        <!-- 카카오 로그인 버튼 -->
        <a id="kakao-login-btn" class="login-btn-wrapper">
          <img
            src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
            alt="카카오 로그인 버튼"
            class="social-btn-img"
          />
        </a>

        <!-- 네이버 로그인 버튼 -->
        <div id="naverIdLogin" class="login-btn-wrapper"></div>
      </div>
    </main>

    <!-- 푸터 -->
    <Footer />
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'

onMounted(() => {
  // ✅ 카카오 SDK
  if (!window.Kakao) {
    const kakaoScript = document.createElement('script')
    kakaoScript.src = 'https://developers.kakao.com/sdk/js/kakao.js'
    kakaoScript.onload = () => {
      window.Kakao.init('YOUR_KAKAO_JAVASCRIPT_KEY') // 👉 실제 앱 키로 교체
      const kakaoLoginBtn = document.getElementById('kakao-login-btn')
      kakaoLoginBtn.addEventListener('click', () => {
        window.Kakao.Auth.authorize({
          redirectUri: 'http://localhost:5173/kakao-callback' // 👉 실제 도메인에 맞게 수정
        })
      })
    }
    document.head.appendChild(kakaoScript)
  }

  // ✅ 네이버 SDK
  const naverScript = document.createElement('script')
  naverScript.src = 'https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js'
  naverScript.onload = () => {
    new window.naver.LoginWithNaverId({
      clientId: 'YOUR_NAVER_CLIENT_ID', // 👉 네이버 앱 클라이언트 ID로 교체
      callbackUrl: 'http://localhost:5173/naver-callback',
      isPopup: false,
      loginButton: {
        color: 'green',
        type: 3,
        height: 45,
        width: 300
      }
    }).init()
  }
  document.head.appendChild(naverScript)
})
</script>

<style scoped>
.bg-mint {
  background-color: #e6fff2;
}

.login-box {
  margin: auto;
  background-color: white;
  border: 3px solid #2d2d2d;
  box-shadow: 4px 4px 0 #2d2d2d;
  border-radius: 12px;
  padding: 1.5rem;
  width: 100%;
  max-width: 320px;
  text-align: center;
}

.title {
  font-family: 'Press Start 2P', cursive;
  font-size: 1rem;
  color: #146c43;
  margin-bottom: 1.5rem;
}

/* 공통 버튼 wrapper */
.login-btn-wrapper {
  width: 100%;
  height: 45px;
  margin: 0 auto 0.75rem auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 카카오 버튼 이미지 */
.social-btn-img {
  width: 80%;
  height: 45px;
  display: block;
}
</style>
