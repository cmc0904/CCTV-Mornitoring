package com.example.cctv.component;

import com.example.cctv.domain.Response;
import com.example.cctv.domain.ResponseData;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CCTVMonitoring {
    private final Gson gson = new Gson();
    public ResponseData getRoadCCTVData() throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://openapi.its.go.kr:9443/cctvInfo?apiKey=0346ddcebab34967abb0104c65f906d6&type=ex&cctvType=1&minX=127.100000&maxX=128.890000&minY=34.100000&maxY=39.100000&getType=json");

        HttpResponse responseData = client.execute(request);
        String result = EntityUtils.toString(responseData.getEntity());

        // JSON 데이터를 Response 객체로 파싱합니다.
        ResponseData response = gson.fromJson(result, ResponseData.class);

        return response;
    }
}
