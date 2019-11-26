package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.ret.CommentVo;

import java.util.List;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 审批意见service
 * @date : 2019/11/2412:55
 */
public interface IFlowableCommentService {

    /**
     * 添加备注
     * @param comment 参数
     */
    public void addComment(CommentVo comment) ;

    /**
     * 通过流程实例id获取审批意见列表
     * @param processInstanceId 流程实例id
     * @return
     */
    public List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId) ;

}
