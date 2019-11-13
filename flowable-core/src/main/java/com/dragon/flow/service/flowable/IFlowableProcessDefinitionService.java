package com.dragon.flow.service.flowable;

import org.flowable.engine.repository.Deployment;

import java.io.InputStream;

/**
 * @author : bruce.liu
 * @title: : IFlowProcessDi
 * @projectName : flowable
 * @description: 流程定义
 * @date : 2019/11/1314:11
 */
public interface IFlowableProcessDefinitionService {

    /**
     * 部署流程
     * @param fileName 文件名
     * @param inputStream 文件流
     */
    public Deployment deploy(String fileName, InputStream inputStream);
}
