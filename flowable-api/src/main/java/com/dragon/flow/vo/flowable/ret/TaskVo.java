package com.dragon.flow.vo.flowable.ret;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : bruce.liu
 * @title: : TaskVo
 * @projectName : flowable
 * @description: 任务vo
 * @date : 2019/11/2316:09
 */
public class TaskVo implements Serializable {
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 审批人
     */
    private String approver;
    /**
     * 审批人id
     */
    private String approverId;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 业务主键
     */
    private String businessKey;
    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 开始时间
     */
    private Date startTime ;

    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 系统标识
     */
    private String systemSn;
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSystemSn() {
        return systemSn;
    }

    public void setSystemSn(String systemSn) {
        this.systemSn = systemSn;
    }
}
