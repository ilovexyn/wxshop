package com.zhuoyuan.wxshop.service;

import java.util.List;

/**
 * @program: wxshop
 * @description: ${description}
 * @author: Mr.Wang
 * @create: 2019-09-16 15:51
 **/
public interface IGetimageService {

    List<String >  getByGoodsId(Long id);
}
