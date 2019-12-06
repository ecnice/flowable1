package com.dragon.flow.vo.flowable;

/**
 * @Description: 驳回的实体VO
 * @Author: Bruce.liu
 * @Since:9:19 2018/9/8
 * 爱拼才会赢 2018 ~ 2030 版权所有
 */
public class BackTaskVo extends BaseProcessVo {

    /**
     * 需要驳回的节点id 必填
     */
    private String distFlowElementId;

    public String getDistFlowElementId() {
        return distFlowElementId;
    }

    public void setDistFlowElementId(String distFlowElementId) {
        this.distFlowElementId = distFlowElementId;
    }
}
