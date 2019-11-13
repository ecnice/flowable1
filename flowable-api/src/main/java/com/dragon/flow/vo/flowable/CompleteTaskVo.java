package com.dragon.flow.vo.flowable;


import java.util.Map;

/**
 * @author : bruce.liu
 * @title: : CompleteTaskVo
 * @projectName : flowable
 * @description: 执行任务Vo
 * @date : 2019/11/1315:27
 */
public class CompleteTaskVo extends BaseProcessVo {
    /**
     * 任务参数 选填
     */
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
