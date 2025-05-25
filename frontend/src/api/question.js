import axios from '@/lib/axios'

export const getMyCompletedQuests = async (userId, page, size) => {
    const response = await axios.get(`/api/v1/quest/my/${userId}`,
        {params: {page, size}});
    return response.data;
}