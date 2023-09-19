<template>
  <div class="sudden-title">
    CCTV
  </div>
  <video ref="videoPlayer" controls autoplay></video>
</template>

<script>
import Hls from 'hls.js';

export default {
  name: 'VideoPlayer',
  props: {
    videoSrc: Object, // m3u8 영상의 URL을 받는 prop
  },
  data() {
    return {
      hls: null,
    };
  },
  create() {
    this.initHls();
  },

  watch: {
    videoSrc: 'initHls', // videoSrc 프로퍼티가 변경될 때 initHls 함수를 실행
  },
  methods: {
    initHls() {
      const video = this.$refs.videoPlayer;

      if (Hls.isSupported()) {
        this.hls = new Hls({
          autoStartLoad: true,
        });

        this.hls.loadSource(this.videoSrc.cctvurl);
        this.hls.attachMedia(video);
        this.hls.startLoad();
      } else if (video.canPlayType('application/vnd.apple.mpegurl')) {
        video.src = this.videoSrc;
      }
    },
  },
};
</script>

<style scoped>
video {
  width: 100%;
  /* height: 100vh; */
}
</style>