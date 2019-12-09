package com.dragon.flow.rest.api;


import com.dragon.flow.model.leave.Purchase;
import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.service.leave.IPurchaseService;
import com.dragon.flow.vo.flowable.StartProcessInstanceVo;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.common.UUIDGenerator;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : admin
 * @date : 2019-12-09 10:00:54
 * description : 采购Controller
 */
@RestController
@RequestMapping("/rest/demo/purchase")
public class PurchaseResource extends BaseResource {
    private static Logger logger = LoggerFactory.getLogger(PurchaseResource.class);

    @Autowired
    private IPurchaseService purchaseService;
    @Autowired
    private IFlowableProcessInstanceService flowableProcessInstanceService;

    @GetMapping("/list")
    public PagerModel<Purchase> list(Purchase purchase, Query query) {
        PagerModel<Purchase> pm = null;
        try {
            pm = this.purchaseService.getPagerModelByQuery(purchase, query);
        } catch (Exception e) {
            logger.error("PurchaseController-ajaxList:", e);
            e.printStackTrace();
        }
        return pm;
    }

    //添加
    @PostMapping("/add")
    public ReturnVo add(Purchase purchase, String sessionId) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.FAIL, "添加失败");
        try {
            String purchaseId = UUIDGenerator.generate();
            purchase.setId(purchaseId);
            StartProcessInstanceVo startProcessInstanceVo = new StartProcessInstanceVo();
            startProcessInstanceVo.setBusinessKey(purchaseId);
            User user = SecurityUtils.getCurrentUserObject();
            startProcessInstanceVo.setCreator(user.getId());
            startProcessInstanceVo.setCurrentUserCode(user.getId());
            startProcessInstanceVo.setFormName("采购流程");
            startProcessInstanceVo.setSystemSn("flow");
            startProcessInstanceVo.setProcessDefinitionKey("purchase");
            Map<String, Object> variables = new HashMap<>();
            variables.put("money", purchase.getMoney());
            startProcessInstanceVo.setVariables(variables);
            ReturnVo<ProcessInstance> returnStart = flowableProcessInstanceService.startProcessInstanceByKey(startProcessInstanceVo);
            if (returnStart.getCode().equals(ReturnCode.SUCCESS)){
                String processInstanceId = returnStart.getData().getProcessInstanceId();
                purchase.setProcessInstanceId(processInstanceId);
                this.purchaseService.insertPurchase(purchase);
                returnVo = new ReturnVo(ReturnCode.SUCCESS, "添加成功");
            }else {
                returnVo = new ReturnVo(returnStart.getCode(), returnStart.getMsg());
            }
        }catch (Exception e){
            logger.error("PurchaseController-add:", e);
            e.printStackTrace();
        }

        return returnVo;
    }

    //修改
    @PostMapping("/update")
    public ReturnVo update(Purchase purchase) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.FAIL, "修改失败");
        try {
            this.purchaseService.updatePurchase(purchase);
            returnVo = new ReturnVo(ReturnCode.SUCCESS, "修改成功");
        } catch (Exception e) {
            logger.error("PurchaseController-update:", e);
            e.printStackTrace();
        }
        return returnVo;
    }

    //删除
    @GetMapping("/dels")
    public ReturnVo dels(String[] ids) {
        ReturnVo returnVo = new ReturnVo(ReturnCode.FAIL, "删除失败");
        try {
            for(String id: ids) {
                this.purchaseService.delPurchaseById(id);
            }
            returnVo = new ReturnVo(ReturnCode.SUCCESS, "删除成功");
        } catch (Exception e) {
            logger.error("PurchaseController-del:", e);
            e.printStackTrace();
        }
        return returnVo;
    }
}
