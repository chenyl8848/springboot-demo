package com.codechen.desensitized.entity;

import lombok.Data;

import java.util.List;

/**
 * @author：Java陈序员
 * @date 2025/10/26 16:57
 * @description 角色信息
 */
@Data
public class Role extends BaseEntity {

    /** 角色名称 */
    private String roleName;
    /** 用户列表 */
    private List<User> userList;
}
