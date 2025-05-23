import axios from '@/lib/axios'

export const fetchDungeonDetail = async (dungeonId) => {
  const res = await axios.get(`/api/v1/dungeons/${dungeonId}`)
  return res.data
}

export const fetchDungeonAttractions = async (dungeonId) => {
  const res = await axios.get(`/api/v1/dungeons/${dungeonId}/attractions`)
  return res.data
}

export const searchDungeons = async (condition) => {
  const res = await axios.post('/api/v1/dungeons/search', condition)
  return res.data
}

export const fetchFirstAttraction = async (dungeonId) => {
  try {
    const res = await axios.get(
      `/api/v1/dungeons/${dungeonId}/attractions/first`
    )
    return res.data
  } catch {
    return null
  }
}
