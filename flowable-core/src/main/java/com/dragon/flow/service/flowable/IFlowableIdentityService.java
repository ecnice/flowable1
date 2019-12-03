package com.dragon.flow.service.flowable;

import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 用户组
 * @date : 2019/11/1411:46
 */
public interface IFlowableIdentityService {
    /**
     * 添加用户
      * @param user
     */
    public void saveUser(User user) ;

    /**
     * 添加组
     * @param group
     */
    public void saveGroup(Group group) ;



}
