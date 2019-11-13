package com.dragon.flow.rest.api;

import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : bruce.liu
 * @title: : ApiTask
 * @projectName : flowable
 * @description: 模型API
 * @date : 2019/11/1321:21
 */
@RestController
@RequestMapping("/api/model")
public class ApiFlowableModelResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFlowableModelResource.class);

    @Autowired
    private FlowableModelQueryService modelQueryService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private RepositoryService repositoryService;


    @PostMapping(value = "/rest/page-model")
    public ReturnVo<PagerModel<AbstractModel>> pageModel() {
        ReturnVo<PagerModel<AbstractModel>> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        List<AbstractModel> datas = modelService.getModelsByModelType(AbstractModel.MODEL_TYPE_BPMN);
        PagerModel<AbstractModel> pm = new PagerModel<>(datas.size(),datas);
        returnVo.setData(pm);
        return returnVo;
    }

    @PostMapping(value = "/rest/import-process-model")
    public ReturnVo<String> importProcessModel(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        modelQueryService.importProcessModel(request, file);
        return returnVo;
    }

    @PostMapping(value = "/rest/deploy")
    public ReturnVo<String> deploy(String modelId) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.SUCCESS,"OK");
        Model model = modelService.getModel(modelId.trim());
        //到时候需要添加分类
        String categoryCode = "1000";
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        //添加隔离信息
        String tenantId = "flow";
        //必须指定文件后缀名否则部署不成功
        Deployment deploy = repositoryService.createDeployment()
                .name(model.getName())
                .key(model.getKey())
                .category(categoryCode)
                .tenantId(tenantId)
                .addBpmnModel(model.getKey() + ".bpmn", bpmnModel)
                .deploy();
        returnVo.setData(deploy.getId());
        return returnVo;
    }





}
