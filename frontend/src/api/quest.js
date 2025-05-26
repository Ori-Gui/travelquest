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
