package com.dragon.flow.config;

import org.flowable.dmn.spring.SpringDmnEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;


/**
 * @author : bruce.liu
 * @title: : FlowableDmnConfig
 * @projectName : flowable
 * @description: DMN config
 * @date : 2019/12/2417:59
 */
@Configuration
public class FlowableDmnConfig implements EngineConfigurationConfigurer<SpringDmnEngineConfiguration> {

    @Override
    public void configure(SpringDmnEngineConfiguration configure) {
        //设置是否升级 false不升级  true升级
        configure.setDatabaseSchemaUpdate("true");
    }

}
