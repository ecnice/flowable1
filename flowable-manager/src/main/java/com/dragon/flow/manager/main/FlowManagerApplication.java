package com.dragon.flow.manager.main;

import com.dragon.flow.config.ApplicationConfiguration;
import com.dragon.flow.constant.FlowConstant;
import com.dragon.flow.servlet.AppDispatcherServletConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : bruce.liu
 * @title: : FlowManagerApplication
 * @projectName : flowable
 * @description: 启动类
 * @date : 2019/11/1313:34
 */
@Import({
        ApplicationConfiguration.class,
        AppDispatcherServletConfiguration.class
})
@EnableScheduling
@MapperScan(FlowConstant.MAPPER_SCAN)
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.dragon"})
public class FlowManagerApplication {
    private static final Logger logger = LoggerFactory.getLogger(FlowManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FlowManagerApplication.class, args);
        logger.info("###########################流程后台程序启动成功##################################");
    }
}
