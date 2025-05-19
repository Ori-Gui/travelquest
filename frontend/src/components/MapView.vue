<template>
  <div class="map-container">
    <div id="map"></div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

const loadKakaoMaps = () => {
  return new Promise((resolve, reject) => {
    if (window.kakao) {
      resolve(window.kakao);
    } else {
      const script = document.createElement('script');
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=95641937d8dff26c96d6aa4f99519a55`;
      script.onload = () => {
        window.kakao.maps.load(() => {
          resolve(window.kakao);
        });
      };
      script.onerror = reject;
      document.head.appendChild(script);
    }
  });
};

onMounted(async () => {
  try {
    const kakao = await loadKakaoMaps();

    const mapContainer = document.getElementById('map');
    const mapOption = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 4,
    };

    const map = new kakao.maps.Map(mapContainer, mapOption);

    const dungeons = [
      { title: '던전 A', lat: 33.450701, lng: 126.570667, difficulty: 4, maxPartySize: 3 },
      { title: '던전 B', lat: 33.450936, lng: 126.569477, difficulty: 2, maxPartySize: 2 },
      { title: '던전 C', lat: 33.449701, lng: 126.571667, difficulty: 6, maxPartySize: 4 },
      { title: '던전 D', lat: 33.451301, lng: 126.572967, difficulty: 8, maxPartySize: 6 },
    ];

    dungeons.forEach((d) => {
      const marker = new kakao.maps.Marker({
        map,
        position: new kakao.maps.LatLng(d.lat, d.lng),
        title: d.title,
      });

      const infowindow = new kakao.maps.InfoWindow({
        content: `
          <div style="padding:5px;font-size:0.5rem;">
            ${d.title}<br>
            난이도: Lv.${d.difficulty}<br>
            최대 파티 인원: ${d.maxPartySize}
          </div>`,
      });

      kakao.maps.event.addListener(marker, 'mouseover', () => infowindow.open(map, marker));
      kakao.maps.event.addListener(marker, 'mouseout', () => infowindow.close());
    });
  } catch (error) {
    console.error("Kakao Maps 로드 에러:", error);
  }
});
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 500px;
  border: 3px solid #2d2d2d;
  border-radius: 12px;
  overflow: hidden;
}

#map {
  width: 100%;
  height: 100%;
}
</style>
