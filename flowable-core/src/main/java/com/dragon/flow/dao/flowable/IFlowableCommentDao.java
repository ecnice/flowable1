package com.dragon.flow.dao.flowable;

import com.dragon.flow.vo.flowable.ret.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 流程备注Dao
 * @date : 2019/11/2413:00
 */
@Mapper
@Repository
public interface IFlowableCommentDao {

    /**
     * 通过流程实例id获取审批意见列表
     * @param ProcessInstanceId 流程实例id
     * @return
     */
    public List<CommentVo> getFlowCommentVosByProcessInstanceId(String ProcessInstanceId);

}
