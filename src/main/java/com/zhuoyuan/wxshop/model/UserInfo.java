package com.zhuoyuan.wxshop.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
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
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String account;
    private String code;
    @TableField("openId")
    private String openId;
    @TableField("nickName")
    private String nickName;
    private String gender;
    private String city;
    private String province;
    private String country;
    @TableField("avatarUrl")
    private String avatarUrl;
    @TableField("unionId")
    private String unionId;
    private Date ct;
    private Date ut;


    @Override
    public String toString() {
        return "UserInfo{" +
        ", id=" + id +
        ", account=" + account +
        ", code=" + code +
        ", openId=" + openId +
        ", nickName=" + nickName +
        ", gender=" + gender +
        ", city=" + city +
        ", province=" + province +
        ", country=" + country +
        ", avatarUrl=" + avatarUrl +
        ", unionId=" + unionId +
        ", ct=" + ct +
        ", ut=" + ut +
        "}";
    }
}
