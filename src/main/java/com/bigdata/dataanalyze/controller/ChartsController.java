package com.bigdata.dataanalyze.controller;

import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bigdata.dataanalyze.common.BaseResponse;
import com.bigdata.dataanalyze.common.ErrorCode;
import com.bigdata.dataanalyze.common.ResultUtils;
import com.bigdata.dataanalyze.common.dto.AnalyzeDto;
import com.bigdata.dataanalyze.entity.Chart;
import com.bigdata.dataanalyze.entity.EsChart;
import com.bigdata.dataanalyze.entity.ExcelEntity;
import com.bigdata.dataanalyze.entity.User;
import com.bigdata.dataanalyze.service.ChartService;
import com.bigdata.dataanalyze.service.EsChartService;
import com.bigdata.dataanalyze.service.UserService;
import com.bigdata.dataanalyze.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
public class ChartsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChartService chartService;

    @Autowired
    private EsChartService esChartService;

    @PostMapping("/gen")
    public BaseResponse analyze(@RequestPart("file")MultipartFile file,String goal,String chartName,HttpServletRequest request){

        if(goal==null || chartName==null || file==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"参数错误");
        }

//        User user = new User();
//        user.setId(1L);
        User user = userService.getLoginUser(request);
        if(user==null){
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR,"请先登录");
        }
        boolean result = chartService.genChart(goal,chartName, user,file);
        if(!result){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"生成图表失败");
        }
        return ResultUtils.success("成功");

    }

    /**
     * 获取某一个用户的所有图表
     * @param size
     * @param page
     * @return
     */
    @GetMapping("/data")
    public BaseResponse getCharts(Integer size, Integer page, HttpServletRequest request){
//        User user = userService.getLoginUser(request);
        User user = new User();
        if(user==null){
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR,"请先登录");
        }
        user.setId(1L);
        List<Chart> charts = chartService.list(Wrappers.<Chart>lambdaQuery().eq(Chart::getUserid, user.getId()));
        return ResultUtils.success(charts);
    }

    /**
     * 根据一个图表的id获取图表，以及相似度最高的图表
     * @param request
     * @param chartId
     * @return
     */
    @GetMapping("/chartAndSimilarity")
    public BaseResponse getChartAndSimilarity(HttpServletRequest request,Long chartId){
        User user = userService.getLoginUser(request);
//        User user = new User();
        if(user==null){
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR,"请先登录");
        }
        user.setId(1L);

        Chart chart = chartService.getById(chartId);
        if(chart==null){
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR,"图表不存在");
        }

        String chartHeader = chart.getChartheader();
        try {

            SearchResponse<EsChart> similarity = esChartService.getSimilarity(chartHeader, user.getId());
            HashMap<String, Object> returnMap = new HashMap<>();
            returnMap.put("chart",chart);
            if(similarity.hits().hits().size()==0) {
                returnMap.put("similarity", null);
            }else{
                returnMap.put("similarity", similarity.hits().hits().get(0).source());
            }
            return ResultUtils.success(returnMap);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }







}
