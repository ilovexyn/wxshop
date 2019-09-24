package com.zhuoyuan.wxshop.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.GoodDetailDto;
import com.zhuoyuan.wxshop.model.Goods;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
public interface IGoodsService extends IService<Goods> {

    Page<Goods>  getList(int current,int size,String name);

    GoodDetailDto getById(Long id);

}
