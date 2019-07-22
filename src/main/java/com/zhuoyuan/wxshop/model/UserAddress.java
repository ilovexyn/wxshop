package com.zhuoyuan.wxshop.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 9216 kB
 * </p>
 *
 * @author Wangjie
 * @since 2019-07-08
 */
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String addressInfo;
    private String mobile;
    private String openid;
    private Date ct;
    private Date ut;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    public Date getUt() {
        return ut;
    }

    public void setUt(Date ut) {
        this.ut = ut;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
        ", id=" + id +
        ", addressInfo=" + addressInfo +
        ", mobile=" + mobile +
        ", openid=" + openid +
        ", ct=" + ct +
        ", ut=" + ut +
        "}";
    }
}
