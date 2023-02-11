package com.chen.secondkill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author cyl
 * @date 2023-02-11 11:49
 * @description swagger 配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("秒杀服务")
                .description("秒杀服务实战")
                .version("1.0.0")
                .build();
        return apiInfo;
    }

    @Bean
    public Docket docket(ApiInfo apiInfo) {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chen.secondkill.controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
