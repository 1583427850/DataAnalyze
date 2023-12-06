package com.bigdata.dataanalyze.entity;

import lombok.Data;

@Data
public class EsChart {

    private Long chartId;

    private Long userId;


    private String chartHeader;

    private String chartName;

    private String chartContent;
}
