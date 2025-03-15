package com.orbithy.cms.data.po;

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

    private String name;
    private String category;
    private Byte point;
    private Integer teacherId;
    private String classroom;
    private Byte weekStart;
    private Byte weekEnd;
    private Byte period;
    private String time;
    private String college;
    private String term;
    private String classNum;
    private CourseType type;
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
