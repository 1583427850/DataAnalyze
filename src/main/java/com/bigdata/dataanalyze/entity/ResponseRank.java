package com.bigdata.dataanalyze.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRank {
    @ExcelIgnore
    private Long entityId;
    @ExcelProperty("手机型号")
    private String entityName;
    @ExcelIgnore
    private Integer value;
    @ExcelProperty("排名")
    private Integer rank;
    @ExcelIgnore
    private String trend;
}

