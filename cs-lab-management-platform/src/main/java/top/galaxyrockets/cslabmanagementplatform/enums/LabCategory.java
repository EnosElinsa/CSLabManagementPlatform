package top.galaxyrockets.cslabmanagementplatform.enums;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonValue;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author EnosElinsa
 * @since 2024/4/30
 */
@Getter
public enum LabCategory {
    SOFTWARE("软件"),
    HARDWARE("硬件"),
    NETWORK("网络"),
    ;

    @EnumValue
    @JsonValue
    private final String categoryName;

    LabCategory(String categoryName) {
        this.categoryName = categoryName;
    }

}
