package com.zhuoyuan.wxshop.model;

import java.util.Date;
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
 * @since 2019-07-08
 */
@Data
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String mobile;
    private String city;
    private String addressDetail;
    private String addressInfo;
    private String openid;
    private int state;
    private Date ct;
    private Date ut;

}
