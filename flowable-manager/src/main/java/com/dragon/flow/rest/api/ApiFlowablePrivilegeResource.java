package com.dragon.flow.rest.api;

import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.Privilege;
import org.flowable.idm.api.PrivilegeQuery;
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
 * @description: 权限
 * @date : 2019/12/222:24
 */
@RestController
@RequestMapping("/rest/privilege")
public class ApiFlowablePrivilegeResource extends BaseResource {

    @Autowired
    private IdmIdentityService idmIdentityService;
    /**
     * 查询权限列表
     *
     * @param name 姓名
     * @return
     */
    @GetMapping("/getPagerModel")
    public PagerModel<Privilege> getPagerModel(String name, Query query) {
        PrivilegeQuery privilegeQuery = idmIdentityService.createPrivilegeQuery().privilegeName(name);
        long count = privilegeQuery.count();
        int firstResult = (query.getPageNum() - 1) * query.getPageSize();
        List<Privilege> datas = privilegeQuery.listPage(firstResult, query.getPageSize());
        return new PagerModel<>(count, datas);
    }

    /**
     * 添加修改权限
     * @param privilegeName  名称
     * @return
     */
    @PostMapping("/save")
    public ReturnVo<String> save(String privilegeName) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.SUCCESS, "添加成功");
        idmIdentityService.createPrivilege(privilegeName);
        return returnVo;
    }

    /**
     * 删除权限
     * @param privilegeId 权限id
     * @return
     */
    @PostMapping("/delete")
    public ReturnVo<String> delete(String privilegeId) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.SUCCESS, "删除成功");
        idmIdentityService.deletePrivilege(privilegeId);
        return returnVo;
    }
}
