package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableProcessDefinitionService;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : bruce.liu
 * @title: : FlowableProcessDefinitionServiceImpl
 * @projectName : flowable
 * @description: 流程定义service
 * @date : 2019/11/1314:18
 */
@Service
public class FlowableProcessDefinitionServiceImpl implements IFlowableProcessDefinitionService {

    @Autowired
    private RepositoryService repositoryService;


}
