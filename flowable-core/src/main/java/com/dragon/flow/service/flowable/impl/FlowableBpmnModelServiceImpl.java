package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableBpmnModelService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : bruce.liu
 * @title: : FlowableBpmnModelServiceImpl
 * @projectName : flowable
 * @description: BpmnModel service
 * @date : 2019/11/2519:42
 */
@Service
public class FlowableBpmnModelServiceImpl implements IFlowableBpmnModelService {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public BpmnModel getBpmnModelByProcessDefId(String processDefId) {
        return repositoryService.getBpmnModel(processDefId);
    }
}
