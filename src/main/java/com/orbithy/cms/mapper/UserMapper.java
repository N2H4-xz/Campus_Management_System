package com.orbithy.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orbithy.cms.data.vo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select id from user where student_id=#{SDUId}")
    Integer getUserId(String SDUId);

    @Insert("insert into user (username, password, student_id, major) values (#{username}, #{password}, #{SDUId}, 0)")
    void addUser(String username, String password, String SDUId);
    @Insert("insert into user (username, password, student_id, major) values (#{username}, #{password}, #{SDUId}, #{permission})")
    void addTeacher(String username, String password, String SDUId, int permission);
}
