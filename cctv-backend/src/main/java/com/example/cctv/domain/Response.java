package com.example.cctv.domain;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private int coordtype;
    private List<CCTVData> data;

}
