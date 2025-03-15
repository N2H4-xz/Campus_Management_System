package com.orbithy.cms.data.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "email")
    private String email;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "password")
    private String password;

    @TableField(value = "student_id")
    private String studentId;

    @TableField(value = "major")
    private Integer major;

    @TableField(value = "section")
    private Integer section;

    @TableField(value = "permission")
    private Byte permission;

    @TableField(value = "nation")
    private String nation;

    @TableField(value = "ethnic")
    private String ethnic;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Timestamp createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updatedAt;

    @TableField(value = "last_login_at")
    private Timestamp lastLoginAt;
}




