<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/userStore'
import { jwtParser } from '@/utils/jwtParser'

const router = useRouter()
const userStore = useUserStore()

onMounted(() => {
  const cookies = document.cookie.split('; ')
  const tokenCookie = cookies.find(cookie => cookie.startsWith('accessToken='))
  const token = tokenCookie?.split('=')[1]

  if (token) {
    const payload = jwtParser(token)
    const registStatus = payload.registStatus
    userStore.setAccessToken(token)


    if (registStatus === 'NOT_YET') {
      router.push('/register')
    } else if(registStatus === 'IN_PROGRESS'){
      router.push('/mbti')
    } else if (registStatus === 'REGISTERED') {
      router.push('/main')
    } else {
      console.warn('⚠️ 알 수 없는 registStatus:', registStatus)
      router.push('/')
    }
  } else {
    console.error('❌ accessToken 쿠키가 존재하지 않습니다')
    router.push('/')
  }
});
</script>
