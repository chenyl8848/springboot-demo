package com.codechen.desensitized.controller;

import com.codechen.desensitized.entity.Role;
import com.codechen.desensitized.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：Java陈序员
 * @date 2025/10/26 16:59
 * @description
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @GetMapping("/")
    public Role getRoleInfo() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("codechen");
            user.setNickName("陈序员");
            user.setPassword("123456");
            user.setPhone("12345678910");
            user.setIdCard("110123456789012345");
            user.setBankCard("6222020200020200200");
            users.add(user);
        }

        Role role = new Role();
        role.setRoleName("超级管理员");
        role.setUserList(users);

        return role;
    }
}
