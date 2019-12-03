package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableIdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.stereotype.Service;

/**
 * @author : bruce.liu
 * @title: : FlowableIdentityServiceImpl
 * @projectName : flowable
 * @description: 用户组service
 * @date : 2019/11/1411:46
 */
@Service
public class FlowableIdentityServiceImpl extends BaseProcessService implements IFlowableIdentityService {

    @Override
    public void saveUser(User user) {
        identityService.saveUser(user);
    }

    @Override
    public void saveGroup(Group group) {
        identityService.saveGroup(group);
    }

}
