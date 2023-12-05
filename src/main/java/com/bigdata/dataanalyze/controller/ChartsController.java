package com.bigdata.dataanalyze.controller;

import com.bigdata.dataanalyze.common.BaseResponse;
import com.bigdata.dataanalyze.common.dto.AnalyzeDto;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartsController {

    @PostMapping("/gen")
    public BaseResponse analyze(@RequestBody AnalyzeDto dto){

        return null;
    }

    @GetMapping("/data")
    public BaseResponse getCharts(Integer size,Integer page){
        return null;
    }

}
