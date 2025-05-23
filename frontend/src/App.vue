<template>
  <div id="app">
    <AppHeader />
    <router-view /> <!-- 라우터에 따라 뷰가 여기에 표시됨 -->
    <AppFooter />
  </div>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import AppFooter from '@/components/AppFooter.vue'
</script>

<style>
body {
  margin: 0;
}
</style>
