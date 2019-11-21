package com.dragon.flow.model.leave;

import com.dragon.tools.common.BaseModel;
import java.util.Date;


/**
 * 请假单类
 * @author admin
 * @date 2019-11-20 19:06:48
 */
public class Leave extends BaseModel{
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
    private String name;
    /**
     *
     */
    private Integer days;
    /**
     *
     */
    private Date startTime;
    /**
     *
     */
    private Date endTime;

    // 临时变量 用于查询
    /**
     * 查询条件
     */
    private String keyWord;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public Integer getDays()
    {
        return days;
    }

    public void setDays(Integer days)
    {
        this.days = days;
    }
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
