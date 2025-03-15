package com.orbithy.cms.data.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.core.enums.IEnum;


@Getter
@Setter
@NoArgsConstructor
@TableName("classes")
public class Classes {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "category")
    private String category;

    @TableField(value = "point")
    private Byte point;

    @TableField(value = "teacher_id")
    private Integer teacherId;

    @TableField(value = "classroom")
    private String classroom;

    @TableField(value = "week_start")
    private Byte weekStart;

    @TableField(value = "week_end")
    private Byte weekEnd;

    @TableField(value = "period")
    private Byte period;

    @TableField(value = "time")
    private String time; // MySQL的SET类型存储为逗号分隔的字符串，如 "1,2,3"

    @TableField(value = "college")
    private String college;

    @TableField(value = "term")
    private String term;

    @TableField(value = "class_num")
    private String classNum;

    @TableField(value = "type")
    private CourseType type;
}


