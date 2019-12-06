package com.dragon.flow.rest.api;

import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.vo.flowable.EndProcessVo;
import com.dragon.flow.vo.flowable.ProcessInstanceQueryVo;
import com.dragon.flow.vo.flowable.ret.ProcessInstanceVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : bruce.liu
 * @title: : ApiTask
 * @projectName : flowable
 * @description: 流程实例API
 * @date : 2019/11/1321:21
 */
@RestController
@RequestMapping("/rest/processInstance")
public class ApiFlowableProcessInstanceResource extends BaseResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFlowableProcessInstanceResource.class);
    @Autowired
    private IFlowableProcessInstanceService flowableProcessInstanceService;

    /**
     * 分页查询流程定义列表
     *
     * @param params 参数
     * @param query  分页
     * @return
     */
    @PostMapping(value = "/page-model")
    public PagerModel<ProcessInstanceVo> pageModel(ProcessInstanceQueryVo params, Query query) {
        PagerModel<ProcessInstanceVo> pm = flowableProcessInstanceService.getPagerModel(params, query);
        return pm;
    }

    /**
     * 删除流程实例haha
     *
     * @param processInstanceId 参数
     * @return
     */
    @GetMapping(value = "/deleteProcessInstanceById/{processInstanceId}")
    public ReturnVo<String> deleteProcessInstanceById(@PathVariable String processInstanceId) {
        ReturnVo<String> data = flowableProcessInstanceService.deleteProcessInstanceById(processInstanceId);
        return data;
    }

    /**
     * 激活或者挂起流程定义
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/saProcessInstanceById")
    public ReturnVo<String> saProcessInstanceById(String id, int suspensionState) {
        ReturnVo<String> returnVo = flowableProcessInstanceService.suspendOrActivateProcessInstanceById(id, suspensionState);
        return returnVo;
    }

    /**
     * 终止
     *
     * @param params 参数
     * @return
     */
    @PostMapping(value = "/stopProcess")
    public ReturnVo<String> stopProcess(EndProcessVo params) {
        boolean flag = this.isSuspended(params.getProcessInstanceId());
        ReturnVo<String> returnVo = null;
        if (flag){
            params.setMessage("后台执行终止");
            params.setUserCode(this.getLoginUser().getId());
            returnVo = flowableProcessInstanceService.stopProcessInstanceById(params);
        }else{
            returnVo = new ReturnVo<>(ReturnCode.FAIL,"流程已挂起，请联系管理员激活!");
        }
        return returnVo;
    }

}
