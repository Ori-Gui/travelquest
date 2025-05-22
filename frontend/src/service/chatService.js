import SockJS from 'sockjs-client'
import { Client } from '@stomp/stompjs'
import { useUserStore } from '@/stores/userStore'

let stompClient = null

export function connect(partyId, onMessageReceived) {
  const userStore = useUserStore()
  const token = userStore.accessToken

  if (!token) {
    console.warn('❌ WebSocket 연결 실패: accessToken 없음')
    return
  }

  if (stompClient && stompClient.connected) {
    console.log('ℹ️ 이미 WebSocket 연결 상태입니다.')
    return
  }

  stompClient = new Client({
    webSocketFactory: () => new SockJS('http://localhost:8080/ws/chat'),
    connectHeaders: {
      Authorization: `Bearer ${token}`,
      partyId: partyId.toString(),
    },
    debug: str => console.log('[STOMP]', str),
    reconnectDelay: 5000,
    onConnect: () => {
      console.log('✅ WebSocket 연결 성공')
      stompClient.subscribe(`/topic/chat/${partyId}`, message => {
        onMessageReceived(JSON.parse(message.body))
      })
    },
    onStompError: frame => {
      console.error('❌ STOMP 오류', frame)
    },
    onWebSocketClose: () => {
      console.warn('⚠️ WebSocket 연결 종료됨')
    },
    onDisconnect: () => {
      console.log('🔌 WebSocket 연결 해제됨')
    },
  })

  stompClient.activate()
}

export function sendMessage(partyId, message) {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: `/app/chat.send.${partyId}`,
      body: JSON.stringify(message),
    })
  } else {
    console.warn('⚠️ WebSocket이 연결되지 않아 메시지를 보낼 수 없습니다.')
  }
}

export function disconnect() {
  if (stompClient) {
    stompClient.deactivate()
    stompClient = null
    console.log('🔌 WebSocket 수동 종료 완료')
  }
}