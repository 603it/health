package com.halo.health.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 *
 * @TableName analysis
 */
@TableName(value ="analysis")
@Data
public class Analysis implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 血氧
     */
    private Double bloodOxygen;

    /**
     * 心率
     */
    private Double heartRate;

    /**
     * 温度
     */
    private Double temperature;

    /**
     * 建议
     */
    private String proposal;

    /**
     * 时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}