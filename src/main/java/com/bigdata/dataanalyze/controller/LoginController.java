package com.bigdata.dataanalyze.controller;

import com.bigdata.dataanalyze.common.BaseResponse;
import com.bigdata.dataanalyze.common.dto.LoginDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginDto dto, HttpServletRequest request){
        return null;
    }

}
