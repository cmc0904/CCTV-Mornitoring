package com.example.cctv.Controller;

import com.example.cctv.domain.CCTVData;
import com.example.cctv.servcie.CCTVMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class RoadMornitoringController {

    private static CCTVMonitoringService cctvMonitoringService;

    @Autowired
    public RoadMornitoringController(CCTVMonitoringService cctvMonitoringService) {
        this.cctvMonitoringService = cctvMonitoringService;
    }



    @GetMapping("/insertCCTV")
    public List<CCTVData> insertCCTV() {
        try {
            return cctvMonitoringService.insertData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getData/{codename}")
    public CCTVData getCCTVDataById(@PathVariable("codename") long codename) {
        try {
            return cctvMonitoringService.getCCTVData(codename);
            //return cctvMonitoringService.getCCTVData(codename, Date.valueOf(dt));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/geGrouptData/{dt}")
    public Map<String, List<CCTVData>> getCCTVDataByDt(@PathVariable("dt") String dt) {
        try {
            return cctvMonitoringService.getGroupCCTVData(dt);
            //return cctvMonitoringService.getCCTVData(codename, Date.valueOf(dt));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
