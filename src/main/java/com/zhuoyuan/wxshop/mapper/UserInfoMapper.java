package com.zhuoyuan.wxshop.mapper;

import com.zhuoyuan.wxshop.model.UserInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhuoyuan.wxshop.request.CustomerRelationRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author Wangjie
 * @since 2019-06-30
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    List<UserInfo> getCustomerRelationRecord(CustomerRelationRecord customerRelationRecord);
}
