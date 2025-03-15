package com.orbithy.cms.data.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.EnumValue;

@Getter
@Setter
@NoArgsConstructor
@TableName("section")
public class Section {
    @TableId(type = IdType.AUTO)
    private Integer id; // 班级唯一ID

    @TableField(value = "major")
    private Major major; // 专业

    @TableField(value = "advisor_id")
    private Integer advisorId; // 导员ID（外键）

    // 专业枚举类型
    public enum Major {
        SOFTWARE_ENGINEERING("0"),
        RASPBERRY("1"),
        BIG_DATA("2"),
        AI("3");

        @EnumValue // 让 MyBatis-Plus 识别该字段的数据库存储值
        private final String value;

        Major(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}

