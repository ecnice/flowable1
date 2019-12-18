package com.dragon.flow.flowable.listener.global;

import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 全局的任务创建监听
 * @Author: Bruce.liu
 * @Since:19:15 2019/12/13
 *  2018 ~ 2030 版权所有
 */
@Component
public class GlobalTaskCreateListener extends AbstractFlowableEngineEventListener {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void taskCreated(FlowableEngineEntityEvent event) {
        if (event instanceof FlowableEntityEventImpl){
            //得到流程定义id
            String processDefinitionId = event.getProcessDefinitionId();
            //得到流程实例id
            String processInstanceId = event.getProcessInstanceId();
            FlowableEntityEventImpl eventImpl = (FlowableEntityEventImpl) event;
            //得到任务实例
            TaskEntity entity = (TaskEntity)eventImpl.getEntity();
            //1、授权

            //2、相邻节点自动跳过

            //3、发送消息
        }

    }

}
