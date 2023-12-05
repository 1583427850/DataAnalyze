package com.bigdata.dataanalyze.service.Impl;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RocketMQService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;




}
