package com.orbithy.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orbithy.cms.data.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
