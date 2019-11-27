package com.dragon.flow.dao.flowable;

import com.dragon.flow.vo.flowable.ProcessDefinitionQueryVo;
import com.dragon.flow.vo.flowable.ProcessInstanceQueryVo;
import com.dragon.flow.vo.flowable.ret.ProcessDefinitionVo;
import com.dragon.flow.vo.flowable.ret.ProcessInstanceVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : bruce.liu
 * @title: : IFlowableProcessInstentDao
 * @projectName : flowable
 * @description: 流程定义Dao
 * @date : 2019/11/2317:09
 */
@Mapper
@Repository
public interface IFlowableProcessDefinitionDao {

    /**
     * 通过条件查询流程定义列表
     * @param params 参数
     * @return
     */
    public Page<ProcessDefinitionVo> getPagerModel(ProcessDefinitionQueryVo params) ;

    /**
     * 通过流程定义id获取流程定义的信息
     * @param processDefinitionId 流程定义id
     * @return
     */
    public ProcessDefinitionVo getById(String processDefinitionId) ;
}
