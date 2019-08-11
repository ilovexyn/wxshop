package com.zhuoyuan.wxshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.GetOrderDetailRequest;
import com.zhuoyuan.wxshop.dto.GetOrderRequest;
import com.zhuoyuan.wxshop.dto.OrderRequest;
import com.zhuoyuan.wxshop.model.Goods;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.zhuoyuan.wxshop.mapper.OrderRecordsMapper;
import com.zhuoyuan.wxshop.model.OrderRecordsDetails;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IGoodsService;
import com.zhuoyuan.wxshop.service.IOrderRecordsDetailsService;
import com.zhuoyuan.wxshop.service.IOrderRecordsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.status.GoodsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@Service
public class OrderRecordsServiceImpl extends ServiceImpl<OrderRecordsMapper, OrderRecords> implements IOrderRecordsService {

    @Autowired
    IOrderRecordsService orderRecordsService;
    @Autowired
    IOrderRecordsDetailsService orderRecordsDetailsService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    OrderRecordsMapper orderRecordsMapper;

    @Override
    @Transactional
    public Result save(OrderRequest orderRequest) throws Exception {
        String orderCode = String.valueOf( System.currentTimeMillis())+orderRequest.getOpenid();
        Goods goods = goodsService.selectById(orderRequest.getGoodsId());
        if(goods == null){
            throw new Exception("没有此商品！");
        }

        BigDecimal b1 = new BigDecimal(goods.getPrice().toString());
        BigDecimal b2 = new BigDecimal(orderRequest.getNum().toString());
        BigDecimal sum = b1.multiply(b2);

        OrderRecords orderRecords = new OrderRecords();
        orderRecords.setAddressId("1");
        orderRecords.setOpenid(orderRequest.getOpenid());
        orderRecords.setState(GoodsStatus.order);
        orderRecords.setSumPrice(sum);
        orderRecords.setOrdercode(orderCode);
        orderRecords.setCt(new Date());
        orderRecords.setUt(new Date());

        orderRecordsService.insert(orderRecords);

        OrderRecordsDetails orderRecordsDetails = new OrderRecordsDetails();
        orderRecordsDetails.setCt(new Date());
        orderRecordsDetails.setGoodsId(goods.getId());
        orderRecordsDetails.setNum(orderRequest.getNum());
        orderRecordsDetails.setOrderId(orderRecords.getId());
        orderRecordsDetails.setPrice(goods.getPrice());
        orderRecordsDetails.setUt(new Date());
        orderRecordsDetailsService.insert(orderRecordsDetails);

        return Result.success();
    }

    @Override
    public Result getOrder(int current, int size, String openid,int state) {
        Page<GetOrderRequest> getOrderRequestPage = new Page<GetOrderRequest>(current, size);
        List<GetOrderRequest> getOrderRequestList = new ArrayList<>();

        EntityWrapper<OrderRecords> orderRecordsEntityWrapper = new EntityWrapper<>();
        orderRecordsEntityWrapper.eq("state",state);
        orderRecordsEntityWrapper.eq("openid",openid);

        List<OrderRecords> orderRequestList = orderRecordsMapper.selectPage(getOrderRequestPage,orderRecordsEntityWrapper);

        for(OrderRecords orderRecords:orderRequestList){
            GetOrderRequest getOrderRequest = new GetOrderRequest();
            int goodsCount = 0;
            EntityWrapper<OrderRecordsDetails> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("order_id",orderRecords.getId());
            List<OrderRecordsDetails> orderRecordsDetails = orderRecordsDetailsService.selectList(entityWrapper);
            List<GetOrderDetailRequest> getOrderDetailRequestList = new ArrayList<>();
            for(OrderRecordsDetails torderRecordsDetails:orderRecordsDetails){
                GetOrderDetailRequest getOrderDetailRequest = new GetOrderDetailRequest();
                getOrderDetailRequest.setCount(torderRecordsDetails.getNum());
                Goods goods =goodsService.selectById(torderRecordsDetails.getGoodsId());
                getOrderDetailRequest.setName(goods.getName());
                getOrderDetailRequest.setImageUrl(goods.getImageurl());
                getOrderDetailRequest.setPrice(goods.getPrice());
                getOrderDetailRequest.setPrice(goods.getPrice());
                goodsCount = goodsCount+torderRecordsDetails.getNum();
                getOrderDetailRequestList.add(getOrderDetailRequest);
            }
            getOrderRequest.setOrdercode(orderRecords.getOrdercode());
            getOrderRequest.setId(orderRecords.getId());
            getOrderRequest.setGoodsCount(goodsCount);
            getOrderRequest.setGetOrderDetailRequestList(getOrderDetailRequestList);
            getOrderRequestList.add(getOrderRequest);
        }

        getOrderRequestPage.setRecords(getOrderRequestList);
        return Result.success(getOrderRequestPage);
    }
}
