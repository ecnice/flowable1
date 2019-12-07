package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.ModelVo;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 模型service
 * @date : 2019/11/1920:56
 */
public interface IFlowableModelService {

    /**
     * 导入模型
     * @param file 文件
     * @param model 模型参数
     * @return
     */
    public ModelRepresentation importProcessModel(MultipartFile file, ModelRepresentation model);

    /**
     * 添加模型
     * @param modelVo
     * @param model
     * @return
     */
    public ModelRepresentation addModel(ModelVo modelVo, ModelRepresentation model);
}
