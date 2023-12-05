package com.bigdata.dataanalyze.MqClient.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.bigdata.dataanalyze.common.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author lin
 */
@RocketMQMessageListener(topic = MqConstant.chartTopic, consumerGroup = "chartConsumer")
@Slf4j
@Component
public class RocketMqListening implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
//        发送到gpt服务进行审核
        log.info("接收到消息：{}", message);
        HttpRequest body = HttpRequest.post("http://localhost/api/gpt/gptChatNoStream").header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsImV4cCI6MTcwMTg3Mjk4MSwiaWF0IjoxNzAxNzg2NTgxfQ.aG4pfsLUzhFnhdFp6rwh6y-VVmNz3SdTBhUrrXAXX1g").body(message);
        HttpResponse response = body.execute();
        log.info("回复为:{}",response.body());

    }
}
