package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.vo.flowable.ProcessInstanceQueryVo;
import com.dragon.flow.vo.flowable.StartProcessInstanceVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : FlowableProcessInstanceServiceImpl
 * @projectName : flowable
 * @description: 流程实例service
 * @date : 2019/11/1314:56
 */
@Service
public class FlowableProcessInstanceServiceImpl implements IFlowableProcessInstanceService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;


    @Override
    public ReturnVo<ProcessInstance> startProcessInstanceByKey(StartProcessInstanceVo params) {
        ReturnVo<ProcessInstance> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
        identityService.setAuthenticatedUserId(params.getCurrentUserCode());
        ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionKey(params.getProcessDefinitionKey().trim())
                .name(params.getFormName().trim())
                .businessKey(params.getBusinessKey().trim())
                .variables(params.getVariables())
                .tenantId(params.getSystemSn().trim())
                .start();
        returnVo.setData(processInstance);
        return returnVo;
    }

    @Override
    public PagerModel<HistoricProcessInstance> getMyProcessInstances(ProcessInstanceQueryVo params, Query query) {
        List<HistoricProcessInstance> datas = historyService.createHistoricProcessInstanceQuery().processInstanceNameLike(params.getFormName())
                .startedBy(params.getUserCode()).listPage(query.getStartRow(), query.getPageSize());
        long count = historyService.createHistoricProcessInstanceQuery().processInstanceNameLike(params.getFormName())
                .startedBy(params.getUserCode()).count();
        return new PagerModel<>(count, datas);
    }
}
