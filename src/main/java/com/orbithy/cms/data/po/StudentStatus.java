package com.orbithy.cms.data.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum StudentStatus {
    STUDYING(0, "在读"),
    SUSPENDED(1, "休学"),
    TRANSFERRED(2, "降转"),
    DROPPED_OUT(3, "退学");

    @EnumValue
    private final int code;

    private final String description;

    StudentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 通过 code 获取枚举，找不到则返回 null
     */
    public static StudentStatus fromCode(int code) {
        for (StudentStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * 通过描述获取枚举，找不到则返回 null
     */
    public static StudentStatus fromDescription(String description) {
        for (StudentStatus status : values()) {
            if (status.description.equals(description)) {
                return status;
            }
        }
        return null;
    }

}
