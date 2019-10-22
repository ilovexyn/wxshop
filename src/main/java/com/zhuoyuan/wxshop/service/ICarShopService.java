package com.zhuoyuan.wxshop.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.CarShopOrderDto;
import com.zhuoyuan.wxshop.model.CarShop;
import com.baomidou.mybatisplus.service.IService;
import com.zhuoyuan.wxshop.request.CarShopPageRequest;
import com.zhuoyuan.wxshop.request.PageRequest;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-25
 */
public interface ICarShopService extends IService<CarShop> {

    CarShopPageRequest getCarShop(Integer current, Integer size, String openid);

    void addCarShop(CarShop carShop) throws  Exception;

    CarShopPageRequest carShopOrder(Integer current, Integer size, String openid);
}
