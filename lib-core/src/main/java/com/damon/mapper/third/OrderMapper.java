package com.damon.mapper.third;

import com.damon.entity.third.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单数据库访问层
 *
 * @author damon du/minghongdud
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
}
