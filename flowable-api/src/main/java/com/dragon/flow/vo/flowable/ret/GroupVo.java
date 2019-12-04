package com.dragon.flow.vo.flowable.ret;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : GroupVo
 * @projectName : flowable
 * @description: 组的VO
 * @date : 2019/12/313:59
 */
public class GroupVo implements Serializable {

    /**
     * 组的id
     */
    private String id;
    /**
     * 组的名称
     */
    private String groupName;
    /****************************扩展字段****************************/
    /**
     * 组的标识
     */
    private String groupSn;

}
