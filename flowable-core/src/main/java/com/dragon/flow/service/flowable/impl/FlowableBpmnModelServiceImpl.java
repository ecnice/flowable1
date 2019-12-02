package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableBpmnModelService;
import org.apache.commons.collections.CollectionUtils;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : bruce.liu
 * @title: : FlowableBpmnModelServiceImpl
 * @projectName : flowable
 * @description: BpmnModel service
 * @date : 2019/11/2519:42
 */
@Service
public class FlowableBpmnModelServiceImpl extends BaseProcessService implements IFlowableBpmnModelService {

    @Override
    public BpmnModel getBpmnModelByProcessDefId(String processDefId) {
        return repositoryService.getBpmnModel(processDefId);
    }

    public List<FlowNode> findFlowNodes(String processDefId) {
        List<FlowNode> flowNodes = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> list = process.getFlowElements();
        list.forEach(flowElement -> {
            if (flowElement instanceof FlowNode) {
                flowNodes.add((FlowNode) flowElement);
            }
        });
        return flowNodes;
    }

    @Override
    public List<EndEvent> findEndFlowElement(String processDefId) {
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
        if (bpmnModel != null) {
            Process process = bpmnModel.getMainProcess();
            return process.findFlowElementsOfType(EndEvent.class);
        } else {
            return null;
        }
    }
}
