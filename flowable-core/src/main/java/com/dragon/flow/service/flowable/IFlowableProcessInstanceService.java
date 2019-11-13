package com.dragon.flow.service.flowable;

import com.dragon.flow.vo.flowable.StartProcessInstanceVo;
import com.dragon.tools.vo.ReturnVo;

/**
 * @author : bruce.liu
 * @projectName : flowable
 * @description: 流程实例service
 * @date : 2019/10/2511:40
 */
public interface IFlowableProcessInstanceService {

    /**
     * 启动流程
     * @param startProcessInstanceVo 参数
     * @return
     */
    public ReturnVo startProcessInstanceByKey(StartProcessInstanceVo startProcessInstanceVo) ;


}
