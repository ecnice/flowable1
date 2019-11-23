package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableTaskService;
import com.dragon.flow.vo.flowable.CompleteTaskVo;
import com.dragon.flow.vo.flowable.DelegateTaskVo;
import com.dragon.flow.vo.flowable.TaskQueryVo;
import com.dragon.flow.vo.flowable.TurnTaskVo;
import com.dragon.flow.vo.flowable.ret.TaskVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dragon.flow.dao.flowable.IFlowableTaskDao;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : FlowableTaskServiceImpl
 * @projectName : flowable
 * @description: task service
 * @date : 2019/11/1315:15
 */
@Service
public class FlowableTaskServiceImpl implements IFlowableTaskService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IFlowableTaskDao flowableTaskDao;

    @Override
    public ReturnVo<String> delegateTask(DelegateTaskVo delegateTaskVo) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
        taskService.addComment(delegateTaskVo.getTaskId(), delegateTaskVo.getProcessInstanceId(), delegateTaskVo.getMessage());
        taskService.delegateTask(delegateTaskVo.getTaskId(), delegateTaskVo.getUserCode());
        return returnVo;
    }

    @Override
    public ReturnVo<String> turnTask(TurnTaskVo turnTaskVo) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
        taskService.addComment(turnTaskVo.getTaskId(), turnTaskVo.getProcessInstanceId(), turnTaskVo.getMessage());
        taskService.setAssignee(turnTaskVo.getTaskId(), turnTaskVo.getTurnToUserId());
        taskService.setOwner(turnTaskVo.getTaskId(), turnTaskVo.getTurnUserId());
        return returnVo;
    }

    @Override
    public ReturnVo<String> complete(CompleteTaskVo params) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
        //1.添加审批意见
        taskService.addComment(params.getTaskId(), params.getProcessInstanceId(), params.getMessage());
        ReturnVo<Task> taskVo = findTaskById(params.getTaskId());
        if (taskVo != null) {
            Task task = taskVo.getData();
            if (task != null) {
                if (DelegationState.PENDING.equals(task.getDelegationState())) {
                    taskService.resolveTask(params.getTaskId());
                } else {
                    //2.修改执行人
                    taskService.setAssignee(params.getTaskId(), params.getUserCode());
                    //3.执行任务
                    taskService.complete(params.getTaskId());
                }
            }
        }
        return returnVo;
    }

    @Override
    public ReturnVo<Task> findTaskById(String taskId) {
        ReturnVo<Task> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "OK");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        returnVo.setData(task);
        return returnVo;
    }

    @Override
    public PagerModel<TaskVo> getApplyingTasks(TaskQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        Page<TaskVo> applyingTasks = flowableTaskDao.getApplyingTasks(params);
        return new PagerModel<>(applyingTasks.getTotal(), applyingTasks.getResult());
    }

    @Override
    public PagerModel<TaskVo> getApplyedTasks(TaskQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        Page<TaskVo> applyingTasks = flowableTaskDao.getApplyedTasks(params);
        return new PagerModel<>(applyingTasks.getTotal(), applyingTasks.getResult());
    }
}
