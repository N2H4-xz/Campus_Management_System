package com.orbithy.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orbithy.cms.data.po.Classes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClassMapper extends BaseMapper<Classes> {
    @Insert("INSERT INTO classes (name, category, point, teacher_id, classroom, week_start, week_end, " +
            "period, time, college, term, class_num, type, capacity, status) " +
            "VALUES (#{name}, #{category}, #{point}, #{teacherId}, #{classroom}, #{weekStart}, #{weekEnd}, " +
            "#{period}, #{time}, #{college}, #{term}, #{classNum}, #{type}, #{capacity}, 0)")
    void createCourse(Classes course);

    @Update("UPDATE classes SET status = #{status} WHERE id = #{courseId}")
    void updateCourseStatus(@Param("courseId") Integer courseId, @Param("status") Integer status);

    @Select("SELECT * FROM classes WHERE id = #{courseId}")
    Classes getCourseById(Integer courseId);
}