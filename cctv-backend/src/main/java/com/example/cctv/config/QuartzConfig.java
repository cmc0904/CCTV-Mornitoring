package com.example.cctv.config;

import com.example.cctv.servcie.CCTVMonitoringService;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(CCTVMonitoringService.class);
        factoryBean.setGroup("dailyJobGroup");
        factoryBean.setName("dailyJob");
        factoryBean.setDurability(true); // Set job as durable
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0 0 12 * * ?"); // 매일 12시에 실행
        return factoryBean;
    }
}
