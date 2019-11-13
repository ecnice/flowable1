package com.dragon.flow.vo.flowable;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : TaskVo
 * @projectName : flowable
 * @description: 任务VO
 * @date : 2019/11/1315:11
 */
public class TaskQueryVo implements Serializable {

    /**
     * 用户工号
     */
    private String userCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
