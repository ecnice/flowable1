package com.dragon.flow.vo.flowable;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : bruce.liu
 * @title: : StartProcessVo
 * @projectName : flowable
 * @description: 启动流程VO
 * @date : 2019/11/1314:50
 */
public class StartProcessInstanceVo implements Serializable {

    /**
     * 流程定义key 必填
     */
    private String processDefinitionKey;
    /**
     * 业务系统id 必填
     */
    private String businessKey;
    /**
     * 启动流程变量 选填
     */
    private Map<String, Object> variables;
    /**
     * 申请人工号 必填
     */
    private String currentUserCode;
    /**
     * 系统标识 必填
     */
    private String systemSn;
    /**
     * 表单显示名称 必填
     */
    private String formName;
    /**
     * 流程提交人工号 必填
     */
    private String creator;

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getCurrentUserCode() {
        return currentUserCode;
    }

    public void setCurrentUserCode(String currentUserCode) {
        this.currentUserCode = currentUserCode;
    }

    public String getSystemSn() {
        return systemSn;
    }

    public void setSystemSn(String systemSn) {
        this.systemSn = systemSn;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
