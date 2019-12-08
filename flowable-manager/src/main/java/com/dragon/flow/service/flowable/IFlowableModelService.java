package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.ModelVo;
import com.dragon.tools.vo.ReturnVo;
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
     * @return
     */
    public ReturnVo<String> importProcessModel(MultipartFile file);

    /**
     * 添加模型
     * @param modelVo
     * @return
     */
    public ReturnVo<String> addModel(ModelVo modelVo);
}
