package com.dragon.flow.rest.api;

import com.dragon.flow.service.flowable.IFlowableIdentityService;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.flowable.idm.api.*;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : bruce.liu
 * @title: : ApiFlowableIdentityResource
 * @projectName : flowable
 * @description: 用户组
 * @date : 2019/12/222:24
 */
@RestController
@RequestMapping("/rest/user")
public class ApiFlowableUserResource extends BaseResource {

    @Autowired
    private IdmIdentityService idmIdentityService;
    @Autowired
    private IFlowableIdentityService flowableIdentityService;

    /**
     * 查询用户列表
     *
     * @param name 姓名
     * @return
     */
    @GetMapping("/getPagerModel")
    public PagerModel<User> getPagerModel(String name, Query query) {
        UserQuery userQuery = idmIdentityService.createUserQuery();
        if (StringUtils.isNotBlank(name)){
            userQuery.userFirstNameLike(name);
        }
        long count = userQuery.count();

        int firstResult = (query.getPageNum() - 1) * query.getPageSize();
        List<User> datas = userQuery.orderByUserFirstName().desc().listPage(firstResult, query.getPageSize());
        return new PagerModel<>(count, datas);
    }

    /**
     * 添加修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/save")
    public ReturnVo<String> save(UserEntityImpl user) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.SUCCESS, "添加成功");
        long count = idmIdentityService.createUserQuery().userId(user.getId()).count();
        flowableIdentityService.saveUser(user);
        if (count == 0) {
            Privilege privilege = idmIdentityService.createPrivilegeQuery().privilegeName("access-idm").singleResult();
            idmIdentityService.addUserPrivilegeMapping(privilege.getId(), user.getId());
        }
        return returnVo;
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @PostMapping("/delete")
    public ReturnVo<String> delete(String userId) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.SUCCESS, "删除成功");
        idmIdentityService.deleteUser(userId);
        return returnVo;
    }

    /**
     * 添加用户组
     *
     * @param userId   用户id
     * @param groupIds 组ids
     * @return
     */
    @PostMapping("/addUserGroup")
    public ReturnVo<String> addUserGroup(String userId, List<String> groupIds) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.SUCCESS, "删除成功");
        if (CollectionUtils.isNotEmpty(groupIds)) {
            groupIds.forEach(groupId -> idmIdentityService.createMembership(userId, groupId));
        }
        return returnVo;
    }
}
