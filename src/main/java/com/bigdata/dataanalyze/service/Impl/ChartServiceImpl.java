package com.bigdata.dataanalyze.service.Impl;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bigdata.dataanalyze.common.constant.MqConstant;
import com.bigdata.dataanalyze.common.dto.AnalyzeDto;
import com.bigdata.dataanalyze.entity.Chart;
import com.bigdata.dataanalyze.entity.GptMessage;
import com.bigdata.dataanalyze.entity.User;
import com.bigdata.dataanalyze.mapper.ChartMapper;
import com.bigdata.dataanalyze.service.ChartService;
import com.bigdata.dataanalyze.utils.ExcelUtils;
import com.bigdata.dataanalyze.utils.GenMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
* @author lin
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-12-05 15:42:20
*/
@Service
@Slf4j
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    /**
     * 生成对应表格信息
     *
     * @param
     * @param user
     */
    @Override
    public boolean genChart(String goal,String chartName, User user, MultipartFile file) {

//        根据file的excel文件，将里面的所有数据提取出来
        String fileMessage = ExcelUtils.excelToCsv(file);
        log.info("文件内容为：{}",fileMessage);
        Chart chart = new Chart();
        chart.setGoal(goal);
        chart.setChartdata(fileMessage);
        chart.setUserid(user.getId());
        chart.setCreatetime(new Date());
        chart.setUpdatetime(new Date());
//       先保存到数据库
        boolean save = this.save(chart);

//        将生成消息的信息发送到rocketmq中
        List<GptMessage> messages = GenMessageUtils.genMessage(chart, file);
        log.info("问题为:{}",JSON.toJSON(messages));
        rocketMQTemplate.syncSend(MqConstant.chartTopic, JSON.toJSON(messages));
        log.info("发送消息成功");
        return true;
    }


}




