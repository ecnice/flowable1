package com.dragon.flow.model.leave;

import com.dragon.tools.common.BaseModel;
import java.util.Date;


/**
 * 采购类
 * @author admin
 * @date 2019-12-09 10:00:54
 */
public class Purchase extends BaseModel{
    /**
     *
     */
    private String id;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private Double money;
    /**
     *
     */
    private Date applyTime;

    // 临时变量 用于查询
    /**
     * 查询条件
     */
    private String keyWord;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
    public Double getMoney()
    {
        return money;
    }

    public void setMoney(Double money)
    {
        this.money = money;
    }
    public Date getApplyTime()
    {
        return applyTime;
    }

    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
