package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.*;
import com.dragon.flow.vo.flowable.ret.FlowNodeVo;
import com.dragon.flow.vo.flowable.ret.TaskVo;
import com.dragon.flow.vo.flowable.ret.UserVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.List;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 运行时的任务service
 * @date : 2019/11/1315:05
 */
public interface IFlowableTaskService {

    /**
     * 驳回任意节点 暂时没有考虑子流程
     *
     * @param backTaskVo 参数
     * @return
     */
    public ReturnVo<String> backToStepTask(BackTaskVo backTaskVo);

    /**
     * 获取可驳回节点列表
     * @param taskId 任务id
     * @param processInstanceId 流程实例id
     * @return
     */
    public List<FlowNodeVo> getBackNodesByProcessInstanceId(String processInstanceId,String taskId) ;
    /**
     * 任务前加签 （如果多次加签只能显示第一次前加签的处理人来处理任务）
     *
     * @param addSignTaskVo 参数
     * @return
     */
    public ReturnVo<String> beforeAddSignTask(AddSignTaskVo addSignTaskVo);

    /**
     * 任务后加签
     *
     * @param addSignTaskVo 参数
     * @return
     */
    public ReturnVo<String> afterAddSignTask(AddSignTaskVo addSignTaskVo);

    /**
     * 任务加签
     *
     * @param addSignTaskVo 参数
     * @param flag  true向后加签  false向前加签
     * @return
     */
    public ReturnVo<String> addSignTask(AddSignTaskVo addSignTaskVo, Boolean flag);

    /**
     * 反签收任务
     *
     * @param claimTaskVo 参数
     * @return
     */
    public ReturnVo<String> unClaimTask(ClaimTaskVo claimTaskVo);

    /**
     * 签收任务
     *
     * @param claimTaskVo 参数
     * @return
     */
    public ReturnVo<String> claimTask(ClaimTaskVo claimTaskVo);

    /**
     * 委派任务
     *
     * @param delegateTaskVo 参数
     * @return
     */
    public ReturnVo<String> delegateTask(DelegateTaskVo delegateTaskVo);

    /**
     * 转办
     *
     * @param turnTaskVo 转办任务VO
     * @return 返回信息
     */
    public ReturnVo<String> turnTask(TurnTaskVo turnTaskVo);

    /**
     * 执行任务
     *
     * @param params 参数
     */
    public ReturnVo<String> complete(CompleteTaskVo params);

    /**
     * 通过任务id获取任务对象
     *
     * @param taskId 任务id
     * @return
     */
    public ReturnVo<Task> findTaskById(String taskId);

    /**
     * 查询待办任务列表
     *
     * @param params 参数
     * @return
     */
    public PagerModel<TaskVo> getApplyingTasks(TaskQueryVo params, Query query);

    /**
     * 查询已办任务列表
     *
     * @param params 参数
     * @return
     */
    public PagerModel<TaskVo> getApplyedTasks(TaskQueryVo params, Query query);

    /**
     * 通过流程实例id获取流程实例的待办任务审批人列表
     *
     * @param processInstanceId 流程实例id
     * @return
     */
    public List<User> getApprovers(String processInstanceId);

    /**
     * 通过任务id判断当前节点是不是并行网关的节点
     * @param taskId 任务id
     * @return
     */
    public boolean checkParallelgatewayNode(String taskId) ;
}
