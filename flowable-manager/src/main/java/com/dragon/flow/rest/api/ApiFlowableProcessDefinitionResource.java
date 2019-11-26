package com.dragon.flow.rest.api;

import com.dragon.flow.service.flowable.FlowProcessDiagramGenerator;
import com.dragon.flow.service.flowable.IFlowableBpmnModelService;
import com.dragon.flow.service.flowable.IFlowableProcessDefinitionService;
import com.dragon.flow.vo.flowable.ProcessDefinitionQueryVo;
import com.dragon.flow.vo.flowable.ret.ProcessDefinitionVo;
import com.dragon.tools.pager.PagerModel;
import com.dragon.tools.pager.Query;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author : bruce.liu
 * @title: : ApiTask
 * @projectName : flowable
 * @description: 模型API
 * @date : 2019/11/1321:21
 */
@RestController
@RequestMapping("/rest/definition")
public class ApiFlowableProcessDefinitionResource extends BaseResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiFlowableProcessDefinitionResource.class);
    @Autowired
    private IFlowableProcessDefinitionService flowableProcessDefinitionService;
    @Autowired
    private IFlowableBpmnModelService flowableBpmnModelService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private FlowProcessDiagramGenerator flowProcessDiagramGenerator;

    /**
     * 分页查询流程定义列表
     * @param params 参数
     * @param query 分页
     * @return
     */
    @PostMapping(value = "/page-model")
    public PagerModel<ProcessDefinitionVo> pageModel(ProcessDefinitionQueryVo params, Query query) {
        PagerModel<ProcessDefinitionVo> pm = flowableProcessDefinitionService.getPagerModel(params, query);
        return pm;
    }

    /**
     * 通过id和类型获取图片
     * @param id 流程定义id
     * @param type 类型
     * @param response response
     */
    @GetMapping(value = "/processFile/{type}/{id}")
    public void processFile(@PathVariable String id,@PathVariable String type, HttpServletResponse response) {
        try {
            BpmnModel bpmnModel = flowableBpmnModelService.getBpmnModelByProcessDefId(id);
            byte[] b = null;
            if (type.equals("xml")){
                response.setHeader("Content-type", "text/xml;charset=UTF-8");
                b = modelService.getBpmnXML(bpmnModel);
            }else {
                response.setHeader("Content-Type", "image/png");
                InputStream inputStream = flowProcessDiagramGenerator.generateDiagram(bpmnModel);
                b = IoUtil.readInputStream(inputStream, "image inputStream name");
            }
            response.getOutputStream().write(b);
        } catch (Exception e) {
            LOGGER.error("ApiFlowableModelResource-loadXmlByModelId:" + e);
            e.printStackTrace();
        }
    }




}
