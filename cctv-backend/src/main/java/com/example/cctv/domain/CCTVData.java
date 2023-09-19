package com.example.cctv.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@IdClass(CCTVDataId.class)
@Table(name = "cctv_list")
public class CCTVData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cctvname;

    private Date dt;
    
    private String roadsectionid;
    private double coordx;
    private double coordy;
    private String cctvresolution;
    private String filecreatetime;
    private String cctvtype;
    private String cctvformat;
    private String cctvurl;

}
