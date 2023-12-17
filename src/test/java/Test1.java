import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Test1 {

    public void test0(){
        
    }




    public void test1() {


        String prompt = """
                你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：
                分析需求：
                {数据分析的需求或者目标}
                原始数据：
                {csv格式的原始数据，用,作为分隔符}
                请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）
                【【【【【
                {前端 Echarts V5 的 option 配置对象的json格式代码，合理地将数据进行可视化，不要生成任何多余的内容，比如注释}
                【【【【【
                {明确的数据分析结论、越详细越好，不要生成多余的注释}
                【【【【【

                                    """;
        System.out.println(prompt);
    }


    public void test2() {
    }

    @Data
    class ExcelEntity{
        @ExcelProperty("电脑型号")
        String Model;
        @ExcelProperty("开始时间")
        String startTime;
        @ExcelProperty("结束时间")
        String endTime;
        @ExcelProperty("开始排名")
        String startRank;
        @ExcelProperty("结束排名")
        String endRank;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Ranking {
        String channel;
        String endDate;
        String rankType;
        String startDate;
        Integer topNum;
        Integer trade1;
        Integer trade2;
        String type;

        public Ranking(String sd, String ed, Integer topNum) {
            this.startDate = sd;
            this.endDate = ed;
            this.topNum = topNum;
            this.channel = "index";
            this.rankType = "daily";
            this.trade1 = 12;
            this.trade2 = 1026;
            this.type = "brand";

        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class ResultEntity{
        private Integer status;
        private ResponseDataEntity data;

    }

    @Data
    class ResponseDataEntity{
        String startDate;
        String endDate;
        List<ResponseRank> data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class ResponseRank{
        @ExcelIgnore
        private Long entityId;
        @ExcelProperty("手机型号")
        private String entityName;
        @ExcelIgnore
        private Integer value;
        @ExcelProperty("排名")
        private Integer rank;
        @ExcelIgnore
        private String trend;
        @ExcelIgnore
        private Double occupy;
    }
}
