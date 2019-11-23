package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.CompleteTaskVo;
import com.dragon.flow.vo.flowable.DelegateTaskVo;
import com.dragon.flow.vo.flowable.TaskQueryVo;
import com.dragon.flow.vo.flowable.TurnTaskVo;
import com.dragon.flow.vo.flowable.ret.TaskVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 运行时的任务service
 * @date : 2019/11/1315:05
 */
public interface IFlowableTaskService {

    /**
     * 委派任务
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
     * @param params 参数
     */
    public ReturnVo<String> complete(CompleteTaskVo params) ;

    /**
     * 通过任务id获取任务对象
     * @param taskId 任务id
     * @return
     */
    public ReturnVo<Task> findTaskById(String taskId);

    /**
     * 查询待办任务列表
     * @param params 参数
     * @return
     */
    public PagerModel<TaskVo> getApplyingTasks(TaskQueryVo params, Query  query) ;

    /**
     * 查询已办任务列表
     * @param params 参数
     * @return
     */
    public PagerModel<TaskVo> getApplyedTasks(TaskQueryVo params, Query  query) ;
}
