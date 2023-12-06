package com.bigdata.dataanalyze.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDataEntity {
    String startDate;
    String endDate;
    List<ResponseRank> data;
}
