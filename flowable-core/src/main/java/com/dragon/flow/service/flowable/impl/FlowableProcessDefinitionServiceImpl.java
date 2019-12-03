package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.dao.flowable.IFlowableProcessDefinitionDao;
import com.dragon.flow.service.flowable.IFlowableProcessDefinitionService;
import com.dragon.flow.vo.flowable.ProcessDefinitionQueryVo;
import com.dragon.flow.vo.flowable.ret.ProcessDefinitionVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.flowable.bpmn.model.BpmnModel;
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
    private IFlowableProcessDefinitionDao flowableProcessDefinitionDao;

    @Override
    public PagerModel<ProcessDefinitionVo> getPagerModel(ProcessDefinitionQueryVo params,Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        Page<ProcessDefinitionVo> pagerModel = flowableProcessDefinitionDao.getPagerModel(params);
        return new PagerModel<>(pagerModel.getTotal(), pagerModel.getResult());
    }


}
