<template>
  <section class="dungeon-info">
    <!-- 던전 기본 정보 -->
    <div class="info-header">
      <h2>{{ dungeon.title }}</h2>
      <div class="period">📅 {{ dungeon.startDate }} ~ {{ dungeon.endDate }}</div>
    </div>

    <div class="info-details">
      <div>난이도: Lv.{{ dungeon.difficulty }}</div>
      <div>최대 파티: {{ dungeon.maxPartySize }}명</div>
      <div>상태: <span :class="`status ${dungeon.status.toLowerCase()}`">{{ dungeon.status }}</span></div>
    </div>

    <!-- 여행지 목록 -->
    <div class="attraction-list">
      <h3>탐험할 여행지</h3>
      <ul>
        <li v-for="attr in attractions" :key="attr.contentId" class="attraction-item">
          <component
            :is="extractUrl(attr.homepage) ? 'a' : 'div'"
            :href="extractUrl(attr.homepage)"
            target="_blank"
            class="attraction-link"
          >
            <img :src="attr.firstImage1 || defaultImage" alt="{{ attr.title }} 대표 이미지" />
            <div class="attr-info">
              <strong>{{ attr.title }}</strong>
              <div class="attr-address">{{ attr.addr1 }}</div>
            </div>
          </component>
        </li>
      </ul>
    </div>
  </section>
</template>

<script setup>
import { defineProps } from 'vue'
import defaultImage from '@/assets/placeholder.png'
import { extractUrl } from "@/utils/linkUtils";

defineProps({
  dungeon: {
    type: Object,
    required: true
  },
  attractions: {
    type: Array,
    default: () => []
  }
})
</script>

<style scoped>
.dungeon-info {
  background-color: #fff;
  border: 2px solid #2d2d2d;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1rem;
}

.info-header h2 {
  margin: 0;
  font-size: 1.25rem;
}
.period {
  margin-top: 0.5rem;
  color: #555;
}

.info-details {
  display: flex;
  gap: 1.5rem;
  margin-top: 1rem;
  font-size: 0.95rem;
}
.status.open { color: green; }
.status.closed { color: red; }

.attraction-list {
  margin-top: 1.5rem;
}
.attraction-list h3 {
  margin-bottom: 0.75rem;
  font-size: 1rem;
}
.attraction-list ul {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  list-style: none;
  padding: 0;
  margin: 0;
}
.attraction-item {
  width: 120px;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
  background: #fafafa;
  margin: 0;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.attraction-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
}

.attraction-link {
  display: block;
  text-decoration: none;
  color: inherit;
  width: 100%;
  height: 100%;
}

.attraction-link img {
  width: 100%;
  height: 80px;
  object-fit: cover;
}

.attraction-link:hover {
  text-decoration: none;
}

.attraction-item img {
  width: 100%;
  height: 80px;
  object-fit: cover;
}
.attr-info {
  padding: 0.5rem;
  font-size: 0.85rem;
}
.attr-address {
  margin-top: 0.25rem;
  color: #777;
}
</style>
