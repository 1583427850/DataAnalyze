package com.bigdata.dataanalyze.service;

import com.bigdata.dataanalyze.common.dto.AnalyzeDto;
import com.bigdata.dataanalyze.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bigdata.dataanalyze.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
* @author lin
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2023-12-05 15:42:20
*/
public interface ChartService extends IService<Chart> {

    /**
     * 生成对应表格信息
     * @param dto
     * @param user
     */
    boolean genChart(String goal,String chartName, User user, MultipartFile file);

    boolean updateChart(Long chartId,String genChart,String getResult );
}
