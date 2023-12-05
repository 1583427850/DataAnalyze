package com.bigdata.dataanalyze.controller;

import com.bigdata.dataanalyze.common.BaseResponse;
import com.bigdata.dataanalyze.common.dto.AnalyzeDto;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChartsController {

    @PostMapping("/gen")
    public BaseResponse analyze(@RequestBody AnalyzeDto dto,HttpServletRequest request){

        return null;
    }

    /**
     * 获取某一个用户的所有图表
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/data")
    public BaseResponse getCharts(Integer size, Integer page, HttpServletRequest request){
        return null;
    }


}
