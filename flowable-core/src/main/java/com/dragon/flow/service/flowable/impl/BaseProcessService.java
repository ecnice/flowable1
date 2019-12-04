package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.dao.flowable.IHisFlowableActinstDao;
import com.dragon.flow.dao.flowable.IRunFlowableActinstDao;
import com.dragon.flow.enm.flowable.CommentTypeEnum;
import com.dragon.flow.service.flowable.IFlowableCommentService;
import com.dragon.flow.vo.flowable.ret.CommentVo;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.*;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntity;
import org.flowable.engine.runtime.ActivityInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : bruce.liu
 * @title: : BaseProcessService
 * @projectName : flowable
 * @description: 基本的流程service
 * @date : 2019/12/220:50
 */
public abstract class BaseProcessService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected IdentityService identityService;
    @Autowired
    protected IFlowableCommentService flowableCommentService;
    @Autowired
    private IRunFlowableActinstDao runFlowableActinstDao;
    @Autowired
    private IHisFlowableActinstDao hisFlowableActinstDao;

    /**
     * 添加审批意见
     * @param taskId 任务id
     * @param userCode 处理人工号
     * @param processInstanceId 流程实例id
     * @param type 审批类型
     * @param message 审批意见
     */
    public void addComment(String taskId,String userCode,String processInstanceId,String type,String message) {
        //1.添加备注
        CommentVo commentVo = new CommentVo(taskId,userCode, processInstanceId,type,message);
        flowableCommentService.addComment(commentVo);
        //TODO 2.修改扩展的流程实例表的状态以备查询待办的时候能带出来状态
        //TODO 3.发送mongodb的数据到消息队列里面
    }

    /**
     * 添加审批意见
     * @param userCode 处理人工号
     * @param processInstanceId 流程实例id
     * @param type 审批类型
     * @param message 审批意见
     */
    public void addComment(String userCode,String processInstanceId,String type,String message) {
        this.addComment(null,userCode, processInstanceId,type,message);
    }

    /**
     * 删除跳转的历史节点信息
     * @param disActivityId 跳转的节点id
     * @param processInstanceId 流程实例id
     */
    protected void deleteHisActivity(String disActivityId,String processInstanceId) {
        List<ActivityInstance> disActivities = runtimeService.createActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityId(disActivityId).list();
        //删除运行时和李四节点信息
        if (CollectionUtils.isNotEmpty(disActivities)) {
            ActivityInstance activityInstance = disActivities.get(0);
            String tableName = managementService.getTableName(ActivityInstanceEntity.class);
            String sql = "select t.* from " + tableName + " t where t.PROC_INST_ID_=#{processInstanceId} and (t.END_TIME_ >= #{endTime} or t.END_TIME_ is null)";
            List<ActivityInstance> datas = runtimeService.createNativeActivityInstanceQuery().sql(sql).parameter("processInstanceId", processInstanceId)
                    .parameter("endTime", activityInstance.getEndTime()).list();
            List<String> runActivityIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(datas)) {
                datas.forEach(ai -> runActivityIds.add(ai.getId()));
                runFlowableActinstDao.deleteRunActinstsByIds(runActivityIds);
                hisFlowableActinstDao.deleteHisActinstsByIds(runActivityIds);
            }
        }
    }

}
