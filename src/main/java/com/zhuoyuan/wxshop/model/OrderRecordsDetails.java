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
public class OrderRecordsDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long goodsId;
    private Integer num;
    private BigDecimal price;
    private Date ct;
    private Date ut;



    @Override
    public String toString() {
        return "OrderRecordsDetails{" +
        ", id=" + id +
        ", orderId=" + orderId +
        ", goodsId=" + goodsId +
        ", num=" + num +
        ", price=" + price +
        ", ct=" + ct +
        ", ut=" + ut +
        "}";
    }
}
