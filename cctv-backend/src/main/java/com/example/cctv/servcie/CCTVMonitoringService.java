package com.example.cctv.servcie;

import com.example.cctv.domain.CCTVData;
import com.example.cctv.component.CCTVMonitoring;
import com.example.cctv.domain.ResponseData;
import com.example.cctv.repository.MysqlRepository;
import jakarta.transaction.Transactional;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class CCTVMonitoringService implements Job {

    private static CCTVMonitoring cctvMonitoring;
    private static MysqlRepository mysqlRepository;
    @Autowired
    public CCTVMonitoringService(CCTVMonitoring cctvMonitoring, MysqlRepository mysqlRepository) {
        this.cctvMonitoring = cctvMonitoring;
        this.mysqlRepository = mysqlRepository;

    }

    @Transactional
    public List<CCTVData> insertData() throws IOException {
        List<CCTVData> cctvDataList = cctvMonitoring.getRoadCCTVData().getResponse().getData();
        System.out.println(cctvDataList.toString());

        for(CCTVData cctvData : cctvDataList) {
            cctvData.setDt(Date.valueOf(LocalDate.now()));
            mysqlRepository.save(cctvData);
        }
        return cctvDataList;
    }


    public CCTVData getCCTVData(long cctvName) throws IOException {
        CCTVData cctvDate = mysqlRepository.findById(cctvName);
        return cctvDate;
    }

    public Map<String, List<CCTVData>> getGroupCCTVData(String dt) throws IOException {

        List<CCTVData> responseData = mysqlRepository.findByDt(Date.valueOf(dt));

        List<String> a = new ArrayList<>();

        for (CCTVData item : responseData) {
            a.add(item.getCctvname().split(" ")[0]);
        }

        Set<String> uniqueArr = new HashSet<>(a);

        Map<String, List<CCTVData>> groupedData = new HashMap<>();

        for (String uniqueCCTV : uniqueArr) {
            List<CCTVData> filteredData = new ArrayList<>();
            for (CCTVData item : responseData) {
                if (item.getCctvname().startsWith(uniqueCCTV)) {
                    filteredData.add(item);
                }
            }
            groupedData.put(uniqueCCTV, filteredData);
        }

        return groupedData;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            insertData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
