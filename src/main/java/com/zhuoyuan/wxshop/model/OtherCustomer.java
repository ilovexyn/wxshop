package com.zhuoyuan.wxshop.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * InnoDB free: 8192 kB
 * </p>
 *
 * @author Wangjie
 * @since 2020-01-04
 */
@Data
public class OtherCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String address;
    private String mobile;
    private String grade;
    private String relation;
}
