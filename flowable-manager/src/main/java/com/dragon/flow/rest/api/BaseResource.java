package com.dragon.flow.rest.api;

import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;

/**
 * @author : bruce.liu
 * @title: : BaseResource
 * @projectName : flowable
 * @description: TODO
 * @date : 2019/11/2216:18
 */
public class BaseResource {

    public User getLoginUser() {
        User user = SecurityUtils.getCurrentUserObject();
        return user;
    }
}
