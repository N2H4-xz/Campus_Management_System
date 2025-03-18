package com.orbithy.cms.data.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.EnumValue;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

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
    private CourseStatus status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

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

    // 课程状态枚举
    @Getter
    public enum CourseStatus {
        PENDING(0, "申请中"),
        APPROVED(1, "已通过"),
        REJECTED(2, "已拒绝"),
        COMPLETED(3, "已结课");

        @EnumValue
        private final Integer code;
        private final String description;

        CourseStatus(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public static CourseStatus fromCode(Integer code) {
            for (CourseStatus status : CourseStatus.values()) {
                if (status.getCode().equals(code)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid course status code: " + code);
        }
    }

    // 验证课程时间是否合理
    public boolean isValidTime() {
        if (weekStart == null || weekEnd == null || weekStart > weekEnd) {
            return false;
        }
        if (period == null || period <= 0) {
            return false;
        }
        Set<Integer> timeSet = getTimeSet();
        return !timeSet.isEmpty() && timeSet.stream().allMatch(t -> t >= 0 && t <= 24);
    }

    // 验证课程容量是否合理
    public boolean isValidCapacity() {
        return capacity != null && capacity > 0;
    }

    // 验证学期格式是否正确
    public boolean isValidTerm() {
        if (term == null || term.isEmpty()) {
            return false;
        }
        // 格式：YYYY-YYYY-S，例如：2023-2024-1
        return term.matches("\\d{4}-\\d{4}-[12]");
    }
}
