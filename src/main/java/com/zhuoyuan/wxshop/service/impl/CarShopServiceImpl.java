package com.zhuoyuan.wxshop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.dto.CarShopDetailDto;
import com.zhuoyuan.wxshop.dto.CarShopDto;
import com.zhuoyuan.wxshop.dto.CarShopOrderDetailDto;
import com.zhuoyuan.wxshop.dto.CarShopOrderDto;
import com.zhuoyuan.wxshop.model.*;
import com.zhuoyuan.wxshop.mapper.CarShopMapper;
import com.zhuoyuan.wxshop.request.CarShopPageRequest;
import com.zhuoyuan.wxshop.request.CreateCarShopOrderRequest;
import com.zhuoyuan.wxshop.request.PageRequest;
import com.zhuoyuan.wxshop.request.UpdateCarShopRequest;
import com.zhuoyuan.wxshop.service.ICarShopService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.service.IGoodsService;
import com.zhuoyuan.wxshop.service.IOrderRecordsDetailsService;
import com.zhuoyuan.wxshop.service.IOrderRecordsService;
import com.zhuoyuan.wxshop.status.GoodsStatus;
import com.zhuoyuan.wxshop.utils.SnowFlake;
import com.zhuoyuan.wxshop.utils.ossService.OssUtil;
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
 * @since 2019-09-25
 */
@Service
@Slf4j
public class CarShopServiceImpl extends ServiceImpl<CarShopMapper, CarShop> implements ICarShopService {

    @Autowired
    CarShopMapper carShopMapper;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    IOrderRecordsService orderRecordsService;
    @Autowired
    IOrderRecordsDetailsService orderRecordsDetailsService;

    @Override
    public CarShopPageRequest getCarShop(Integer current, Integer size, String openid) {
        CarShopPageRequest carShopPageRequest = new CarShopPageRequest();
        carShopPageRequest.setCurrent(current);
        carShopPageRequest.setSize(size);

        EntityWrapper<CarShop> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("open_id",openid);
        entityWrapper.eq("state",0);
        List<CarShop> carShopList =  carShopMapper.selectList(entityWrapper);
        carShopPageRequest.setTotal(carShopList.size());
        List list = new ArrayList();
        int totalRecord = carShopList.size();// 一共多少条记录
        List<CarShopDetailDto> carShopDetailDtoList = new ArrayList<>();
        BigDecimal bigDecimal = new BigDecimal(0);
        if(totalRecord >0){
            for(CarShop carShop:carShopList){
                CarShopDetailDto carShopDetailDto = new CarShopDetailDto();
                Goods goods = goodsService.selectById(carShop.getGoodId());
                //String imageurl = ossUtil.getURL(goods.getImageurl()).toString();
                carShopDetailDto.setGoodId(goods.getId());
                //carShopDetailDto.setImageurl(imageurl);
                carShopDetailDto.setName(goods.getName());
                carShopDetailDto.setPrice(goods.getPrice());
                carShopDetailDto.setNum(carShop.getNum());
                bigDecimal = bigDecimal.add(new BigDecimal(carShop.getNum()).multiply(goods.getPrice()));

                carShopDetailDtoList.add(carShopDetailDto);
            }
            int firstIndex = (current - 1) * size;
            if(firstIndex<totalRecord){
                int lastIndex = current * size;
                if(lastIndex > totalRecord){
                    lastIndex = totalRecord;
                }
                list = carShopDetailDtoList.subList(firstIndex,lastIndex);
            }
        }
        carShopPageRequest.setRecords(list);
        carShopPageRequest.setSum(bigDecimal);
        return carShopPageRequest;
    }

    @Override
    public void addCarShop(CarShop carShop) throws Exception {
        //如果已经存在次商品ID 只更新数量 如果不存 新增一条购物车信息
        CarShop eCarshop = new CarShop();
        eCarshop.setState(0);
        eCarshop.setGoodId(carShop.getGoodId());
        eCarshop.setOpenId(carShop.getOpenId());

        CarShop tCarshop = carShopMapper.selectOne(eCarshop);
        if(null != tCarshop){
            tCarshop.setNum(tCarshop.getNum()+carShop.getNum());
            tCarshop.setUt(new Date());
            carShopMapper.updateById(tCarshop);
        }else{
            // ============
            carShop.setState(0);
            carShop.setCt(new Date());
            carShop.setUt(new Date());
            carShopMapper.insert(carShop);
        }
    }

    @Override
    public CarShopPageRequest carShopOrder(Integer current, Integer size, String openid) {

        CarShopPageRequest carShopPageRequest = new CarShopPageRequest();
        carShopPageRequest.setCurrent(current);
        carShopPageRequest.setSize(size);
        List<CarShopOrderDetailDto> carShopOrderDetailDtoList = new ArrayList<>();
        BigDecimal sum = new BigDecimal(0);
        //查询购物车信息
        EntityWrapper<CarShop> shopEntityWrapper = new EntityWrapper<>();
        shopEntityWrapper.eq("open_id",openid);
        shopEntityWrapper.eq("state",0);
        List<CarShop> carShopList = carShopMapper.selectList(shopEntityWrapper);
        carShopPageRequest.setTotal(carShopOrderDetailDtoList.size());
        List list = new ArrayList();
        int totalRecord = carShopList.size();// 一共多少条记录
        if(totalRecord >0){
            for(CarShop carShop:carShopList){
                CarShopOrderDetailDto carShopOrderDetailDto = new CarShopOrderDetailDto();
                Goods goods = goodsService.selectById(carShop.getGoodId());
                carShopOrderDetailDto.setNum(carShop.getNum());
                carShopOrderDetailDto.setPrice(goods.getPrice());
                carShopOrderDetailDto.setGoodName(goods.getName());
                //carShopOrderDetailDto.setImageUrl(ossUtil.getURL(goods.getImageurl()).toString());
                carShopOrderDetailDtoList.add(carShopOrderDetailDto);
                sum = sum.add(goods.getPrice().multiply(new BigDecimal(carShop.getNum())));
            }
            int firstIndex = (current - 1) * size;
            if(firstIndex<totalRecord){
                int lastIndex = current * size;
                if(lastIndex > totalRecord){
                    lastIndex = totalRecord;
                }
                list = carShopOrderDetailDtoList.subList(firstIndex,lastIndex);
            }
        }
        carShopPageRequest.setRecords(list);
        carShopPageRequest.setSum(sum);
        return carShopPageRequest;
    }


    @Override
    public void updateCarShop(UpdateCarShopRequest updateCarShopRequest) throws Exception {
        String openid = updateCarShopRequest.getOpenId();
        Long goodsId = updateCarShopRequest.getGoodId();
        Integer type = updateCarShopRequest.getType();
        Integer num = 0;
        if(type>0){
            num = 1;
        }else{
            num = -1;
        }

        CarShop entityCarShop = new CarShop();
        entityCarShop.setGoodId(goodsId);
        entityCarShop.setOpenId(openid);
        CarShop tCarShop = carShopMapper.selectOne(entityCarShop);
        tCarShop.setNum(tCarShop.getNum()+num);
        Integer uCount = carShopMapper.updateById(tCarShop);

    }
}
