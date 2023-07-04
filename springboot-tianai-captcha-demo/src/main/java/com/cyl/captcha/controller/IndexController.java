package com.cyl.captcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cyl
 * @date 2023-07-04 9:10
 * @description 模板渲染控制层
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/slider")
    public String slider() {
        return "slider";
    }

    @GetMapping("/rotate")
    public String rotate() {
        return "rotate";
    }


    @GetMapping("/concat")
    public String concat() {
        return "concat";
    }


    @GetMapping("/word-click")
    public String wordClick() {
        return "word-click";
    }


}


