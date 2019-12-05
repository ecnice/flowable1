package com.dragon.flow.vo.flowable;



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
    private String delegateUserCode;

    public String getDelegateUserCode() {
        return delegateUserCode;
    }

    public void setDelegateUserCode(String delegateUserCode) {
        this.delegateUserCode = delegateUserCode;
    }
}
