package com.bigdata.dataanalyze.service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.bigdata.dataanalyze.entity.EsChart;
import com.bigdata.dataanalyze.service.EsChartService;
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

    /**
     * 获取和某一个表头相似的其他表格
     *
     * @param chartHeader
     * @param userId
     * @return
     */
    @Override
    public SearchResponse<EsChart> getSimilarity(String chartHeader, Long userId) throws IOException {
        SearchResponse<EsChart> response = client.search(s -> s
                        .index("chart")
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .match(t -> t
                                                        .field("chartHeader")
                                                        .query(chartHeader)
                                                )
                                        )
                                        .mustNot(m -> m
                                                .term(t -> t
                                                        .field("userId")
                                                        .value(userId)
                                                )
                                        )
                                )
                        ),
                EsChart.class
        );
        return response;
    }


}
