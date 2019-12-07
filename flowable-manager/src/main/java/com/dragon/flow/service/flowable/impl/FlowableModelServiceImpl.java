package com.dragon.flow.service.flowable.impl;

import com.dragon.flow.service.flowable.IFlowableModelService;
import com.dragon.flow.vo.flowable.ModelVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.idm.api.User;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

/**
 * @author : bruce.liu
 * @title: : FlowableModelServiceImpl
 * @projectName : flowable
 * @description: 模型service实现类
 * @date : 2019/11/1920:58
 */
@Service
public class FlowableModelServiceImpl implements IFlowableModelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlowableModelServiceImpl.class);
    @Autowired
    protected ModelRepository modelRepository;

    @Autowired
    protected ModelService modelService;
    @Autowired
    protected ObjectMapper objectMapper;

    protected BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    @Override
    public ModelRepresentation addModel(ModelVo modelVo, ModelRepresentation model) {
        InputStream inputStream = new ByteArrayInputStream(modelVo.getXml().getBytes());
        return this.createModel(inputStream, model, modelVo.getProcessName());
    }

    @Override
    public ModelRepresentation importProcessModel(MultipartFile file, ModelRepresentation model) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && (fileName.endsWith(".bpmn") || fileName.endsWith(".bpmn20.xml"))) {
            try {
                InputStream inputStream = file.getInputStream();
                return this.createModel(inputStream, model, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new BadRequestException("Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName);
        }
        return model;
    }

    private ModelRepresentation createModel(InputStream inputStream, ModelRepresentation model, String fileName) {
        try {
            XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
            InputStreamReader xmlIn = new InputStreamReader(inputStream, "UTF-8");
            XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
            BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
            //模板验证
            ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
            List<ValidationError> errors = validator.validate(bpmnModel);
            if (CollectionUtils.isNotEmpty(errors)) {
                StringBuffer es = new StringBuffer();
                errors.forEach(ve -> es.append(ve.toString()).append("/n"));
                throw new BadRequestException("模板验证失败，原因: " + es.toString());
            }
            if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                throw new BadRequestException("No process found in definition " + fileName);
            }
            if (bpmnModel.getLocationMap().size() == 0) {
                BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                bpmnLayout.execute();
            }
            ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
            org.flowable.bpmn.model.Process process = bpmnModel.getMainProcess();
            String name = process.getId();
            if (StringUtils.isNotEmpty(process.getName())) {
                name = process.getName();
            }
            String description = process.getDocumentation();
            model.setKey(process.getId());
            model.setName(name);
            model.setDescription(description);
            model.setModelType(AbstractModel.MODEL_TYPE_BPMN);

            User createdBy = SecurityUtils.getCurrentUserObject();
            //查询是否已经存在流程模板
            Model newModel = new Model();
            List<Model> models = modelRepository.findByKeyAndType(model.getKey(), model.getModelType());
            if (CollectionUtils.isNotEmpty(models)) {
                Model updateModel = models.get(0);
                newModel.setId(updateModel.getId());
            }
            newModel.setName(model.getName());
            newModel.setKey(model.getKey());
            newModel.setModelType(model.getModelType());
            newModel.setCreated(Calendar.getInstance().getTime());
            newModel.setCreatedBy(createdBy.getId());
            newModel.setDescription(model.getDescription());
            newModel.setModelEditorJson(modelNode.toString());
            newModel.setLastUpdated(Calendar.getInstance().getTime());
            newModel.setLastUpdatedBy(createdBy.getId());
            newModel.setTenantId(model.getTenantId());
            newModel = modelService.createModel(newModel, SecurityUtils.getCurrentUserObject());
            return new ModelRepresentation(newModel);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Import failed for {}", fileName, e);
            throw new BadRequestException("Import failed for " + fileName + ", error message " + e.getMessage());
        }
    }
}
