<template>
  <div class="map-container">
    <div id="map"></div>
  </div>
</template>

<script setup>
import { onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import dungeonIconUrl from '@/assets/dungeon-gate.png?url'

// 부모로부터 마커 배열을 받는다
const props = defineProps({
  markers: {
    type: Array,
    default: () => []
  }
})

const router = useRouter()
let mapInstance
let kakaoInstance
let markerObjs = []
let infoWindow = null

// 카카오 스크립트 로더
const loadKakao = () =>
  new Promise((resolve, reject) => {
    if (window.kakao && window.kakao.maps) {
      return resolve(window.kakao)
    }
    const script = document.createElement('script')
    script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=95641937d8dff26c96d6aa4f99519a55&autoload=false`
    script.onload = () => window.kakao.maps.load(() => resolve(window.kakao))
    script.onerror = reject
    document.head.appendChild(script)
  })

// 기본 위치 분산 (Spiral/Circle Layout) 함수
function getOffsetPosition(base, index, totalCount) {
  const offset = 0.0001
  const angle = (2 * Math.PI * index) / totalCount
  const latOffset = offset * Math.cos(angle)
  const lngOffset = offset * Math.sin(angle)
  return {
    lat: base.lat + latOffset,
    lng: base.lng + lngOffset
  }
}

// 맵 초기화
onMounted(async () => {
  try {
    kakaoInstance = await loadKakao()
    mapInstance = new kakaoInstance.maps.Map(
      document.getElementById('map'),
      {
        center: new kakaoInstance.maps.LatLng(33.450701, 126.570667),
        level: 4
      }
    )
    // 초기 마커 렌더링
    updateMarkers(props.markers)
  } catch (e) {
    console.error('카카오 맵 로드 에러', e)
  }
})

// markers prop이 바뀔 때마다 마커 갱신
watch(
  () => props.markers,
  (newList) => {
    updateMarkers(newList)
  },
  { deep: true }
)

// 마커 업데이트 함수
function updateMarkers(list) {
  if (!mapInstance || !kakaoInstance) return
  // 기존 마커 제거
  markerObjs.forEach(m => m.setMap(null))
  markerObjs = []
  if (infoWindow) infoWindow.close()

  // 같은 위치 그룹핑
  const groups = {}
  list.forEach(item => {
    const key = `${item.lat},${item.lng}`
    if (!groups[key]) groups[key] = []
    groups[key].push(item)
  })

  // 커스텀 마커 이미지 설정
  const imageSize = new kakaoInstance.maps.Size(32, 32) // 아이콘 크기
  const imageOption = { offset: new kakaoInstance.maps.Point(16, 32) } // 마커 기준점
  const markerImage = new kakaoInstance.maps.MarkerImage(
    dungeonIconUrl,
    imageSize,
    imageOption
  )

  // 각 그룹에 분산 배치
  Object.values(groups).forEach(group => {
    const total = group.length
    group.forEach((item, idx) => {
      const { lat, lng } = getOffsetPosition({ lat: item.lat, lng: item.lng }, idx, total)
      const marker = new kakaoInstance.maps.Marker({
        map: mapInstance,
        position: new kakaoInstance.maps.LatLng(lat, lng),
        title: item.title,
        image: markerImage
      })

      kakaoInstance.maps.event.addListener(marker, 'click', () => {
        // 이전 InfoWindow 닫기
        if (infoWindow) infoWindow.close()
        // InfoWindow 콘텐츠
        const content = `
          <div style="padding:8px; width:14rem; font-size:0.8rem;">
            <strong>${item.title}</strong><br/>
            주여행지: <strong>${item.first}</strong><br/>
            시작: ${item.startDate}<br/>
            종료: ${item.endDate}<br/>
            난이도: Lv.${item.difficulty}<br/>
            최대 파티: ${item.maxPartySize}<br/>
            상태: ${item.status}<br/>
            <button id="detail-btn-${item.id}" style="margin-top:6px; padding:4px 8px; font-size:0.8rem; cursor:pointer;">상세보기</button>
            <button id="close-btn-${item.id}" style="top:4px; right:4px; border:none; background:transparent; font-size:1rem; cursor:pointer">❌</button>
          </div>
        `
        infoWindow = new kakaoInstance.maps.InfoWindow({ content })
        infoWindow.open(mapInstance, marker)
        // 버튼 클릭 이벤트 리스너
        setTimeout(() => {
          const closeBtn = document.getElementById(`close-btn-${item.id}`)
          closeBtn?.addEventListener('click', () => infoWindow.close())
          const detailBtn = document.getElementById(`detail-btn-${item.id}`)
          detailBtn?.addEventListener('click', () => {
            router.push({ name: 'DungeonDetail', params: { dungeonId: item.id } })
          })
        }, 0)
      })
      markerObjs.push(marker)
    })
  })

  // 지도 범위 재조정
  if (markerObjs.length) {
    const bounds = new kakaoInstance.maps.LatLngBounds()
    markerObjs.forEach(m => bounds.extend(m.getPosition()))
    mapInstance.setBounds(bounds)
  }
}
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 500px;
  border: 3px solid #2d2d2d;
  border-radius: 12px;
}
#map {
  width: 100%;
  height: 100%;
}
</style>
