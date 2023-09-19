<template>
    <div class="sudden-title">
        주의안전구간
        <button style = "margin-top: 5px;" @click="fetchData()">데이터 새로고침</button>

    </div>

    <div class="table-container">
        <table v-for="data in datas" :key="data" class="tbl">
            <tr>
                <th>종류</th>
                <td>{{data.outbrkType}}</td>
            </tr>
            <tr>
                <th>도로명</th>
                <td>{{ data.revRouteName }}</td>
            </tr>
            <tr>
                <th>노선방향</th>
                <td>{{ data.roadDrcType }}</td>
            </tr>
            <tr>
                <th>내용</th>
                <td>{{ data.message }}</td>
            </tr>
            <tr>
                <th colspan="2">
                    <button @click = "postToMap(data)">위치 자세히 보기</button>
                </th>
            </tr>
        </table>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            datas: null,
        };
    },
    mounted() {
        this.fetchData();
    },
    methods: {
        async fetchData() {
            try {
                const response = await axios.get('https://openapi.its.go.kr:9443/posIncidentInfo?apiKey=0346ddcebab34967abb0104c65f906d6&minX=126.800000&maxX=127.890000&minY=34.900000&maxY=35.100000&getType=json');

                this.datas = response.data.body.items;

            } catch (error) {
                console.error('API 요청 중 오류 발생:', error);
            }
        },
        postToMap(value) {
            value.type = "주의안전구간"
            this.$emit("value", value);
        }
    }
}
</script>

<style scoped>
.table-container {
  /* 상위 부모 요소에 스크롤을 추가 */
  max-height: 400px; /* 원하는 높이로 설정 */
  overflow-y: scroll;
  border: 1px solid #ccc; /* 스크롤된 영역의 테두리를 추가할 수 있습니다. */
}

.tbl {
  width: 100%;
  border-collapse: collapse;
  font-family: 'HakgyoansimWoojuR';
  margin-top: 3px;
}

.tbl, tr, td, th{
    border: 1px solid black;
}
th, th {
    padding: 5px;
}

button {
    width: 100%;
    height: 100%;
    background-color: #ccc;
    font-family: 'HakgyoansimWoojuR';
    font-size: 20px;
}
</style>