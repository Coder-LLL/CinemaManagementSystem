package com.zrkworld.cinema;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lzh
 */

@EnableSwagger2 // 启用swagger
@SpringBootApplication
@MapperScan("com.zrkworld.cinema.mapper")
public class CimenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CimenaApplication.class, args);
    }

}
