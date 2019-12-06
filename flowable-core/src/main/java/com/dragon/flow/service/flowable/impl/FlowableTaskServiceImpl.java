package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.constant.FlowConstant;
import com.dragon.flow.dao.flowable.IFlowableTaskDao;
import com.dragon.flow.enm.flowable.CommentTypeEnum;
import com.dragon.flow.service.flowable.IFlowableBpmnModelService;
import com.dragon.flow.service.flowable.IFlowableTaskService;
import com.dragon.flow.vo.flowable.*;
import com.dragon.flow.vo.flowable.ret.FlowNodeVo;
import com.dragon.flow.vo.flowable.ret.TaskVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.Activity;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.idm.api.User;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private IFlowableBpmnModelService flowableBpmnModelService;

    @Override
    public ReturnVo<String> backToStepTask(BackTaskVo backTaskVo) {
        ReturnVo<String> returnVo = null;
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(backTaskVo.getTaskId()).singleResult();
        //1.把当前的节点设置为空
        if (taskEntity != null) {
            //2.设置审批人
            taskEntity.setAssignee(backTaskVo.getUserCode());
            taskService.saveTask(taskEntity);
            //3.添加驳回意见
            this.addComment(backTaskVo.getTaskId(), backTaskVo.getUserCode(), backTaskVo.getProcessInstanceId(),
                    CommentTypeEnum.BH.toString(), backTaskVo.getMessage());
            //4.处理提交人节点
            Activity distActivity = flowableBpmnModelService.findMainProcessActivityByActivityId(taskEntity.getProcessDefinitionId(), backTaskVo.getDistFlowElementId());
            if (distActivity != null) {
                if (FlowConstant.FLOW_SUBMITTER.equals(distActivity.getName())){
                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
                    runtimeService.setVariable(backTaskVo.getProcessInstanceId(), FlowConstant.FLOW_SUBMITTER_VAR, processInstance.getStartUserId());
                }
            }
            //5.执行驳回 TODO 这里并没有考虑子流程
            List<Execution> executions = runtimeService.createExecutionQuery().parentId(taskEntity.getProcessInstanceId()).list();
            List<String> executionIds = new ArrayList<>();
            executions.forEach(execution -> executionIds.add(execution.getId()));
            runtimeService.createChangeActivityStateBuilder()
                    .moveExecutionsToSingleActivityId(executionIds, backTaskVo.getDistFlowElementId())
                    .changeState();
            //6.删除节点 TODO 这里并没有考虑子流程
            this.deleteActivity(backTaskVo.getDistFlowElementId(),taskEntity.getProcessInstanceId());
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "不存在任务实例,请确认!");
        }
        return returnVo;
    }

    @Override
    public List<FlowNodeVo> getBackNodesByProcessInstanceId(String processInstanceId) {
        List<FlowNodeVo> datas = new ArrayList<>();
        Map<String, FlowNodeVo> nodeMap = new HashMap<>();
        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
                .activityType(BpmnXMLConstants.ELEMENT_TASK_USER).processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceEndTime().desc().finished().list();
        if (CollectionUtils.isNotEmpty(activityInstances)) {
            activityInstances.forEach(activityInstance -> {
                if (!nodeMap.containsKey(activityInstance.getActivityId())) {
                    FlowNodeVo node = new FlowNodeVo();
                    node.setNodeId(activityInstance.getActivityId());
                    node.setNodeName(activityInstance.getActivityName());
                    node.setEndTime(activityInstance.getEndTime());
                    nodeMap.put(activityInstance.getActivityId(), node);
                    datas.add(node);
                }
            });
        }
        return datas;
    }

    @Override
    public ReturnVo<String> beforeAddSignTask(AddSignTaskVo addSignTaskVo) {
        return this.addSignTask(addSignTaskVo, false);
    }

    @Override
    public ReturnVo<String> afterAddSignTask(AddSignTaskVo addSignTaskVo) {
        return this.addSignTask(addSignTaskVo, true);
    }

    @Override
    public ReturnVo<String> addSignTask(AddSignTaskVo addSignTaskVo, Boolean flag) {
        ReturnVo<String> returnVo = null;
        TaskEntityImpl taskEntity = (TaskEntityImpl) taskService.createTaskQuery().taskId(addSignTaskVo.getTaskId()).singleResult();
        //1.把当前的节点设置为空
        if (taskEntity != null) {
            //如果是加签再加签
            String parentTaskId = taskEntity.getParentTaskId();
            if (StringUtils.isBlank(parentTaskId)) {
                taskEntity.setOwner(addSignTaskVo.getUserCode());
                taskEntity.setAssignee(null);
                taskEntity.setCountEnabled(true);
                if (flag) {
                    taskEntity.setScopeType(FlowConstant.AFTER_ADDSIGN);
                } else {
                    taskEntity.setScopeType(FlowConstant.BEFORE_ADDSIGN);
                }
                //1.2 设置任务为空执行者
                taskService.saveTask(taskEntity);
            }
            //2.添加加签表中
            this.createSignSubTasks(addSignTaskVo, taskEntity);
            //3.添加审批意见
            String type = flag ? CommentTypeEnum.HJQ.toString() : CommentTypeEnum.QJQ.toString();
            this.addComment(addSignTaskVo.getTaskId(), addSignTaskVo.getUserCode(), addSignTaskVo.getProcessInstanceId(),
                    type, addSignTaskVo.getMessage());
            String message = flag ? "后加签成功" : "前加签成功";
            returnVo = new ReturnVo<>(ReturnCode.SUCCESS, message);
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "不存在任务实例，请确认!");
        }
        return returnVo;
    }

    /**
     * 创建加签子任务
     *
     * @param signVo     加签参数
     * @param taskEntity 父任务
     */
    private void createSignSubTasks(AddSignTaskVo signVo, TaskEntity taskEntity) {
        if (CollectionUtils.isNotEmpty(signVo.getSignPersoneds())) {
            String parentTaskId = taskEntity.getParentTaskId();
            if (StringUtils.isBlank(parentTaskId)) {
                parentTaskId = taskEntity.getId();
            }
            String finalParentTaskId = parentTaskId;
            //1.创建被加签人的任务列表
            signVo.getSignPersoneds().forEach(userCode -> {
                if (StringUtils.isNotBlank(userCode)) {
                    this.createSubTask(taskEntity, finalParentTaskId, userCode);
                }
            });
            String taskId = taskEntity.getId();
            if (StringUtils.isBlank(taskEntity.getParentTaskId())) {
                //2.创建加签人的任务并执行完毕
                Task task = this.createSubTask(taskEntity, finalParentTaskId, signVo.getUserCode());
                taskId = task.getId();
            }
            Task taskInfo = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (null != taskInfo) {
                taskService.complete(taskId);
            }
            //如果是候选人，需要删除运行时候选表种的数据。
            long candidateCount = taskService.createTaskQuery().taskId(parentTaskId).taskCandidateUser(signVo.getUserCode()).count();
            if (candidateCount > 0) {
                taskService.deleteCandidateUser(parentTaskId, signVo.getUserCode());
            }
        }
    }

    @Override
    public ReturnVo<String> unClaimTask(ClaimTaskVo claimTaskVo) {
        ReturnVo<String> returnVo = null;
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(claimTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.添加审批意见
            this.addComment(claimTaskVo.getTaskId(), claimTaskVo.getProcessInstanceId(), CommentTypeEnum.QS.toString(), claimTaskVo.getMessage());
            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(claimTaskVo.getTaskId());
            boolean flag = false;
            if (CollectionUtils.isNotEmpty(identityLinks)) {
                for (IdentityLink link : identityLinks) {
                    if (IdentityLinkType.CANDIDATE.equals(link.getTaskId())) {
                        flag = true;
                        break;
                    }
                }
            }
            //2.签收
            if (flag) {
                taskService.claim(claimTaskVo.getTaskId(), null);
                returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "签收成功");
            } else {
                returnVo = new ReturnVo<>(ReturnCode.FAIL, "由于没有候选人或候选组,会导致任务无法认领,请确认.");
            }
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "签收失败");
        }
        return returnVo;
    }

    @Override
    public ReturnVo<String> claimTask(ClaimTaskVo claimTaskVo) {
        ReturnVo<String> returnVo = null;
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(claimTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.添加审批意见
            this.addComment(claimTaskVo.getTaskId(), claimTaskVo.getProcessInstanceId(), CommentTypeEnum.QS.toString(), claimTaskVo.getMessage());
            //2.签收
            taskService.claim(claimTaskVo.getTaskId(), claimTaskVo.getUserCode());
            returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "签收成功");
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "签收失败");
        }
        return returnVo;
    }

    @Override
    public ReturnVo<String> delegateTask(DelegateTaskVo delegateTaskVo) {
        ReturnVo<String> returnVo = null;
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(delegateTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.添加审批意见
            this.addComment(delegateTaskVo.getTaskId(), delegateTaskVo.getUserCode(), delegateTaskVo.getProcessInstanceId(), CommentTypeEnum.WP.toString(), delegateTaskVo.getMessage());
            //2.设置审批人就是当前登录人
            taskService.setAssignee(delegateTaskVo.getTaskId(), delegateTaskVo.getUserCode());
            //3.执行委派
            taskService.delegateTask(delegateTaskVo.getTaskId(), delegateTaskVo.getDelegateUserCode());
            returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "委派成功");
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "没有运行时的任务实例,请确认!");
        }
        return returnVo;
    }

    @Override
    public ReturnVo<String> turnTask(TurnTaskVo turnTaskVo) {
        ReturnVo<String> returnVo = null;
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(turnTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.生成历史记录
            TaskEntity task = this.createSubTask(currTask, turnTaskVo.getUserCode());
            //2.添加审批意见
            this.addComment(task.getId(), turnTaskVo.getUserCode(), turnTaskVo.getProcessInstanceId(), CommentTypeEnum.ZB.toString(), turnTaskVo.getMessage());
            taskService.complete(task.getId());
            //3.转办
            taskService.setAssignee(turnTaskVo.getTaskId(), turnTaskVo.getTurnToUserId());
            taskService.setOwner(turnTaskVo.getTaskId(), turnTaskVo.getUserCode());
            returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "转办成功");
        } else {
            returnVo = new ReturnVo<>(ReturnCode.FAIL, "没有运行时的任务实例,请确认!");
        }
        return returnVo;
    }

    @Override
    public ReturnVo<String> complete(CompleteTaskVo params) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS, "审批成功");
        if (StringUtils.isNotBlank(params.getProcessInstanceId())
                && StringUtils.isNotBlank(params.getTaskId())) {
            //1.查看当前任务是存在
            TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(params.getTaskId()).singleResult();
            if (taskEntity != null) {
                String taskId = params.getTaskId();
                //2.委派处理
                if (DelegationState.PENDING.equals(taskEntity.getDelegationState())) {
                    //2.1生成历史记录
                    TaskEntity task = this.createSubTask(taskEntity, params.getUserCode());
                    taskService.complete(task.getId());
                    taskId = task.getId();
                    //2.2执行委派
                    taskService.resolveTask(params.getTaskId(), params.getVariables());
                } else {
                    //3.1修改执行人 其实我这里就相当于签收了
                    taskService.setAssignee(params.getTaskId(), params.getUserCode());
                    //3.2执行任务
                    taskService.complete(params.getTaskId(), params.getVariables());
                    //4.处理加签父任务
                    String parentTaskId = taskEntity.getParentTaskId();
                    if (StringUtils.isNotBlank(parentTaskId)) {
                        String tableName = managementService.getTableName(TaskEntity.class);
                        String sql = "select count(1) from " + tableName + " where PARENT_TASK_ID_=#{parentTaskId}";
                        long subTaskCount = taskService.createNativeTaskQuery().sql(sql).parameter("parentTaskId", parentTaskId).count();
                        if (subTaskCount == 0) {
                            Task task = taskService.createTaskQuery().taskId(parentTaskId).singleResult();
                            //处理前后加签的任务
                            taskService.resolveTask(parentTaskId);
                            if (FlowConstant.AFTER_ADDSIGN.equals(task.getScopeType())) {
                                taskService.complete(parentTaskId);
                            }
                        }
                    }
                }
                String type = params.getType() == null ? CommentTypeEnum.SP.toString() : params.getType();
                //5.生成审批意见
                this.addComment(taskId, params.getUserCode(), params.getProcessInstanceId(), type, params.getMessage());
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
                    if (user != null) {
                        users.add(user);
                    }
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
                                User user = identityService.createUserQuery().userId(identityLink.getUserId()).singleResult();
                                if (user != null) {
                                    users.add(user);
                                }
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
