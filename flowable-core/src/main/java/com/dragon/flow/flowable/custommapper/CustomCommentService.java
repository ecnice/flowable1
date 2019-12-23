package com.dragon.flow.flowable.custommapper;

import org.flowable.common.engine.impl.cmd.CustomSqlExecution;
import org.flowable.engine.ManagementService;
import org.flowable.engine.impl.cmd.AbstractCustomSqlExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : bruce.liu
 * @title: : CustomCommentService
 * @projectName : flowable
 * @description: 自定义sql
 * @date : 2019/12/239:54
 */
@Service
@Scope("prototype")
public class CustomCommentService {
    @Autowired
    private ManagementService managementService;

    private String procInstId ;

    CustomSqlExecution<CustomCommentMapper, List<Map<String, Object>>> customSqlExecution = new AbstractCustomSqlExecution<CustomCommentMapper, List<Map<String, Object>>>(CustomCommentMapper.class) {
        @Override
        public List<Map<String, Object>> execute(CustomCommentMapper customMapper) {
            return customMapper.selectCommentsByProcInstId(procInstId);
        }
    };

    public List<Map<String, Object>> getCommentsByProcInstId(String procInstId){
        this.setProcInstId(procInstId);
        return managementService.executeCustomSql(customSqlExecution);
    }

    private void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
}
