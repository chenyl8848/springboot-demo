package com.codechen.common.controller;

import com.codechen.common.enums.ResultCodeEnum;
import com.codechen.common.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：Java陈序员
 * @date：2024-10-12 16:53
 * @description：测试接口
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public Result<List<String>> query() {
        List<String> list = new ArrayList<>();
        list.add("zhangsan");
        list.add("lisi");
        list.add("wangwu");

//        return Result.success("test success");
//        return Result.success("成功", list);
        return Result.build(ResultCodeEnum.SUCCESS, list);
    }
}
