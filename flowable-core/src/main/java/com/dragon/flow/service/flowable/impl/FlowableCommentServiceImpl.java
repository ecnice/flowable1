package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.cmd.AddHisCommentCmd;
import com.dragon.flow.dao.flowable.IFlowableCommentDao;
import com.dragon.flow.enm.flowable.CommentTypeEnum;
import com.dragon.flow.service.flowable.IFlowableCommentService;
import com.dragon.flow.vo.flowable.ret.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : FlowCommentServiceImpl
 * @projectName : flowable
 * @description: 流程备注service
 * @date : 2019/11/2412:58
 */
@Service
public class FlowableCommentServiceImpl extends BaseProcessService implements IFlowableCommentService {

    @Autowired
    private IFlowableCommentDao flowableCommentDao;

    @Override
    public void addComment(CommentVo comment) {
        managementService.executeCommand(new AddHisCommentCmd(comment.getTaskId(), comment.getUserId(), comment.getProcessInstanceId(),
                comment.getType(), comment.getMessage()));
    }

    @Override
    public List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId) {
        List<CommentVo> datas = flowableCommentDao.getFlowCommentVosByProcessInstanceId(processInstanceId);
        datas.forEach(commentVo -> {
            commentVo.setTypeName(CommentTypeEnum.getEnumMsgByType(commentVo.getType()));
        });
        return datas;
    }
}
