package com.dragon.flow.service.flowable;

import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;

import java.util.Collection;
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

    /**
     * 获取end节点
     *
     * @param processDefId 流程定义id
     * @return FlowElement
     */
    public List<EndEvent> findEndFlowElement(String processDefId);

    /**
     * 通过名称获取节点
     *
     * @param processDefId 流程定义id
     * @param name         节点名称
     * @return
     */
    public Activity findActivityByName(String processDefId, String name) ;
}
