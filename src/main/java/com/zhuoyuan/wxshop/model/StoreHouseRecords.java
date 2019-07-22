package com.zhuoyuan.wxshop.model;

import com.baomidou.mybatisplus.enums.IdType;
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
public class StoreHouseRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 货品id
     */
    private Long goodsId;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 仓库id
     */
    private Long storeId;
    /**
     * 数量
     */
    private Integer count;
    private Date ct;
    private Date ut;
    private String ordercode;



    @Override
    public String toString() {
        return "StoreHouseRecords{" +
        ", id=" + id +
        ", goodsId=" + goodsId +
        ", orderId=" + orderId +
        ", storeId=" + storeId +
        ", count=" + count +
        ", ct=" + ct +
        ", ut=" + ut +
        ", ordercode=" + ordercode +
        "}";
    }
}
