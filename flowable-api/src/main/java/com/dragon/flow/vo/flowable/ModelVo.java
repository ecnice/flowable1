package com.dragon.flow.vo.flowable;

import java.io.Serializable;

/**
 * @author : bruce.liu
 * @title: : ModelVo
 * @projectName : flowable
 * @description: modelVo
 * @date : 2019/12/710:20
 */
public class ModelVo implements Serializable {
    //流程id
    private String processId;
    //流程名称
    private String processName;
    /**
     * 分类Id
     */
    private String categoryId;
    //流程的xml
    private String xml;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
