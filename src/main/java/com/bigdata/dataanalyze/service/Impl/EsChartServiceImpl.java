package com.bigdata.dataanalyze.service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import com.bigdata.dataanalyze.entity.EsChart;
import com.bigdata.dataanalyze.service.EsChartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EsChartServiceImpl implements EsChartService {


    @Autowired
    private ElasticsearchClient client;

    @Override
    public boolean saveOne(EsChart esChart) {
        try {
            CreateResponse save = client.create(i -> i
                    .index("chart")
                    .id(String.valueOf(esChart.getChartId()))
                    .document(esChart));
        } catch (IOException e) {
            log.error("保存出现错误:{}",esChart);
            return false;
        }
        return true;
    }






}
