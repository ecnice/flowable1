package com.dragon.flow.service.flowable;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowNode;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : IFlowableBpmnModelService
 * @projectName : flowable
 * @description: BpmnModelservice
 * @date : 2019/11/2519:38
 */
public interface IFlowableBpmnModelService {

    /**
     * 通过流程定义id获取BpmnModel
     * @param processDefId 流程定义id
     * @return
     */
    public BpmnModel getBpmnModelByProcessDefId(String processDefId);

    /**
     * 通过流程定义id获取所有的节点
     * @param processDefId 流程定义id
     * @return
     */
    public List<FlowNode> findFlowNodes(String processDefId);
}
