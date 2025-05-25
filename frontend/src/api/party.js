import axios from '@/lib/axios'

export const getPartiesByDungeonId = async (dungeonId) => {
  const response = await axios.get(`/api/v1/party/${dungeonId}`)
  return response.data
}

export const createParty = async (dungeonId, partyData) => {
  const response = await axios.post(`/api/v1/party/${dungeonId}`, partyData)
  return response.data
}

export const getPartyDetailById = async (partyId) => {
  const response = await axios.get(`/api/v1/party/detail/${partyId}`)
  return response.data
}

export const getPartyStatus = async (partyId) => {
  const response = await axios.get(`/api/v1/party/${partyId}/info`)
  return response.data
}

export const getPartyMembers = async (partyId) => {
  const response = await axios.get(`/api/v1/partymember/party/${partyId}`)
  return response.data
}

export const getRequiredJobs = async (partyId) => {
  const response = await axios.get(`/api/v1/party/${partyId}/required`)
  return response.data
}

export const joinParty = async (partyId) => {
  const response = await axios.post(`/api/v1/party/join/${partyId}`)
  return response.data
}

export const leaveParty = async (partyId) => {
  const response = await axios.delete(`/api/v1/party/leave/${partyId}`)
  return response.data
}

export const kickPartyMember = async (partyId, userId) => {
  const response = await axios.post(`/api/v1/party/${partyId}/kick/${userId}`)
  return response.data
}

export const getMyParties = async (userId) => {
  const response = await axios.get(`/api/v1/party/my/${userId}`)
  return response.data
}

export async function updatePartyStatus(partyId, status) {
  const r = await axios.patch(
        `/api/v1/party/${partyId}/status`,
        { status: 'IN_PROGRESS' }
    )
    return r.data
}