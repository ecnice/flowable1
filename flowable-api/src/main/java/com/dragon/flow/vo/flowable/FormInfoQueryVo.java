package com.dragon.flow.vo.flowable;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : FormInfoQueryVo
 * @projectName : flowable
 * @description: formInfo查询参数
 * @date : 2019/11/2314:09
 */
public class FormInfoQueryVo implements Serializable {

    /**
     * 任务id
     */
    private String taskId;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 表单id
     */
    private String businessKey;

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

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
