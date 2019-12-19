package com.dragon.flow.config;


import com.dragon.flow.flowable.listener.global.GlobalTaskCreateListener;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;
import org.flowable.engine.RuntimeService;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author : bruce.liu
 * @title: : FlowableGlobListenerConfig
 * @projectName : flowable
 * @description: 全局监听配置 ContextRefreshedEvent在类被初始化之后触发
 * @date : 2019/12/1816:57
 */
@Configuration
public class FlowableGlobListenerConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SpringProcessEngineConfiguration configuration;
    @Autowired
    private GlobalTaskCreateListener globalTaskCreateListener;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        FlowableEventDispatcher dispatcher = configuration.getEventDispatcher();
        //任务创建全局监听
        dispatcher.addEventListener(globalTaskCreateListener, FlowableEngineEventType.TASK_CREATED);
    }
}
