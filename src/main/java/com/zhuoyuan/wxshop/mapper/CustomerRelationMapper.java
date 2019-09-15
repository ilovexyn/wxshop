package com.zhuoyuan.wxshop.mapper;

import com.zhuoyuan.wxshop.model.CustomerRelation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhuoyuan.wxshop.model.UserInfo;
import com.zhuoyuan.wxshop.request.CustomerRelationRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author Wangjie
 * @since 2019-09-09
 */
@Repository
public interface CustomerRelationMapper extends BaseMapper<CustomerRelation> {

    List<UserInfo> getCustomerRelationRecord(CustomerRelationRecord customerRelationRecord);
}
