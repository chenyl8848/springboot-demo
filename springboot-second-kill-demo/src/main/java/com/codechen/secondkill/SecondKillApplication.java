package com.codechen.secondkill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cyl
 * @date 2023-02-11 11:27
 * @description 秒杀服务主启动类
 */
@SpringBootApplication
@MapperScan(basePackages = "com.chen.secondkill.mapper")
public class SecondKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondKillApplication.class);
    }
}
