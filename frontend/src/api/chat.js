import axios from '@/lib/axios'

/**
 * 해당 유저가 조인한 시점 이후부터 최대 limit개 메시지를 오름차순으로 조회
 *
 * @param {Number} chatRoomId
 * @param {String} before ISO 문자열 (e.g. new Date().toISOString())
 * @param {Number} limit
 * @returns {Promise<ChatMessageDto[]>}
 */
export const getChatMessages = async (chatRoomId, before, limit) => {
  const response = await axios.get(`/api/v1/chat/${chatRoomId}/messages`, {
    params: { before, limit }
  })
  return response.data
}
