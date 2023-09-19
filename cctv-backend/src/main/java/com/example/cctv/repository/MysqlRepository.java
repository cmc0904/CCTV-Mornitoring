package com.example.cctv.repository;

import com.example.cctv.domain.CCTVData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MysqlRepository extends JpaRepository<CCTVData, Long> {
    CCTVData findById(long id);

    List<CCTVData> findByDt(Date dt);

}
