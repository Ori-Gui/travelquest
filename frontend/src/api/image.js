import axios from '@/lib/axios'

/**
 * 이미지 업로드 API 모듈
 */
export function uploadImage(formData) {
  return axios.post('/api/v1/image', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });
}

/**
 * 이미지 삭제 API
 */
export function deleteImage(filename) {
  return axios.delete(filename);
}

/**
 * 이미지 URL 반환 헬퍼
 */
export function getImageUrl(filename) {
  return axios.get(filename);
}
