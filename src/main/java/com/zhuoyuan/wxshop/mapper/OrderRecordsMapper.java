package com.zhuoyuan.wxshop.mapper;

import com.zhuoyuan.wxshop.model.OrderRecords;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@Repository
public interface OrderRecordsMapper extends BaseMapper<OrderRecords> {

    Integer selectTest();
}
