package com.codechen.desensitized.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author：Java陈序员
 * @date 2025/10/26 17:15
 * @description 基础实体类
 */
@Data
public class BaseEntity {

    /** id */
    private Integer id;

    /** 是否脱敏 默认是 */
    @JsonIgnore
    private Boolean desensitized = true;
}
