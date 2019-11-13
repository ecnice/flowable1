package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.vo.flowable.StartProcessInstanceVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public ReturnVo startProcessInstanceByKey(StartProcessInstanceVo params) {
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
}
