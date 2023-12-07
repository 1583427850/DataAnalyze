package com.bigdata.dataanalyze.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.bigdata.dataanalyze.entity.EsChart;

import java.io.IOException;

public interface EsChartService {

    /**
     * 插入一条数据到es中
     * @param esChart
     * @return
     */
    public boolean saveOne(EsChart esChart);

    /**
     * 获取和某一个表头相似的其他表格
     *
     * @param chartHeader
     * @return
     */
    public SearchResponse<EsChart> getSimilarity(String chartHeader, Long userId) throws IOException;
}
