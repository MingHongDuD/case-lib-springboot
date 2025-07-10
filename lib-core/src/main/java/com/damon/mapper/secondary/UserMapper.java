package com.damon.mapper.secondary;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.damon.entity.secondary.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库访问层
 *
 * @author damon du/minghongdud
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
