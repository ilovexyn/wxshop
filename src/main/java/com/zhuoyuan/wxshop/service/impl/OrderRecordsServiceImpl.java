package com.zhuoyuan.wxshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.GetOrderDetailRequest;
import com.zhuoyuan.wxshop.dto.GetOrderRequest;
import com.zhuoyuan.wxshop.dto.OrderRequest;
import com.zhuoyuan.wxshop.model.Goods;
import com.zhuoyuan.wxshop.model.OrderRecords;
import com.zhuoyuan.wxshop.mapper.OrderRecordsMapper;
import com.zhuoyuan.wxshop.model.OrderRecordsDetails;
import com.zhuoyuan.wxshop.model.UserAddress;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.status.GoodsStatus;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderRecordsServiceImpl extends ServiceImpl<OrderRecordsMapper, OrderRecords> implements IOrderRecordsService {

    @Autowired
    IOrderRecordsService orderRecordsService;
    @Autowired
    IOrderRecordsDetailsService orderRecordsDetailsService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    OrderRecordsMapper orderRecordsMapper;
    @Autowired
    MailService mailService;
    @Autowired
    IUserAddressService userAddressService;

    @Override
    @Transactional
    public Result save(OrderRequest orderRequest) throws Exception {
        log.info("OrderRecordsServiceImpl -- save:"+JSONObject.toJSONString(orderRequest));
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
        log.info("OrderRecordsServiceImpl -- getOrder:"+"openid:"+openid+",state:"+state);
        Page<GetOrderRequest> getOrderRequestPage = new Page<GetOrderRequest>(current, size);
        List<GetOrderRequest> getOrderRequestList = new ArrayList<>();

        EntityWrapper<OrderRecords> orderRecordsEntityWrapper = new EntityWrapper<>();
        if(0 != state){
            orderRecordsEntityWrapper.eq("state",state);
        }
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
            getOrderRequest.setState(orderRecords.getState());
            getOrderRequest.setOrdercode(orderRecords.getOrdercode());
            getOrderRequest.setId(orderRecords.getId());
            getOrderRequest.setSumPrice(orderRecords.getSumPrice());
            getOrderRequest.setGoodsCount(goodsCount);
            getOrderRequest.setGetOrderDetailRequestList(getOrderDetailRequestList);
            getOrderRequestList.add(getOrderRequest);
        }

        getOrderRequestPage.setRecords(getOrderRequestList);
        return Result.success(getOrderRequestPage);
    }

    @Override
    public Result updateOrder(OrderRecords orderRecords) throws Exception {
        log.info("OrderRecordsServiceImpl -- updateOrder:"+JSONObject.toJSONString(orderRecords));
        OrderRecords torderRecords = orderRecordsService.selectById(orderRecords.getId());
        String content = this.getOrderInfo(torderRecords);
        if(orderRecords.getState() == 1){//支付完成 变成 代发货
            orderRecords.setState(2);
            mailService.sendHtmlMail("13718478366@163.com", "支付完成等待发货->订单号："+torderRecords.getOrdercode(), content);
        }else if(orderRecords.getState() == 3){//待收货变为收货 订单完成
            orderRecords.setState(4);
        }else if(orderRecords.getState() == 2){//待收货变为收货 订单完成
            //发送邮件提醒发货
            mailService.sendHtmlMail("13718478366@163.com", "提醒发货->订单号："+torderRecords.getOrdercode(), content);

        }
        orderRecords.setUt(new Date());
        orderRecordsService.updateById(orderRecords);
        return Result.success();
    }

    private String getOrderInfo(OrderRecords torderRecords){
        //拼装订单信息
        String content = "";
        EntityWrapper<OrderRecordsDetails> orderRecordsDetailsEntityWrapper = new EntityWrapper<>();
        orderRecordsDetailsEntityWrapper.eq("order_id",torderRecords.getId());
        List<OrderRecordsDetails> orderRecordsDetailsList = orderRecordsDetailsService.selectList(orderRecordsDetailsEntityWrapper);
        for(OrderRecordsDetails orderRecordsDetails:orderRecordsDetailsList){
            Goods goods = goodsService.selectById(orderRecordsDetails.getGoodsId());
            content = content +"商品："+goods.getName()+"；数量："+orderRecordsDetails.getNum();
        }
        String addressInfo = "";
        UserAddress userAddress = userAddressService.selectById(torderRecords.getAddressId());
        addressInfo = "  姓名："+userAddress.getName()+"手机号："+userAddress.getMobile()+"地址:"+userAddress.getAddressInfo();
        content = content+addressInfo;
        String url ="https://www.1000000tao.com/api/wxserve/order/updateOrderByMail?orderId=\"+torderRecords.getId()" ;
        content = content+"操作:"+"<a href='"+url+"'";
        return content;
    }

    @Override
    public void updateOrderByMail(Long orderId) {
        OrderRecords torderRecords = orderRecordsService.selectById(orderId);
        torderRecords.setState(3);
        orderRecordsService.updateById(torderRecords);
    }
}
