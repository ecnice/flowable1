package com.dragon.flow.rest.api;

import com.dragon.flow.enm.flowable.CommentTypeEnum;
import com.dragon.flow.service.flowable.IFlowableCommentService;
import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.service.flowable.IFlowableTaskService;
import com.dragon.flow.vo.flowable.CompleteTaskVo;
import com.dragon.flow.vo.flowable.DelegateTaskVo;
import com.dragon.flow.vo.flowable.TurnTaskVo;
import com.dragon.flow.vo.flowable.ret.FlowCommentVo;
import com.dragon.tools.common.DateUtil;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.ui.modeler.domain.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author : bruce.liu
 * @title: : ApiFlowCommentReource
 * @projectName : flowable
 * @description: 备注
 * @date : 2019/11/2413:13
 */
@RestController
@RequestMapping("/rest/formdetail")
public class ApiFormDetailReource extends BaseResource{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFormDetailReource.class);
    @Autowired
    private IFlowableCommentService flowableCommentService;
    @Autowired
    private IFlowableTaskService flowableTaskService;
    @Autowired
    private IFlowableProcessInstanceService flowableProcessInstanceService;

    /**
     * 通过流程实例id获取审批意见
     * @param processInstanceId 流程实例id
     * @return
     */
    @GetMapping("/commentsByProcessInstanceId")
    public List<FlowCommentVo> commentsByProcessInstanceId(String processInstanceId) {
        List<FlowCommentVo> datas = flowableCommentService.getFlowCommentVosByProcessInstanceId(processInstanceId);
//        for(int i=0; i<10;i++) {
//            FlowCommentVo flowCommentVo = new FlowCommentVo("00000001", processInstanceId, "军哥提交了一个请假的流程", CommentTypeEnum.TJ.toString());
//            flowCommentVo.setUserName("军哥");
//            flowCommentVo.setTime(DateUtil.addDate(new Date(),-i));
//            flowCommentVo.setTypeName(CommentTypeEnum.getEnumMsgByType(flowCommentVo.getType()));
//            datas.add(flowCommentVo);
//        }
        return datas;
    }

    /**
     * 审批任务
     * @param params 参数
     * @return
     */
    @PostMapping(value = "/complete")
    public ReturnVo<String> complete(CompleteTaskVo params) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        params.setUserCode(this.getLoginUser().getId());
        returnVo = flowableTaskService.complete(params);
        return returnVo;
    }

    /**
     * 转办
     * @param params 参数
     * @return
     */
    @PostMapping(value = "/turnTask")
    public ReturnVo<String> turnTask(TurnTaskVo params){
        ReturnVo<String> returnVo = null;
        params.setUserCode(this.getLoginUser().getId());
        returnVo = flowableTaskService.turnTask(params);
        return returnVo;
    }

    /**
     * 委派
     * @param params 参数
     * @return
     */
    @PostMapping(value = "/delegateTask")
    public ReturnVo<String> delegateTask(DelegateTaskVo params){
        ReturnVo<String> returnVo = null;
        params.setUserCode(this.getLoginUser().getId());
        returnVo = flowableTaskService.delegateTask(params);
        return returnVo;
    }

    @GetMapping(value = "/image/{processInstanceId}")
    public void image(@PathVariable String processInstanceId, HttpServletResponse response) {
        try {
            byte[] b = flowableProcessInstanceService.createImage(processInstanceId);
            response.setHeader("Content-type", "text/xml;charset=UTF-8");
            response.getOutputStream().write(b);
        } catch (Exception e) {
            LOGGER.error("ApiFormDetailReource-image:" + e);
            e.printStackTrace();
        }
    }
}
