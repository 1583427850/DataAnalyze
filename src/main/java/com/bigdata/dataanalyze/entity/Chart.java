package com.bigdata.dataanalyze.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 图表信息表
 * @TableName chart
 */
@TableName(value ="chart")
@Data
public class Chart implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表信息
     */
    private String chartdata;

    /**
     * 图表类型
     */
    private String charttype;

    /**
     * 生成的图表信息
     */
    private String genchart;

    /**
     * 生成的分析结论
     */
    private String getresult;

    /**
     * 创建图标用户 id
     */
    private Long userid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Chart other = (Chart) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoal() == null ? other.getGoal() == null : this.getGoal().equals(other.getGoal()))
            && (this.getChartdata() == null ? other.getChartdata() == null : this.getChartdata().equals(other.getChartdata()))
            && (this.getCharttype() == null ? other.getCharttype() == null : this.getCharttype().equals(other.getCharttype()))
            && (this.getGenchart() == null ? other.getGenchart() == null : this.getGenchart().equals(other.getGenchart()))
            && (this.getGetresult() == null ? other.getGetresult() == null : this.getGetresult().equals(other.getGetresult()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoal() == null) ? 0 : getGoal().hashCode());
        result = prime * result + ((getChartdata() == null) ? 0 : getChartdata().hashCode());
        result = prime * result + ((getCharttype() == null) ? 0 : getCharttype().hashCode());
        result = prime * result + ((getGenchart() == null) ? 0 : getGenchart().hashCode());
        result = prime * result + ((getGetresult() == null) ? 0 : getGetresult().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goal=").append(goal);
        sb.append(", chartdata=").append(chartdata);
        sb.append(", charttype=").append(charttype);
        sb.append(", genchart=").append(genchart);
        sb.append(", getresult=").append(getresult);
        sb.append(", userid=").append(userid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}