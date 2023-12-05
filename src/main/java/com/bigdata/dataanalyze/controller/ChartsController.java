package com.bigdata.dataanalyze.controller;

import com.bigdata.dataanalyze.common.BaseResponse;
import com.bigdata.dataanalyze.common.ErrorCode;
import com.bigdata.dataanalyze.common.ResultUtils;
import com.bigdata.dataanalyze.common.dto.AnalyzeDto;
import com.bigdata.dataanalyze.entity.User;
import com.bigdata.dataanalyze.service.ChartService;
import com.bigdata.dataanalyze.service.UserService;
import com.bigdata.dataanalyze.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChartsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChartService chartService;

    @PostMapping("/gen")
    public BaseResponse analyze(@RequestPart("file")MultipartFile file,String goal,String chartName,HttpServletRequest request){

        if(goal==null || chartName==null || file==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"参数错误");
        }

        User user = new User();
        user.setId(1L);
//        User user = userService.getLoginUser(request);
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
        User user = userService.getLoginUser(request);
        if(user==null){
            return ResultUtils.error(ErrorCode.NO_AUTH_ERROR,"请先登录");
        }
        return null;
    }

//    @PostMapping("/test")
//    public BaseResponse analyze(@RequestPart("file")MultipartFile file,
//                                HttpServletRequest request){
//
//        if( file==null){
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"参数错误");
//        }
//        String result = ExcelUtils.excelToCsv(file);
//        System.out.println(result);
//        return null;
//    }





}
