package com.dragon.flow.vo.flowable;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : TurnTaskVo
 * @projectName : flowable
 * @description: 转办Vo
 * @date : 2019/11/1315:34
 */
public class TurnTaskVo extends BaseProcessVo {

    /**
     * 被转办人工号 必填
     */
    private String turnToUserId;
    /**
     * 转办人工号 必填
     */
    private String turnUserId;

    public String getTurnToUserId() {
        return turnToUserId;
    }

    public void setTurnToUserId(String turnToUserId) {
        this.turnToUserId = turnToUserId;
    }

    public String getTurnUserId() {
        return turnUserId;
    }

    public void setTurnUserId(String turnUserId) {
        this.turnUserId = turnUserId;
    }
}
