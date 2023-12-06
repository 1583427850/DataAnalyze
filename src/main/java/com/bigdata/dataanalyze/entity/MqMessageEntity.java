package com.bigdata.dataanalyze.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqMessageEntity {

    private List<GptMessage> messagews;
    private Long chartId;
    private Integer retryCount;
    private String chartName;
    private String chartHeader;
}
