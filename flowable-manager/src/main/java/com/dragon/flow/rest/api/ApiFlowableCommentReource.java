package com.dragon.flow.rest.api;

import com.dragon.flow.service.flowable.IFlowableCommentService;
import com.dragon.flow.vo.flowable.ret.FlowCommentVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : ApiFlowCommentReource
 * @projectName : flowable
 * @description: 备注
 * @date : 2019/11/2413:13
 */
@RestController
@RequestMapping("/rest/comment")
public class ApiFlowableCommentReource extends BaseResource{

    @Autowired
    private IFlowableCommentService flowableCommentService;

    /**
     * 添加备注
     * @param comment 备注
     * @return
     */
    @PostMapping("/addComment")
    public ReturnVo<String> addComment(FlowCommentVo comment){
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        flowableCommentService.addComment(comment);
        return returnVo;
    }

    /**
     * 通过流程实例id获取审批意见
     * @param processInstanceId 流程实例id
     * @return
     */
    @GetMapping("/listByProcessInstanceId")
    public List<FlowCommentVo> listByProcessInstanceId(String processInstanceId) {
        List<FlowCommentVo> datas = flowableCommentService.getFlowCommentVosByProcessInstanceId(processInstanceId);
        return datas;
    }
}
