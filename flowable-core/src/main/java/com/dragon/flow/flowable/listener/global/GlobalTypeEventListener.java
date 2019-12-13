package com.dragon.flow.flowable.listener.global;

import com.dragon.tools.common.SpringContextHolder;
import org.flowable.common.engine.api.delegate.event.AbstractFlowableEventListener;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 全局的类型事件监听入口
 * @Author: Bruce.liu
 * @Since:17:28 2019/12/13
 * 2018 ~ 2030 版权所有
 */
public class GlobalTypeEventListener extends AbstractFlowableEventListener {
    public final static Logger logger = LoggerFactory.getLogger(GlobalTypeEventListener.class);
    /**
     * 调用的具体实例实现bean的id
     */
    private String eventHandlerBeanId;

    @Override
    public void onEvent(FlowableEvent event) {
        String eventType = event.getType().name();
        logger.debug("envent type is ========>" + eventType);
        // 根据事件的类型ID,找到对应的事件处理器
        if (eventHandlerBeanId != null) {
            EventHandler handler = SpringContextHolder.getBean(eventHandlerBeanId);
            handler.handle(event);
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    public void setEventHandlerBeanId(String eventHandlerBeanId) {
        this.eventHandlerBeanId = eventHandlerBeanId;
    }
}
