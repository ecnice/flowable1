package com.dragon.flow.rest.api;

import com.dragon.flow.model.leave.Leave;
import com.dragon.flow.service.leave.ILeaveService;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import com.dragon.tools.common.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author : admin
 * @date : 2019-11-20 19:06:48
 * description : 请假单Controller
 */
@RestController
@RequestMapping("/rest/leave")
public class LeaveController {
    private static Logger logger = LoggerFactory.getLogger(LeaveController.class);

    private final String nameSpace = "leave";

    @Resource
    private ILeaveService LeaveService;

    @GetMapping("/ajaxList")
    public PagerModel<Leave> ajaxList(Leave Leave, Query query, String sessionId) {
        PagerModel<Leave> pm = null;
        try {
            pm = this.LeaveService.getPagerModelByQuery(Leave, query);
        } catch (Exception e) {
            logger.error("LeaveController-ajaxList:", e);
            e.printStackTrace();
        }
        return pm;
    }

    //添加
    @PostMapping("/add")
    public ReturnVo add(Leave Leave, String sessionId) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.FAIL, "添加失败");
        try {
            this.LeaveService.insertLeave(Leave);
            returnVo = new ReturnVo(ReturnCode.SUCCESS, "添加成功");
        } catch (Exception e) {
            logger.error("LeaveController-add:", e);
            e.printStackTrace();
        }
        return returnVo;
    }

    //修改
    @PostMapping("/update")
    public ReturnVo update(Leave Leave, String sessionId) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.FAIL, "修改失败");
        try {
            this.LeaveService.updateLeave(Leave);
            returnVo = new ReturnVo(ReturnCode.SUCCESS, "修改成功");
        } catch (Exception e) {
            logger.error("LeaveController-update:", e);
            e.printStackTrace();
        }
        return returnVo;
    }

}
