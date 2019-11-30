package com.dragon.flow.rest.api;

import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : bruce.liu
 * @title: : BaseResource
 * @projectName : flowable
 * @description: TODO
 * @date : 2019/11/2216:18
 */
public class BaseResource {

    @Autowired
    protected RuntimeService runtimeService;

    public User getLoginUser() {
        User user = SecurityUtils.getCurrentUserObject();
        return user;
    }

    /**
     * 判断是否挂起状态
     * @param processInstanceId 流程实例id
     * @return
     */
    public boolean isSuspended(String processInstanceId) {
        boolean flag = true;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance != null){
            flag = !processInstance.isSuspended();
        }
        return flag;
    }
}
