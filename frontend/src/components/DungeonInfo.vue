<template>
  <section class="dungeon-info">
    <div>📅 기간: 5/25 ~ 5/27</div>
    <div>📍 던전명: No.37, 강화섬 비밀의 숲</div>
    <div class="tag-box">
      <div v-for="tag in tags" :key="tag" class="tag">{{ tag }}</div>
    </div>
  </section>
</template>

<script setup>
const tags = ["자연", "식도락", "사진", "강화문화재"];
</script>

<style scoped>
@import "@/assets/styles/fonts.css";

.dungeon-info {
  background-color: #fff;
  border: 2px solid #2d2d2d;
  padding: 1.5rem;
  border-radius: 12px;
  margin-bottom: 1rem;
  font-size: 1rem;
  line-height: 1.5;
  overflow-x: auto;
}

.tag-box {
  display: flex;
  gap: 0.5rem;
  margin-top: 0.5rem;
  flex-wrap: wrap;
}

.tag {
  background-color: #ffe599;
  border: 1px solid #2d2d2d;
  border-radius: 6px;
  padding: 0.3rem 0.6rem;
  font-size: 0.8rem;
}
</style>
