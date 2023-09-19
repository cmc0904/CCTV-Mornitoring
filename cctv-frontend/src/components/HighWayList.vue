<template>
  <nav class="navbar">
    고속도로 모니터링 시스템
  </nav>
  <nav class="sidebar">
    <div class="menu_content">
      <ul class="menu_items">
        <div class="menu_title menu_dahsboard"></div>
        <!-- duplicate or remove this li tag if you want to add or remove navlink with submenu -->
        <!-- start -->
        <li class="item" v-for="item in Object.keys(groupedData)" :key="item">
          <div href="#" class="nav_link submenu_item">
            <span class="navlink_icon">
              <i class="bx bx-home-alt"></i>
            </span>
            <span class="navlink">{{ item }}</span>
            <i class="bx bx-chevron-right arrow-left"></i>
          </div>

          <ul class="menu_items submenu">
            <a v-for="gdata in groupedData[item]" :key="gdata" @click="getData(gdata.id)"
              class="nav_link sublink">{{ gdata.cctvname.replaceAll(item + " ", "") }}</a>
          </ul>
        </li>
        <!-- end -->
      </ul>
    </div>
  </nav>
  <section>
    <div class="grid-item">
      <HelloWorld :videoSrc="selectedData"></HelloWorld>
    </div>
    <div class="grid-item">
      <KaKaoMap v-bind:videoSrc="selectedMapData"></KaKaoMap>
    </div>
    <div class="grid-item">
      <SuddenInformation v-on:value="updateParentValue"></SuddenInformation>
    </div>
    <div class="grid-item">
      <CautionSafetyZone v-on:value="updateParentValue"></CautionSafetyZone>
    </div>
  </section>
</template>
<script>
import HelloWorld from './HelloWorld.vue';
import axios from 'axios';
import KaKaoMap from './KaKaoMap.vue';
import SuddenInformation from './SuddenInformation.vue';
import CautionSafetyZone from './CautionSafetyZone.vue';

export default {
  components: {
    HelloWorld,
    KaKaoMap,
    SuddenInformation,
    CautionSafetyZone
  },
  data() {
    return {
      groupedData: {},
      selectedVideoData: {},
      selectedMapData: {},
      selectedData: {}
    };
  },
  mounted() {
    this.fetchData();
  },

  methods: {
    registerSubmenuToggle() {
      const submenuItems = document.querySelectorAll(".submenu_item");

      submenuItems.forEach((item, index) => {
        item.addEventListener("click", () => {
          item.classList.toggle("show_submenu");
          submenuItems.forEach((item2, index2) => {
            if (index !== index2) {
              item2.classList.remove("show_submenu");
            }
          });
        });
      });

    },
    async updateParentValue(value) {
      var data = value;
      if(data != undefined) {
        this.selectedMapData = value;
      }
    },
    async fetchData() {
      try {
        let today = new Date();
        let year = today.getFullYear(); // 년도
        let month = today.getMonth() + 1;  // 월
        let date = today.getDate();  // 날짜
        const response = await axios.get("http://localhost:1000/geGrouptData/" + year + "-" + month + '-' + date);

        this.groupedData = response.data;

        this.$nextTick(() => {
          this.registerSubmenuToggle();
        });
      } catch (error) {
        console.error('API 요청 중 오류 발생:', error);
      }
    },

    
    async getData(data){
      const response = await axios.get("http://localhost:1000/getData/" + data);
      this.selectedData = response.data;
      this.selectedMapData = response.data;
      this.selectedMapData.type = "video";
    }
    
  }
} 
</script>

<style>
.highway-icon {
  background-image: url("../assets/highwayIcon.png") -600px -100px no-repeat;
}

@font-face {
  font-family: 'TheJamsil5Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2') format('woff2');
  font-weight: 700;
  font-style: normal;
}

@font-face {
  font-family: 'KimjungchulGothic-Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/KimjungchulGothic-Bold.woff2') format('woff2');
  font-weight: 700;
  font-style: normal;
}

@font-face {
  font-family: 'HakgyoansimWoojuR';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2307-2@1.0/HakgyoansimWoojuR.woff2') format('woff2');
  font-weight: normal;
  font-style: normal;
}


.sudden-title {
  font-family: 'TheJamsil5Bold';
  color: red;
  font-size: 20px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

:root {
  --white-color: #fff;
  --blue-color: #4070f4;
  --grey-color: #707070;
  --grey-color-light: #aaa;
}

body {
  background-color: #e7f2fd;
  transition: background-color 0.5s ease;
}

body.dark {
  background-color: #333;
  --white-color: #333;
  --blue-color: #fff;
  --grey-color: #f2f2f2;
  --grey-color-light: #aaa;
}

.navbar {
  position: fixed;
  top: 0;
  width: 100%;
  left: 0;
  /* background-color: var(--white-color); */
  background-color: #2C4251;
  color: white;
  font-family: 'TheJamsil5Bold';
  height: 60px;
  line-height: 60px;
  z-index: 1000;
  box-shadow: 0 0 2px var(--grey-color-light);
  text-align: center;
  font-size: 25px;
}

.sidebar {
  background-color: var(--white-color);
  width: 280px;
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  padding: 80px 20px;
  z-index: 100;
  overflow-y: scroll;
  box-shadow: 0 0 1px var(--grey-color-light);
  transition: all 0.5s ease;
  font-family: 'TheJamsil5Bold';
}

.sidebar.close {
  padding: 60px 0;
  width: 80px;
}

.menu_content {
  position: relative;
}

.menu_title {
  margin: 15px 0;
  padding: 0 20px;
  font-size: 18px;
}

.sidebar.close .menu_title {
  padding: 6px 30px;
}

.menu_title::before {
  color: var(--grey-color);
  white-space: nowrap;
}

.sidebar.close .menu_title::before {
  content: "";
  position: absolute;
  height: 2px;
  width: 18px;
  border-radius: 12px;
  background: var(--grey-color-light);
}

.menu_items {
  padding: 0;
  list-style: none;
  font-family: 'KimjungchulGothic-Bold';

}

.navlink_icon {
  position: relative;
  font-size: 22px;
  min-width: 50px;
  line-height: 40px;
  display: inline-block;
  text-align: center;
  border-radius: 6px;
}

.navlink_icon::before {
  content: "";
  position: absolute;
  height: 100%;
  width: calc(100% + 100px);
  left: -20px;
}

.navlink_icon:hover {
  background: var(--blue-color);
}

.sidebar .nav_link {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 4px 15px;
  border-radius: 8px;
  text-decoration: none;
  color: var(--grey-color);
  white-space: nowrap;
}

.sidebar.close .navlink {
  display: none;
}

.nav_link:hover {
  color: var(--white-color);
  background: var(--blue-color);
}

/*
.sidebar.close .nav_link:hover {
  background: var(--white-color);
}
*/
.submenu_item {
  cursor: pointer;
}

.submenu {
  display: none;
}

.submenu_item .arrow-left {
  position: absolute;
  right: 10px;
  display: inline-block;
  margin-right: auto;
}

.sidebar.close .submenu {
  display: none;
}

.show_submenu~.submenu {
  display: block;
}

.show_submenu .arrow-left {
  transform: rotate(90deg);
}

.submenu .sublink {
  padding: 15px 15px 15px 52px;
}

.bottom_content {
  position: fixed;
  bottom: 60px;
  left: 0;
  width: 260px;
  cursor: pointer;
  transition: all 0.5s ease;
}

.bottom {
  position: absolute;
  display: flex;
  align-items: center;
  left: 0;
  justify-content: space-around;
  padding: 18px 0;
  text-align: center;
  width: 100%;
  color: var(--grey-color);
  border-top: 1px solid var(--grey-color-light);
  background-color: var(--white-color);
}

.bottom i {
  font-size: 20px;
}

.bottom span {
  font-size: 18px;
}

.sidebar.close .bottom_content {
  width: 50px;
  left: 15px;
}

.sidebar.close .bottom span {
  display: none;
}

.sidebar.hoverable .collapse_sidebar {
  display: none;
}

#sidebarOpen {
  display: none;
}

section {
  position: relative;
  background: #E4E9F7;
  height: 93.5vh;
  top: 60px;
  left: 280px;
  width: calc(100% - 280px);
  transition: all 0.5s ease;
}


section {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  overflow: hidden;
  gap: 5px;
  /* 셀 사이의 간격 설정 */
}

.grid-item {
  padding: 3px;
  background-color: #ffffff;
  text-align: center;
  font-size: 18px;
}

/* 각 자식 요소의 위치 설정 */
.grid-item:nth-child(1) {
  grid-column: 1 / span 1;
  /* 첫 번째 열, 첫 번째 행 */
  grid-row: 1 / span 1;
}

.grid-item:nth-child(2) {
  grid-column: 2 / span 1;
  /* 두 번째 열, 첫 번째 행 */
  grid-row: 1 / span 1;
}

.grid-item:nth-child(3) {
  grid-column: 1 / span 1;
  /* 첫 번째 열, 두 번째 행 */
  grid-row: 2 / span 1;
}

.grid-item:nth-child(4) {
  grid-column: 2 / span 1;
  /* 두 번째 열, 두 번째 행 */
  grid-row: 2 / span 1;
}

@media screen and (max-width: 768px) {
  #sidebarOpen {
    font-size: 25px;
    display: block;
    margin-right: 10px;
    cursor: pointer;
    color: var(--grey-color);
  }

  .sidebar.close {
    left: -100%;
  }
}</style>
