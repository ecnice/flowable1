package com.dragon.flow.flowable.interceptor;

import org.flowable.common.engine.impl.interceptor.AbstractCommandInterceptor;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandConfig;
import org.flowable.engine.impl.cmd.CompleteTaskCmd;

/**
 * @author : bruce.liu
 * @title: : FlowablePostCommandInterceptor
 * @projectName : flowable
 * @description: 后置拦截器
 * @date : 2019/12/1216:11
 */
public class FlowablePostCommandInterceptor extends AbstractCommandInterceptor {

    @Override
    public <T> T execute(CommandConfig commandConfig, Command<T> command) {
        if (command instanceof CompleteTaskCmd){
            CompleteTaskCmd cmd = (CompleteTaskCmd)command;
        }
        return next.execute(commandConfig, command);
    }
}
