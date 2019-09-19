package com.zhuoyuan.wxshop.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhuoyuan.wxshop.model.Goods;
import com.zhuoyuan.wxshop.mapper.GoodsMapper;
import com.zhuoyuan.wxshop.request.Result;
import com.zhuoyuan.wxshop.service.IGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhuoyuan.wxshop.utils.ossService.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    OssUtil ossUtil;

    @Override
    public Page<Goods> getList(int current, int size, String name) {
            Page<Goods> goodsPage = new Page<Goods>(current, size);
            EntityWrapper<Goods> entityWrapper = new EntityWrapper<>();
            if(null == name || "".equals(name)) {
                entityWrapper.like("name",name);
            }
            entityWrapper.eq("state","1");
            List<Goods> goodsList =  goodsMapper.selectPage(goodsPage,entityWrapper);
            //根据路径显示商品图片
           for(Goods goods:goodsList){
               String key = goods.getImageurl();
               URL url = ossUtil.getURL(key);
               String result = url.toString().replace("http","https");
               System.out.println("111:"+result);
           }

            goodsPage.setRecords(goodsMapper.selectPage(goodsPage,entityWrapper));
            return  goodsPage;
    }

    @Override
    public Goods getById(Long id) {
        Goods goods = goodsMapper.selectById(id);
        List<String> stringList = ossUtil.getUrlList(goods.getImageurl());
        return null;
    }
}
