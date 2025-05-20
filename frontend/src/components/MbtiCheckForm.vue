<template>
  <div class="question-box">
    <h2 class="question-title">Q{{ question.id }}. {{ question.question }}</h2>
    <button
      v-for="option in question.options"
      :key="option.value"
      class="choice-button"
      @click="$emit('answered', option.value)"
    >
      {{ option.label }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  question: Object
})
</script>

<style scoped>
.question-box {
  background-color: #ffffff;
  border: 3px solid #2d2d2d;
  box-shadow: 4px 4px 0 #2d2d2d;
  border-radius: 16px;
  padding: 2rem;
  width: 90%;
  max-width: 360px;
  text-align: center;
}
.question-title {
  font-family: 'Press Start 2P', cursive;
  font-size: 0.9rem;
  margin-bottom: 1.5rem;
}
.choice-button {
  width: 100%;
  padding: 0.75rem 1rem;
  margin-bottom: 1rem;
  font-size: 0.85rem;
  border: 2px solid #2d2d2d;
  border-radius: 12px;
  background-color: white;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.choice-button:hover {
  background-color: #f0f0f0;
}
</style>
