package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.ProcessDefinitionQueryVo;
import com.dragon.flow.vo.flowable.ret.ProcessDefinitionVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import com.github.pagehelper.Page;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;

/**
 * @author : bruce.liu
 * @title: : IFlowProcessDi
 * @projectName : flowable
 * @description: 流程定义
 * @date : 2019/11/1314:11
 */
public interface IFlowableProcessDefinitionService {

    /**
     * 通过条件查询流程定义
     *
     * @param params
     * @return
     */
    public PagerModel<ProcessDefinitionVo> getPagerModel(ProcessDefinitionQueryVo params, Query query);

    /**
     * 通过流程定义id获取流程定义的信息
     *
     * @param processDefinitionId 流程定义id
     * @return
     */
    public ProcessDefinitionVo getById(String processDefinitionId);

    /**
     * 挂起流程定义
     *
     * @param processDefinitionId 流程定义id
     * @param suspensionState     状态1挂起 2激活
     */
    public ReturnVo suspendOrActivateProcessDefinitionById(String processDefinitionId, int suspensionState);

}
