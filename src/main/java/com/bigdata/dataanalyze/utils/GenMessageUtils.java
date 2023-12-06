package com.bigdata.dataanalyze.utils;

import com.bigdata.dataanalyze.entity.Chart;
import com.bigdata.dataanalyze.entity.GptMessage;
import com.bigdata.dataanalyze.entity.MqMessageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class GenMessageUtils {

    static String prompt = """
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

    public static MqMessageEntity genMessage(Chart chart, MultipartFile file,String chartHeader) {
        ArrayList<GptMessage> messages = new ArrayList<>();
        GptMessage system = new GptMessage("system", prompt);
        messages.add(system);
        String answer = "分析需求:".concat("\n").concat(chart.getGoal()).concat("\n").concat("原始数据：").concat("\n").concat(chart.getChartdata());
        messages.add(new GptMessage("user", answer));
        return new MqMessageEntity(messages, chart.getId(), 0,chart.getChartName(),chartHeader);
    }
}
