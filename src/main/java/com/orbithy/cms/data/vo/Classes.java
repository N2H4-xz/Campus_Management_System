package com.orbithy.cms.data.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.EnumValue;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String time; // MySQL SET 类型存储为逗号分隔的字符串，如 "1,2,3"

    @TableField(value = "college")
    private String college;

    @TableField(value = "term")
    private String term;

    @TableField(value = "class_num")
    private String classNum;

    @TableField(value = "type")
    private CourseType type;

    @TableField(value = "capacity")
    private Byte capacity;

    // 处理 MySQL SET 类型，转换为 Java Set<Integer>
    public Set<Integer> getTimeSet() {
        if (this.time == null || this.time.isEmpty()) {
            return Set.of();
        }
        return Set.of(this.time.split(","))
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    public void setTimeSet(Set<Integer> timeSet) {
        this.time = timeSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    // 课程类型枚举
    @Getter
    public enum CourseType {
        MANDATORY("必修"),
        LIMITED("限选"),
        ELECTIVE("任选");

        @EnumValue  // 标记存储到数据库的字段
        private final String value;

        CourseType(String value) {
            this.value = value;
        }

    }
}
