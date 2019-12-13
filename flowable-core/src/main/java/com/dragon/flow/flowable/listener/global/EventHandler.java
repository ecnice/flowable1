package com.dragon.flow.flowable.listener.global;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;

/**
 * @Description:
 * @Author: Bruce.liu
 * @Since:17:30 2019/12/13
 *  2018 ~ 2030 版权所有
 */
public interface EventHandler {

    /**
     * 执行方法
     * @param event 事件对象
     */
    public void handle(FlowableEvent event) ;
}
