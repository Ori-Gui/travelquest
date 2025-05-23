/* api/quest.js */
import axios from '@/lib/axios'

/**
 * 퀘스트 목록 조회
 * @param {Number} dungeonId
 */
export const fetchQuests = async (dungeonId) => {
  const res = await axios.get(`/api/v1/dungeons/${dungeonId}/quests`)
  return res.data
}

// /**
//  * 퀘스트 인증 제출
//  * @param {FormData} formData - questId, partyId, photo 필드 포함
//  */
// export const submitQuestVerification = async (formData) => {
//   const res = await axios.post(`/api/v1/quest-verifications`, formData, {
//     headers: { 'Content-Type': 'multipart/form-data' }
//   })
//   return res.data
// }
