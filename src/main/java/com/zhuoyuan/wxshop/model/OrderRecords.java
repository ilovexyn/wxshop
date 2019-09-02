package com.zhuoyuan.wxshop.model;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 9216 kB
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@Data
public class OrderRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 买家id
     */
    private String openid;
    /**
     * 订单状态
     */
    private Integer state;
    /**
     * 客户地址码
     */
    private String addressId;
    /**
     * 快递码
     */
    private String expressCode;
    /**
     * 订单类型：1-客户购买 2-经纪人进货 3-经纪人买经纪人
     */
    private Integer type;
    private Date ct;
    private Date ut;
    /**
     * 订单号()
     */
    private String ordercode;
    /**
     * 订单总价
     */
    private BigDecimal sumPrice;

    private String appraise;

    @Override
    public String toString() {
        return "OrderRecords{" +
        ", id=" + id +
        ", openid=" + openid +
        ", state=" + state +
        ", addressId=" + addressId +
        ", expressCode=" + expressCode +
        ", type=" + type +
        ", ct=" + ct +
        ", ut=" + ut +
        ", ordercode=" + ordercode +
        ", sumPrice=" + sumPrice +
        "}";
    }
}
