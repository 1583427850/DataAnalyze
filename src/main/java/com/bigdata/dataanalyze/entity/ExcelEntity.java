package com.bigdata.dataanalyze.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelEntity {
    @ExcelProperty("电脑型号")
    String Model;
    @ExcelProperty("开始时间")
    String startTime;
    @ExcelProperty("结束时间")
    String endTime;
    @ExcelProperty("开始排名")
    String startRank;
    @ExcelProperty("结束排名")
    String endRank;
}
