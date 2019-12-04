package com.dragon.flow.vo.flowable.ret;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : IdentitylinkVo
 * @projectName : flowable
 * @description: 关联审批表
 * @date : 2019/12/411:27
 */
public class IdentitylinkVo implements Serializable {

    private String id;
    private String type;
    private String groupId;
    private String userId;
    private String taskId;
    private String processInstanceId;
    private String processDefinitionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }
}
