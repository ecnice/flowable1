package com.dragon.flow.vo.flowable;

import java.io.Serializable;

/**
 * @Description: 流程执行过程中的基本参数VO
 * @Author: Bruce.liu
 */
public class BaseProcessVo implements Serializable {
    /**********************任务相关的参数**********************/
    /**
     * 任务id 必填
     */
    private String taskId;
    /**********************审批意见的参数**********************/
    /**
     * 操作人code 必填
     */
    private String userCode;
    /**
     * 审批意见 必填
     */
    private String message;
    /**
     * 流程实例的id 必填
     */
    private String processInstanceId;
    /**
     * 审批类型 必填
     */
    private String type;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
