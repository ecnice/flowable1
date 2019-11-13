package com.dragon.flow.vo.flowable;


import java.util.List;

/**
 * @author : bruce.liu
 * @title: : DelegateTaskVo
 * @projectName : flowable
 * @description: 委派
 * @date : 2019/11/1315:51
 */
public class DelegateTaskVo extends BaseProcessVo {

    /**
     * 委派人
     */
    private String userCode;

    @Override
    public String getUserCode() {
        return userCode;
    }

    @Override
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
