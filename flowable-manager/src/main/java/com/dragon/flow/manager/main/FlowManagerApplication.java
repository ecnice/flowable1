package com.dragon.flow.manager.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : bruce.liu
 * @title: : FlowManagerApplication
 * @projectName : flowable
 * @description: 启动类
 * @date : 2019/11/1313:34
 */
@SpringBootApplication(scanBasePackages = {"com.dragon"})
@MapperScan("com.dragon.*.dao.*")
@EnableTransactionManagement
public class FlowManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowManagerApplication.class, args);
    }
}
