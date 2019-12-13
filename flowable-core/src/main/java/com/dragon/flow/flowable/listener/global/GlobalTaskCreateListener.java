package com.dragon.flow.flowable.listener.global;

import com.dragon.flow.constant.FlowConstant;
import com.dragon.flow.flowable.listener.BaseListener;
import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.service.flowable.IFlowableTaskService;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.engine.ManagementService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 全局的任务创建监听
 * @Author: Bruce.liu
 * @Since:19:15 2019/12/13
 *  2018 ~ 2030 版权所有
 */
@Component(FlowConstant.GLOBALTASKCREATELISTENER)
public class GlobalTaskCreateListener extends BaseListener implements EventHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IFlowableTaskService flowableTaskService;

    @Override
    public void handle(FlowableEvent event) {
        try {
            Object taskEntity = this.getTaskEntityByEvent(event);
            if (taskEntity instanceof TaskEntity) {
                TaskEntity entity = (TaskEntity) taskEntity;

                //1、授权

                //2、相邻节点自动跳过

                //3、发送消息
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("全局的任务创建监听报错", e);
        }
    }

}
