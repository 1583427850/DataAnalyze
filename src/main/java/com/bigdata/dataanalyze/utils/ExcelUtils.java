package com.bigdata.dataanalyze.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.bigdata.dataanalyze.entity.ExcelEntity;
import com.bigdata.dataanalyze.entity.Ranking;
import com.bigdata.dataanalyze.entity.ResponseRank;
import com.bigdata.dataanalyze.entity.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel 相关工具类
 */
@Slf4j
public class ExcelUtils {

    /**
     * excel 转 csv
     *
     * @param multipartFile
     * @return
     */
    public static String excelToCsv(MultipartFile multipartFile) {
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:网站数据.xlsx");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 读取数据
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误", e);
        }
        if (CollUtil.isEmpty(list)) {
            return "";
        }
        // 转换为 csv
        StringBuilder stringBuilder = new StringBuilder();
        // 读取表头
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
        // 过滤null数据
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");
        // 读取数据
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
            stringBuilder.append(StringUtils.join(dataList, ",")).append("\n");
        }
        return stringBuilder.toString();
    }

    public static Collection<String> getChartHeader(MultipartFile multipartFile){
         // 读取数据
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误", e);
        }
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        // 转换为 csv
        StringBuilder stringBuilder = new StringBuilder();
        // 读取表头
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
        return headerMap.values();

    }

    public static void getTestXlsx(String startTime,String endTime,String num,String filePath){
        LocalDate sd = LocalDate.of(2023, Month.of(1), 2);
        Ranking ranking = new Ranking(sd.toString(), sd.toString(), 20);
//        System.out.println(JSON.toJSONString(ranking));
        HttpResponse response = HttpRequest.post("https://index.baidu.com/insight/brand/queryTradeRank").body(JSON.toJSONString(ranking)).execute();
        ResultEntity bean = JSONUtil.toBean(response.body(), ResultEntity.class);

        HashMap<String, ExcelEntity> map = new HashMap<>();

        bean.getData().getData().forEach(start->{
            ExcelEntity excelEntity = new ExcelEntity();
            excelEntity.setModel(start.getEntityName());
            excelEntity.setStartTime(bean.getData().getStartDate());
            excelEntity.setStartRank(start.getRank().toString());
            map.put(start.getEntityName(),excelEntity);
        });

//        第二次查询
        LocalDate ed = sd.plusMonths(1L);
        Ranking endRanking = new Ranking(ed.toString(), ed.toString(), 20);
        HttpResponse endResponse = HttpRequest.post("https://index.baidu.com/insight/brand/queryTradeRank").body(JSON.toJSONString(endRanking)).execute();
        ResultEntity endBean = JSONUtil.toBean(endResponse.body(), ResultEntity.class);
        endBean.getData().getData().forEach(end->{
            ExcelEntity excelEntity = map.get(end.getEntityName());
            if(excelEntity!=null) {
                excelEntity.setEndTime(endBean.getData().getStartDate());
                excelEntity.setEndRank(end.getRank().toString());
            }else{
                ExcelEntity newEntity = new ExcelEntity();
                newEntity.setStartRank("未在榜单上面");
                newEntity.setEndRank(end.getRank().toString());
                newEntity.setModel(end.getEntityName());
                newEntity.setStartTime(bean.getData().getStartDate());
            }
        });

        Collection<ExcelEntity> values = map.values();

        EasyExcel.write(filePath, ExcelEntity.class).sheet("模板").doWrite(values);
    }


    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {

    }

}
