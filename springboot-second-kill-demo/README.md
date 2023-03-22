# 实现高并发秒杀的几种方式

## 引言

### 数据库设计

- 商品表
```sql
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`  (
  `sku_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品主键',
  `sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_storage` int(11) NULL DEFAULT NULL COMMENT '商品库存',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 订单表
```
DROP TABLE IF EXISTS `product_order`;
CREATE TABLE `product_order`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户主键',
  `sku_id` int(11) NULL DEFAULT NULL COMMENT '商品主键',
  `order_state` int(11) NULL DEFAULT NULL COMMENT '订单状态',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2144503842 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```

### 项目搭建

项目环境： `SpringBoot 2.5.2` + `MySQL 8.0.X` + `MybatisPlus 3.5.2` + `Swagger 2.7.0` + `Lombok`

`pom.xml` 文件：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.chen</groupId>
    <artifactId>springboot-second-kill-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.5.2</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.2</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.7.0</version>
        </dependency>

        <!-- ip:port/swagger-ui.html 的 swagger ui -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.7.0</version>
        </dependency>

        <!-- ip:port/doc.html 的 swagger ui -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>swagger-bootstrap-ui</artifactId>
            <version>1.9.6</version>
        </dependency>

        <!-- ip:port/swagger-ui/index.html 的 swagger ui -->
        <!--        <dependency>-->
        <!--            <groupId>io.springfox</groupId>-->
        <!--            <artifactId>springfox-boot-starter</artifactId>-->
        <!--            <version>3.0.0</version>-->
        <!--        </dependency>-->

        <!-- 引入 disruptor -->
        <dependency>
            <groupId>com.lmax</groupId>
            <artifactId>disruptor</artifactId>
            <version>3.4.0</version>
        </dependency>
    </dependencies>

</project>
```

配置文件：
```yaml
spring:
  application:
    name: second-kill
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/second_kill?serverTimezone=Asia/Shanghai&characterEncoding=utf-8
    username: root
    password: root

mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

```

`Swagger` 配置类：
```java
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
```



### 模拟场景 

**模拟工具**：`Apache Jmeter`

**模拟数据**：
- 并发数1000 商品数100
- 并发数1000 商品数1000
- 并发数2000 商品数1000

## 实现方式一——锁

### Controller

```java
@ApiOperation(value = "秒杀实现方式--Lock加锁")
@PostMapping("/start/lock")
public Result startLock(Integer skuId) {
    try {
        log.info("开始秒杀...Lock加锁...");
        Integer userId = new Random().nextInt() * (99999 - 10000 + 1) + 10000;
        Result result = secondKillService.startKillByLock(userId, skuId);
        if (result != null) {
            log.info("用户:{}--{}", userId, result.getMessage());
        } else {
            log.info("用户:{}--{}", userId, "人太多了，请稍后");
        }

        return result;

    } catch (Exception e) {
        e.printStackTrace();
        return Result.fail("程序出现错误,请联系系统管理员");
    } finally {

    }
}
```

### Service

```java
/**
 * 会出现超卖的情况
 * 这里在业务方法开始加了锁，在业务方法结束后释放了锁。
 * 但这里的事务提交却不是这样的，有可能在事务提交之前，就已经把锁释放了，这样会导致商品超卖现象。（事务提交是在整个方法执行完）
 * 所以加锁的时机很重要！
 * - 可以在controller层进行加锁
 * - 可以使用Aop在业务方法执行之前进行加锁
 *
 * @param userId
 * @param skuId
 * @return
 */
@Override
@Transactional(rollbackFor = Exception.class)
public Result startKillByLock(Integer userId, Integer skuId) {
    // 加锁
    lock.lock();
    try {
        // 1.校验库存
        ProductSku productSku = productSkuService.getById(skuId);
        if (productSku == null) {
            return Result.fail("商品不存在");
        }
        Integer storage = productSku.getSkuStorage();
        if (storage > 0) {
            // 2.扣库存
            productSku.setSkuStorage(storage - 1);
            productSkuService.updateById(productSku);

            // 3.创建订单
            ProductOrder productOrder = new ProductOrder();
            productOrder.setUserId(userId);
            productOrder.setSkuId(skuId);
            productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
            productOrderService.save(productOrder);

        } else {
            return Result.fail("秒杀失败");
        }

    } catch (Exception e) {
        throw e;
    } finally {
        // 解锁
        lock.unlock();
    }
    return Result.success("秒杀成功");
}
```

### Dao

```java
```

## 实现方式二——锁优化

### Controller

### Service

### Dao

## 实现方式三——`AOP`锁

### Controller

### Service

### Dao

## 实现方式四——数据库锁

### Controller

### Service

### Dao

## 实现方式五——阻塞队列

### Controller

### Service

### Dao

## 实现方式六——`Disruptor`队列

### Controller

### Service

### Dao