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
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 货品名称
     */
    private String name;
    /**
     * 货品价格
     */
    private BigDecimal price;
    /**
     * 所属地区
     */
    private String place;
    private Date ct;
    private Date ut;
    /**
     * 商品图片链接
     */
    private String imageurl;
    /**
     * 商品介绍
     */
    private String content;
    /**
     * 1-有效 2-失效
     */
    private Integer state;



    @Override
    public String toString() {
        return "Goods{" +
        ", id=" + id +
        ", name=" + name +
        ", price=" + price +
        ", place=" + place +
        ", ct=" + ct +
        ", ut=" + ut +
        ", imageurl=" + imageurl +
        ", content=" + content +
        ", state=" + state +
        "}";
    }
}
