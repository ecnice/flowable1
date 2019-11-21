package com.dragon.flow.rest.api;

import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.service.flowable.IFlowableTaskService;
import com.dragon.flow.vo.flowable.ProcessInstanceQueryVo;
import com.dragon.flow.vo.flowable.TaskQueryVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : bruce.liu
 * @title: : ApiFlowableTaskResource
 * @projectName : flowable
 * @description: 任务列表
 * @date : 2019/11/2115:34
 */
@RestController
@RequestMapping("/rest/task")
public class ApiFlowableTaskResource {

    @Autowired
    private IFlowableTaskService flowableTaskService;
    @Autowired
    private IFlowableProcessInstanceService flowableProcessInstanceService;

    /**
     * 获取待办任务列表
     *
     * @param params 参数
     * @param query  查询条件
     * @return
     */
    @GetMapping(value = "/get-applying-tasks")
    public PagerModel<Task> getApplyingTasks(TaskQueryVo params, Query query) {
        PagerModel<Task> pm = flowableTaskService.getApplyingTasks(params, query);
        return pm;
    }

    /**
     * 获取已办任务列表
     *
     * @param params 参数
     * @param query  查询条件
     * @return
     */
    @GetMapping(value = "/get-applyed-tasks")
    public PagerModel<HistoricTaskInstance> getApplyedTasks(TaskQueryVo params, Query query) {
        PagerModel<HistoricTaskInstance> pm = flowableTaskService.getApplyedTasks(params, query);
        return pm;
    }

    /**
     * 获取已办任务列表
     *
     * @param params 参数
     * @param query  查询条件
     * @return
     */
    @GetMapping(value = "/my-processInstances")
    public PagerModel<HistoricProcessInstance> myProcessInstances(ProcessInstanceQueryVo params, Query query) {
        PagerModel<HistoricProcessInstance> pm = flowableProcessInstanceService.getMyProcessInstances(params, query);
        return pm;
    }


}
