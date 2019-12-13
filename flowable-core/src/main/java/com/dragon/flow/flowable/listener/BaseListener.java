package com.dragon.flow.flowable.listener;

import org.apache.commons.lang.StringUtils;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.engine.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Description: 基本的监听器
 * @Author: Bruce.liu
 * @Since:18:07 2018/11/13
 * 爱拼才会赢 2018 ~ 2030 版权所有
 */
public abstract class BaseListener {

    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected IdentityService identityService;

    /**
     * 设置参数
     * @param parameters 参数
     * @param paramMap 传值map
     */
    public void setParams(String parameters, Map<String, Object> paramMap) {
        if (StringUtils.isNotBlank(parameters)) {
            String[] ps = parameters.split(";");
            if (ps != null && ps.length > 0) {
                for (String p : ps) {
                    String[] split = p.split(":");
                    if (split != null && split.length > 0) {
                        paramMap.put(split[0], split[1]);
                    }
                }
            }
        }
    }


    public Object getTaskEntityByEvent(FlowableEvent event){
        Object taskEntity = null;
        if (event instanceof org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl){
            org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl eventImpl = (org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl) event;
            taskEntity = eventImpl.getEntity();
        }else if (event instanceof org.flowable.common.engine.impl.event.FlowableEntityEventImpl){
            org.flowable.common.engine.impl.event.FlowableEntityEventImpl eventImpl = (org.flowable.common.engine.impl.event.FlowableEntityEventImpl) event;
            taskEntity = eventImpl.getEntity();
        }
        return taskEntity;
    }


}
