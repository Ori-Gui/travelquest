// src/api/party.js
import axios from '@/lib/axios'

export const getPartiesByDungeonId = async (dungeonId) => {
  const response = await axios.get(`/api/v1/party/${dungeonId}`)
  return response.data
}

export const createParty = async (dungeonId, partyData) =>
  await axios.post(`/api/v1/party/${dungeonId}`, partyData)
