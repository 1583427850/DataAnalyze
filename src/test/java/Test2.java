import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.bigdata.dataanalyze.DataAnalyzeApplication;
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
        esChart.setChartId(1L);
        esChart.setUserId(1L);
        esChart.setChartHeader("数量 销量 价格");
        esChart.setChartName("销量");
        esChart.setChartContent("数据");


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


}