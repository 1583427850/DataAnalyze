package com.bigdata.dataanalyze.MqClient.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.bigdata.dataanalyze.common.constant.MqConstant;
import com.bigdata.dataanalyze.entity.Chart;
import com.bigdata.dataanalyze.entity.EsChart;
import com.bigdata.dataanalyze.entity.GptMessage;
import com.bigdata.dataanalyze.entity.MqMessageEntity;
import com.bigdata.dataanalyze.service.ChartService;
import com.bigdata.dataanalyze.service.EsChartService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author lin
 */
@RocketMQMessageListener(topic = MqConstant.chartTopic, consumerGroup = "chartConsumer")
@Slf4j
@Component
public class RocketMqListening implements RocketMQListener<String> {

    @Autowired
    private ChartService chartService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private EsChartService esChartService;

    private Integer CHART_MQ_MAX_RETRY_COUNT = 3;


    @Override
    public void onMessage(String message) {
//        发送到gpt服务进行审核
        MqMessageEntity messageEntity = JSONUtil.toBean(message, MqMessageEntity.class);
        log.info("接收到消息：{}", messageEntity);
        HttpResponse response = HttpRequest.post("http://localhost:7531/gptChatNoStream")
                .header("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsImV4cCI6MTcwMTg3Mjk4MSwiaWF0IjoxNzAxNzg2NTgxfQ.aG4pfsLUzhFnhdFp6rwh6y-VVmNz3SdTBhUrrXAXX1g" )
                .body(JSONUtil.toJsonStr(messageEntity.getMessagews())).execute();
        GptResponseEntity entity = JSONUtil.toBean(response.body(), GptResponseEntity.class);
        GptMessage bean = JSONUtil.toBean(entity.getData(), GptMessage.class);
        log.info("收到的回复为：{}",bean.getContent());


        String[] strings = analyzeResponse(bean.getContent(),messageEntity);

        if(strings==null){
            return;
        }

//        更新数据库数据
        chartService.updateChart(messageEntity.getChartId(),strings[1],strings[2]);
//        在继续保存到es里面
        boolean result = saveChartToEs(messageEntity,strings);
        if(result){
            log.info("保存成功到es");
        }

    }

    private boolean saveChartToEs(MqMessageEntity messageEntity, String strings[]) {
        EsChart esChart = new EsChart();
        esChart.setChartId(messageEntity.getChartId());
        esChart.setChartContent(strings[1]);
        esChart.setChartName(messageEntity.getChartName());
        esChart.setChartHeader(messageEntity.getChartHeader());
        return esChartService.saveOne(esChart);
    }

    public String[] analyzeResponse(String message,MqMessageEntity entity) {
        if (message == null) {
            return null;
        }
        String[] split = message.split("【【【【【");
        if(split.length!=3 && entity.getRetryCount()<=CHART_MQ_MAX_RETRY_COUNT){
            entity.setRetryCount(entity.getRetryCount()+1);
            rocketMQTemplate.syncSend(MqConstant.chartTopic, JSONUtil.toJsonStr(entity));
            return null;
        }
        return split;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class GptResponseEntity{
        String code;
        String data;
        String message;
    }
}

