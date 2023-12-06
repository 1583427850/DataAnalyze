package com.bigdata.dataanalyze.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {
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
