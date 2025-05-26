import axios from '@/lib/axios'

/**
 * 주어진 partyId의 Quest 검증 기록 목록을 가져옵니다.
 * @param {number} partyId
 * @returns {Promise<Array<{ id: number, questId: number, partyId: number, verifiedByUserId: number, photoUrl: string, status: string, verifiedAt: string }>>}
 */
export function fetchVerifications(partyId) {
  return axios
    .get('/api/v1/quest-verifications', {
      params: { partyId }
    })
    .then(res => res.data)
}

export function updateVerificationStatus(id, status) {
  return axios
    .patch(`/api/v1/quest-verifications/${id}`, { status })
    .then(res => res.data)
}
