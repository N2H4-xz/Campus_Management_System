package com.orbithy.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orbithy.cms.data.po.Section;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SectionMapper extends BaseMapper<Section> {

    @Select("select count(*) from section where grade=#{grade}")
    int getSectionCount(String grade);

    @Select("select id from section where grade=#{grade}")
    List<Integer> getSectionList(String grade);
}
