<template>
  <div id="app">
    <AppHeader />
    <main class="app-content">
      <router-view />
    </main>
    <AppFooter />
  </div>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
</script>

<style>
/* 브라우저 기본 마진 제거 및 html 높이 100%로 */
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
}

/* 루트 컨테이너를 뷰포트 높이에 맞춘 컬럼형 flex로 */
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* 헤더와 푸터 사이의 콘텐츠 영역이
   남은 공간을 차지하고 내부 스크롤 */
.app-content {
  flex: 1;
  overflow-y: auto;
}
</style>
