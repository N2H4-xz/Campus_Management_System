package com.orbithy.cms.data.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@TableName("status")
public class Status {
    @TableId(value = "id") //  id 作为主键，关联 user 表
    private Integer id;

    @TableField(value = "grade")
    private Byte grade;

    @TableField(value = "section")
    private Byte section;

    @EnumValue
    @TableField(value = "status")
    private StudentStatus status;
}
