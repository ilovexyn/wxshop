package com.zhuoyuan.wxshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.CarShopDetailDto;
import com.zhuoyuan.wxshop.dto.GetOrderDetailRequest;
import com.zhuoyuan.wxshop.dto.GetOrderRequest;
import com.zhuoyuan.wxshop.dto.OrderRequest;
import com.zhuoyuan.wxshop.mapper.CarShopMapper;
import com.zhuoyuan.wxshop.model.*;
import com.zhuoyuan.wxshop.mapper.OrderRecordsMapper;
import com.zhuoyuan.wxshop.request.CreateCarShopOrderRequest;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.status.GoodsStatus;
import com.zhuoyuan.wxshop.utils.BusinessIdUtil;
import com.zhuoyuan.wxshop.utils.SnowFlake;
import com.zhuoyuan.wxshop.utils.ossService.OssUtil;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private IOrderRecordsService orderRecordsService;
    @Autowired
    private IOrderRecordsDetailsService orderRecordsDetailsService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    private OrderRecordsMapper orderRecordsMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private CarShopMapper carShopMapper;
    @Value("${spring.mail.username}")
    private String mailUrl;
    @Resource
    private IStoreHouseService storeHouseService;

    @Override
    @Transactional
    public Result save(OrderRequest orderRequest) throws Exception {
        log.info("OrderRecordsServiceImpl -- save:"+JSONObject.toJSONString(orderRequest));
        SnowFlake snowFlake = new SnowFlake(2, 9);
        String orderCode = String.valueOf(snowFlake.nextId());
        Goods goods = goodsService.selectById(orderRequest.getGoodsId());

        if(goods == null){
            throw new Exception("没有此商品！");
        }

        BigDecimal b1 = new BigDecimal(goods.getPrice().toString());
        BigDecimal b2 = new BigDecimal(orderRequest.getNum().toString());
        BigDecimal sum = b1.multiply(b2);

        OrderRecords orderRecords = new OrderRecords();
        orderRecords.setAddressId(String.valueOf(orderRequest.getAddressId()));
        orderRecords.setOpenid(orderRequest.getOpenid());
        orderRecords.setState(GoodsStatus.pay);
        orderRecords.setSumPrice(sum);
        orderRecords.setOrdercode(orderCode);
        orderRecords.setCt(new Date());
        orderRecords.setUt(new Date());
        orderRecords.setType(2);

        orderRecordsService.insert(orderRecords);

        OrderRecordsDetails orderRecordsDetails = new OrderRecordsDetails();
        orderRecordsDetails.setCt(new Date());
        orderRecordsDetails.setGoodsId(goods.getId());
        orderRecordsDetails.setNum(orderRequest.getNum());
        orderRecordsDetails.setOrderId(orderRecords.getId());
        orderRecordsDetails.setPrice(goods.getPrice());
        orderRecordsDetails.setUt(new Date());
        orderRecordsDetailsService.insert(orderRecordsDetails);
        String content = this.getOrderInfo(orderRecords);
        mailService.sendHtmlMail(mailUrl, "支付完成等待发货->订单号："+orderCode, content);
        //更新积分
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
                //getOrderDetailRequest.setImageUrl(ossUtil.getURL(goods.getImageurl()).toString().replace("http","https"));
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
            mailService.sendHtmlMail(mailUrl, "支付完成等待发货->订单号："+torderRecords.getOrdercode(), content);
        }else if(orderRecords.getState() == 3){//待收货变为收货 订单完成
            orderRecords.setState(4);
        }else if(orderRecords.getState() == 2){//待收货变为收货 订单完成
            //发送邮件提醒发货
            mailService.sendHtmlMail(mailUrl, "提醒发货->订单号："+torderRecords.getOrdercode(), content);

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

        return content;
    }

    @Override
    public void updateOrderByMail(Long orderId) {
        OrderRecords torderRecords = orderRecordsService.selectById(orderId);
        torderRecords.setState(3);
        orderRecordsService.updateById(torderRecords);
    }

    @Override
    @Transactional
    public void createCarShopOrder(CreateCarShopOrderRequest carShopPageRequest) throws Exception {
        //形成订单
        SnowFlake snowFlake = new SnowFlake(2, 9);
        String orderCode = String.valueOf(snowFlake.nextId());
        OrderRecords orderRecords = new OrderRecords();
        orderRecords.setAddressId(String.valueOf(carShopPageRequest.getAddressId()));
        orderRecords.setOpenid(carShopPageRequest.getOpenid());
        orderRecords.setState(GoodsStatus.pay);
        orderRecords.setSumPrice(carShopPageRequest.getSum());
        orderRecords.setOrdercode(orderCode);
        orderRecords.setCt(new Date());
        orderRecords.setUt(new Date());
        orderRecords.setType(2);
        orderRecordsService.insert(orderRecords);
        //明细
        List<OrderRecordsDetails> orderRecordsDetailsList = new ArrayList<>();
        List<CarShopDetailDto> carShopDetailDtoList = carShopPageRequest.getCarShopDetailDtoList();
        List list = new ArrayList();
        for(CarShopDetailDto carShopDetailDto:carShopDetailDtoList){
            OrderRecordsDetails orderRecordsDetails = new OrderRecordsDetails();
            orderRecordsDetails.setCt(new Date());
            orderRecordsDetails.setGoodsId(carShopDetailDto.getGoodId());
            orderRecordsDetails.setNum(carShopDetailDto.getNum());
            orderRecordsDetails.setOrderId(orderRecords.getId());
            orderRecordsDetails.setPrice(carShopDetailDto.getPrice());
            orderRecordsDetails.setUt(new Date());
            list.add(carShopDetailDto.getGoodId());
            orderRecordsDetailsList.add(orderRecordsDetails);
        }
        orderRecordsDetailsService.insertBatch(orderRecordsDetailsList);
        //更改购物车状态
        //carShopPageRequest.getOpenid()
        CarShop carShop = new CarShop();
        carShop.setState(1);
        carShop.setOrderId(orderCode);
        carShop.setUt(new Date());
        EntityWrapper<CarShop> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("open_id",carShopPageRequest.getOpenid());
        entityWrapper.in("good_id",list);
        entityWrapper.eq("state",0);
        carShopMapper.update(carShop,entityWrapper);
        //发送邮件提醒
        String content = this.getOrderInfo(orderRecords);
        mailService.sendHtmlMail(mailUrl, "支付完成等待发货->订单号："+orderCode, content);
    }

    @Override
    public  synchronized void offlinePool(OrderRequest orderRequest) throws Exception{

            System.out.println(System.currentTimeMillis());
            log.info("OrderRecordsServiceImpl -- save:"+JSONObject.toJSONString(orderRequest));
            SnowFlake snowFlake = new SnowFlake(2, 9);
            String orderCode = String.valueOf(snowFlake.nextId());
            Goods goods = goodsService.selectById(orderRequest.getGoodsId());

            StoreHouse storeHouse =
                    storeHouseService.selectOne(new EntityWrapper<StoreHouse>().eq("goods_id",orderRequest.getGoodsId()));
            int count  =  storeHouse.getCount() -1 ;
            storeHouse.setCount(count);
            storeHouse.setUt(new Date());
            storeHouseService.updateById(storeHouse);

            if(goods == null){
                log.info("没有商品了！");
            }

            BigDecimal b1 = new BigDecimal(goods.getPrice().toString());
            BigDecimal b2 = new BigDecimal(orderRequest.getNum().toString());
            BigDecimal sum = b1.multiply(b2);

            OrderRecords orderRecords = new OrderRecords();
            orderRecords.setAddressId(String.valueOf(orderRequest.getAddressId()));
            orderRecords.setOpenid(orderRequest.getOpenid());
            orderRecords.setState(GoodsStatus.pay);
            orderRecords.setSumPrice(sum);
            orderRecords.setOrdercode(orderCode);
            orderRecords.setCt(new Date());
            orderRecords.setUt(new Date());
            orderRecords.setType(2);

            orderRecordsService.insert(orderRecords);

            OrderRecordsDetails orderRecordsDetails = new OrderRecordsDetails();
            orderRecordsDetails.setCt(new Date());
            orderRecordsDetails.setGoodsId(goods.getId());
            orderRecordsDetails.setNum(orderRequest.getNum());
            orderRecordsDetails.setOrderId(orderRecords.getId());
            orderRecordsDetails.setPrice(goods.getPrice());
            orderRecordsDetails.setUt(new Date());
            orderRecordsDetailsService.insert(orderRecordsDetails);
            //String content = this.getOrderInfo(orderRecords);
            //mailService.sendHtmlMail(mailUrl, "支付完成等待发货->订单号："+orderCode, content);




    }
}
