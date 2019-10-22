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
 * @since 2019-09-25
 */
@Data
public class CarShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String openId;
    private Long goodId;
    private Integer num;
    private Integer state;
    private Long orderId;
    private Date ct;
    private Date ut;


    @Override
    public String toString() {
        return "CarShop{" +
        ", id=" + id +
        ", openId=" + openId +
        ", goodId=" + goodId +
        ", num=" + num +
        ", state=" + state +
        ", ct=" + ct +
        ", ut=" + ut +
        "}";
    }
}
