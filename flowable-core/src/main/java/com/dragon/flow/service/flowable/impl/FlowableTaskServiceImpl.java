package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.dao.flowable.IFlowableTaskDao;
import com.dragon.flow.service.flowable.IFlowableTaskService;
import com.dragon.flow.vo.flowable.CompleteTaskVo;
import com.dragon.flow.vo.flowable.DelegateTaskVo;
import com.dragon.flow.vo.flowable.TaskQueryVo;
import com.dragon.flow.vo.flowable.TurnTaskVo;
import com.dragon.flow.vo.flowable.ret.TaskVo;
import com.dragon.flow.vo.flowable.ret.UserVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.entitylink.api.EntityLink;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.idm.api.User;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : bruce.liu
 * @title: : FlowableTaskServiceImpl
 * @projectName : flowable
 * @description: task service
 * @date : 2019/11/1315:15
 */
@Service
public class FlowableTaskServiceImpl extends BaseProcessService implements IFlowableTaskService {

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
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "审批成功");
        if (StringUtils.isNotBlank(params.getProcessInstanceId())
                && StringUtils.isNotBlank(params.getTaskId())) {
            //1.添加审批意见
            this.addComment(params.getTaskId(), params.getUserCode(), params.getProcessInstanceId(),
                    params.getType(), params.getMessage());
            Task task = taskService.createTaskQuery().taskId(params.getTaskId()).singleResult();
            if (task != null) {
                //2.委派处理
                if (DelegationState.PENDING.equals(task.getDelegationState())) {
                    taskService.resolveTask(params.getTaskId(), params.getVariables());
                } else {
                    //3.1修改执行人
                    taskService.setAssignee(params.getTaskId(), params.getUserCode());
                    //3.2执行任务
                    taskService.complete(params.getTaskId(), params.getVariables());
                }
                //TODO 4.处理加签的逻辑
            } else {
                returnVo = new ReturnVo<>(ReturnCode.FAIL, "没有此任务，请确认!");
            }
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "请输入正确的参数!");
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
        return new PagerModel<>(applyingTasks);
    }

    @Override
    public PagerModel<TaskVo> getApplyedTasks(TaskQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        Page<TaskVo> applyedTasks = flowableTaskDao.getApplyedTasks(params);
        return new PagerModel<>(applyedTasks);
    }

    @Override
    public List<User> getApprovers(String processInstanceId) {
        List<User> users = new ArrayList<>();
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(task -> {
                if (StringUtils.isNotBlank(task.getAssignee())) {
                    //1.审批人ASSIGNEE_是用户id
                    User user = identityService.createUserQuery().userId(task.getAssignee()).singleResult();
                    users.add(user);
                    //2.审批人ASSIGNEE_是组id
                    List<User> gusers = identityService.createUserQuery().memberOfGroup(task.getAssignee()).list();
                    if (CollectionUtils.isNotEmpty(gusers)) {
                        users.addAll(gusers);
                    }
                } else {
                    List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
                    if (CollectionUtils.isNotEmpty(identityLinks)) {
                        identityLinks.forEach(identityLink -> {
                            //3.审批人ASSIGNEE_为空,用户id
                            if (StringUtils.isNotBlank(identityLink.getUserId())) {
                                User user = identityService.createUserQuery().userId(task.getAssignee()).singleResult();
                                users.add(user);
                            } else {
                                //4.审批人ASSIGNEE_为空,组id
                                List<User> gusers = identityService.createUserQuery().memberOfGroup(identityLink.getGroupId()).list();
                                if (CollectionUtils.isNotEmpty(gusers)) {
                                    users.addAll(gusers);
                                }
                            }
                        });
                    }
                }
            });
        }
        return users;
    }
}
