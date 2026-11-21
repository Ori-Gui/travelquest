<template>
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
</template>

<script setup>
import { onMounted } from 'vue'

// 카카오/네이버 공개 키는 frontend/.env 로 주입합니다(frontend/.env.example 참고).
// 카카오는 지도(VITE_KAKAO_MAP_APP_KEY)와 같은 JavaScript 앱 키를 씁니다.
const KAKAO_JS_APP_KEY = import.meta.env.VITE_KAKAO_JS_APP_KEY
const NAVER_CLIENT_ID = import.meta.env.VITE_NAVER_CLIENT_ID

onMounted(() => {
  // ✅ 카카오 SDK
  if (!window.Kakao) {
    const kakaoScript = document.createElement('script')
    kakaoScript.src = 'https://developers.kakao.com/sdk/js/kakao.js'
    kakaoScript.onload = () => {
      if (!KAKAO_JS_APP_KEY) {
        console.warn('VITE_KAKAO_JS_APP_KEY is not set. Kakao login init skipped. See frontend/.env.example.')
        return
      }
      window.Kakao.init(KAKAO_JS_APP_KEY)
      const kakaoLoginBtn = document.getElementById('kakao-login-btn')
      kakaoLoginBtn.addEventListener('click', () => {
        window.Kakao.Auth.authorize({
          redirectUri: 'http://192.168.205.51:8080/oauth2/authorization/kakao'
        })
      })
    }
    document.head.appendChild(kakaoScript)
  }

  // ✅ 네이버 SDK
  if (!NAVER_CLIENT_ID) {
    console.warn('VITE_NAVER_CLIENT_ID is not set. Naver login init skipped. See frontend/.env.example.')
    return
  }
  const naverScript = document.createElement('script')
  naverScript.src = 'https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js'
  naverScript.onload = () => {
    new window.naver.LoginWithNaverId({
      clientId: NAVER_CLIENT_ID,
      callbackUrl: 'http://192.168.205.51:8080/oauth2/authorization/naver',
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

.login-btn-wrapper {
  width: 100%;
  height: 45px;
  margin: 0 auto 0.75rem auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.social-btn-img {
  width: 80%;
  height: 45px;
  display: block;
  cursor: pointer;
}
</style>
