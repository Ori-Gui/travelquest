/* api/quest.js */
import axios from '@/lib/axios'

/**
 * 던전ID + 파티ID로 퀘스트 목록을 가져오되,
 * 각 퀘스트에 verified 플래그를 포함해서 반환받는다.
 */
export function fetchQuests(dungeonId, partyId) {
  return axios
    .get('/api/v1/dungeons/' + dungeonId + '/quests', {
      params: { partyId }
    })
    .then(res => res.data)
}

/**
 * 퀘스트 아이디로 퀘스트 상세 정보를 가져온다.
 */
export function fetchQuest(questId) {
  return axios
    .get(`/api/v1/quests/${questId}`)
    .then(res => res.data)
}

/**
 * 퀘스트 검증 글을 삭제한다.
 * @param {number} verificationId
 */
export function deleteQuestVerification(verificationId) {
  return axios
    .delete(`/api/v1/quest-verifications/${verificationId}`)
}
