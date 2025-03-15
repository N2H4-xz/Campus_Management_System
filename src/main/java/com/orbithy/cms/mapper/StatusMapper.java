package com.orbithy.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orbithy.cms.data.vo.Status;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatusMapper extends BaseMapper<Status> {

    @Insert("INSERT INTO status (id, grade) VALUES (#{id}, #{grade})")
    void insertStatus(@Param("id") String id, @Param("grade") String grade);

}
