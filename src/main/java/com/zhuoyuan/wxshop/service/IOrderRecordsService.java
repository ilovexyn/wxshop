package com.zhuoyuan.wxshop.service;

import com.zhuoyuan.wxshop.dto.OrderRequest;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.baomidou.mybatisplus.service.IService;
import com.zhuoyuan.wxshop.request.CreateCarShopOrderRequest;
import com.zhuoyuan.wxshop.request.Result;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
public interface IOrderRecordsService extends IService<OrderRecords> {

    Result save(OrderRequest orderRequest) throws Exception;

    Result getOrder(int current,int size,String openid,int state);

    Result updateOrder(OrderRecords orderRecords) throws Exception;

    void updateOrderByMail(Long orderId);

    void createCarShopOrder ( CreateCarShopOrderRequest carShopPageRequest) throws  Exception;
}
