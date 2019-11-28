package com.dragon.flow.vo.flowable;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : QueryProcessInstanceVo
 * @projectName : flowable
 * @description: 查询流程实例VO
 * @date : 2019/11/2115:42
 */
public class ProcessInstanceQueryVo implements Serializable {

    private String formName;
    private String userCode;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }
}
