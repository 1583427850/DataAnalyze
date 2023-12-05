package com.bigdata.dataanalyze.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bigdata.dataanalyze.entity.Chart;
import com.bigdata.dataanalyze.mapper.ChartMapper;
import com.bigdata.dataanalyze.service.ChartService;
import org.springframework.stereotype.Service;

/**
* @author lin
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-12-05 15:42:20
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




