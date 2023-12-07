import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.bigdata.dataanalyze.DataAnalyzeApplication;
import com.bigdata.dataanalyze.controller.ChartsController;
import com.bigdata.dataanalyze.entity.EsChart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.RandomAccessFile;

@SpringBootTest(classes = DataAnalyzeApplication.class)
class Test2 {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void test1() throws IOException {

        EsChart esChart = new EsChart();
        esChart.setChartId(3L);
        esChart.setUserId(2L);
        esChart.setChartHeader("数量 销量 价格 总类 人数");
        esChart.setChartName("销量3");
        esChart.setChartContent("数据3");


        CreateResponse chart = elasticsearchClient.create(i ->
                i.index("chart")
                        .id(String.valueOf(esChart.getChartId()))
                        .document(esChart)
        );

    }

    @Test
    public void test2() throws IOException {
        SearchResponse<EsChart> response = elasticsearchClient.search(s -> s
                        .index("chart")
                        .query(q -> q
                                .match(t -> t
                                        .field("chartHeader")
                                        .query("销量")
                                )
                        ),
                EsChart.class
        );
        System.out.println(response);
    }

    @Test
    public void test3() throws IOException {
        GetResponse<EsChart> response = elasticsearchClient.get(g -> g
                        .index("chart")
                        .id("1"),
                EsChart.class
        );
        if(response.found()){
            EsChart chart = response.source();
            System.out.println(chart);
        }
    }


    @Test
    public void test4() throws IOException {
        Long userId = 1L;
        SearchResponse<EsChart> response = elasticsearchClient.search(s -> s
                        .index("chart")
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .match(t -> t
                                                        .field("chartHeader")
                                                        .query("销量")
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
        System.out.println(response);
    }

    @Test
    public void test5(){
        ChartsController chartsController = new ChartsController();
    }



}