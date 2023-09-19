<template>
  <div class="sudden-title">CCTV MAP</div>
  <div id="map"></div>
</template>
<style scoped>
#map {
  width: 100%;
  height: 492px;
}
</style>
<script>

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      marker: null,
      markers: []
    };
  },
  props: {
    videoSrc: Object,
  },

  mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  unmounted() { },
  methods: {
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=d4898454a4f4340b3af6d370e995ef63&autoload=false";
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(37.7009201049804, 128.873397827148),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(container, options);
      // this.loadMaker();
    },
    loadMaker() {
      const markerPosition = new window.kakao.maps.LatLng(
        33.450701,
        126.570667
      );

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });

      marker.setMap(this.map);
    },

  },
  watch: {
    videoSrc(newVal) {
      this.markers.forEach(function(marker) {
        marker.setMap(null);
      });
      var moveLatLon ;

      var imageSrc, imageSize, imageOption, markerImage;

      if(newVal.type == "video") {
        imageSrc = 'https://cdn.discordapp.com/attachments/1084502680313020538/1151658386509791284/4017956.png', // 마커이미지의 주소입니다    
        imageSize = new window.kakao.maps.Size(32, 32), // 마커이미지의 크기입니다
        imageOption = {offset: new window.kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),

        moveLatLon = new window.kakao.maps.LatLng(newVal.coordy, newVal.coordx);
      } else if(newVal.type == "돌발상황정보") {
        imageSrc = 'https://cdn.discordapp.com/attachments/1084502680313020538/1151659790737620992/Attention-sign-icon.png', // 마커이미지의 주소입니다    
        imageSize = new window.kakao.maps.Size(33, 33), // 마커이미지의 크기입니다
        imageOption = {offset: new window.kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),

        moveLatLon = new window.kakao.maps.LatLng(newVal.coordY, newVal.coordX);
      } else if(newVal.type == "주의안전구간") {
        imageSrc = 'https://cdn.discordapp.com/attachments/1084502680313020538/1151659790737620992/Attention-sign-icon.png', // 마커이미지의 주소입니다    
        imageSize = new window.kakao.maps.Size(33, 33), // 마커이미지의 크기입니다
        imageOption = {offset: new window.kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),

        moveLatLon = new window.kakao.maps.LatLng(newVal.revY, newVal.revX);
      }
      // // 지도 중심을 이동 시킵니다
      this.map.setCenter(moveLatLon);

      this.marker = new window.kakao.maps.Marker({
        position: moveLatLon,
        image: markerImage// 위도, 경도 순서로 입력
      });

      this.markers.push(this.marker);
      this.marker.setMap(this.map);
    },
  },
};
</script>
