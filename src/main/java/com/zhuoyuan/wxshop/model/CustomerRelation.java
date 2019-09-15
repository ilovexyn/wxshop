package com.zhuoyuan.wxshop.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 9216 kB
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-09
 */
public class CustomerRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String hCustomer;
    private String lCustomer;
    private Date ct;
    private Date ut;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gethCustomer() {
        return hCustomer;
    }

    public void sethCustomer(String hCustomer) {
        this.hCustomer = hCustomer;
    }

    public String getlCustomer() {
        return lCustomer;
    }

    public void setlCustomer(String lCustomer) {
        this.lCustomer = lCustomer;
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
        return "CustomerRelation{" +
        ", id=" + id +
        ", hCustomer=" + hCustomer +
        ", lCustomer=" + lCustomer +
        ", ct=" + ct +
        ", ut=" + ut +
        "}";
    }
}
