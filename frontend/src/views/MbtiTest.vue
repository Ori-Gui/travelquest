<template>
  <div class="mbti-container">
    <main class="question-wrapper" v-if="currentQuestion">
      <MbtiCheckForm
        :question="currentQuestion"
        @answered="handleAnswer"
      />
    </main>
  </div>
</template>

<script setup>
import MbtiCheckForm from '@/components/MbtiCheckForm.vue'
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import testData from '@/assets/mbti_test.json'
import axios from '@/lib/axios'

const router = useRouter()
const questions = ref([])
const currentIndex = ref(0)
const answers = ref([])

onMounted(() => {
  questions.value = testData
})

const currentQuestion = computed(() => questions.value[currentIndex.value])

const handleAnswer = (value) => {
  const { type } = questions.value[currentIndex.value]
  answers.value.push({ type, value })

  if (currentIndex.value < questions.value.length - 1) {
    currentIndex.value++
  } else {
    submitResult()
  }
}

const submitResult = async () => {
  try {
    const response = await axios.post('/api/v1/user/mbti', answers.value)
    router.push({
        name: 'MbtiResult',
        params: { mbtiCode: response.data.mbti }
    })
  } catch (e) {
    console.error('MBTI 제출 실패:', e)
  }
}
</script>

<style scoped>
.mbti-container {
  background-color: #e6fff2;
  min-height: 70vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.question-wrapper {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
