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
     *
     * @param processDefId 流程定义id
     * @return
     */
    public BpmnModel getBpmnModelByProcessDefId(String processDefId);

    /**
     * 通过流程定义id获取所有的节点
     *
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
     * 判断节点是不是子流程的节点
     * @param processDefId 流程定义id
     * @param activityId 节点id
     * @return
     */
    public boolean checkActivitySubprocessByActivityId(String processDefId, String activityId);
    /**
     * 通过流程id获取节点
     *
     * @param processDefId 流程定义id
     * @param activityId   节点id
     * @return
     */
    public List<Activity> findActivityByActivityId(String processDefId, String activityId);

    /**
     * 通过流程id获取主流程中的节点
     *
     * @param processDefId 流程定义id
     * @param activityId   节点id
     * @param processDefId
     * @param activityId
     * @return
     */
    public FlowNode findMainProcessActivityByActivityId(String processDefId, String activityId);

    /**
     * 查找节点
     * @param processDefId 流程定义id
     * @param activityId 节点id
     * @return
     */
    public FlowNode findFlowNodeByActivityId(String processDefId, String activityId) ;

    /**
     * 通过名称获取节点
     *
     * @param processDefId 流程定义id
     * @param name         节点名称
     * @return
     */
    public Activity findActivityByName(String processDefId, String name);
}
