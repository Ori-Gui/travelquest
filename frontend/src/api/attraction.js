import axios from '@/lib/axios'

export const fetchSidos = async () => {
  const res = await axios.get('/api/v1/attractions/sidos')
  return res.data
}

export const fetchGuguns = async (sidoCode) => {
  const res = await axios.get('/api/v1/attractions/guguns', {
    params: { sidoCode }
  })
  return res.data
}

export const fetchContentTypes = async () => {
  const res = await axios.get('/api/v1/attractions/content-types')
  return res.data
}
