package com.codechen.desensitized.controller;

import com.codechen.desensitized.annotation.DisableDesensitized;
import com.codechen.desensitized.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：Java陈序员
 * @date 2025/10/26 16:05
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    @DisableDesensitized
    public User getUserInfo() {
        User user = new User();
        user.setUsername("codechen");
        user.setNickName("陈序员");
        user.setPassword("123456");
        user.setPhone("12345678910");
        user.setIdCard("110123456789012345");
        user.setBankCard("6222020200020200200");
        return user;
    }

    @GetMapping("/page")
    @DisableDesensitized
    public Map<String, Object> getUserInfoPage() {
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("codechen");
            user.setNickName("陈序员");
            user.setPassword("123456");
            user.setPhone("12345678910");
            user.setIdCard("110123456789012345");
            user.setBankCard("6222020200020200200");
            list.add(user);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("total", 100);

        return result;
    }
}
