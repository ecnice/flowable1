package com.dragon.flow.vo.flowable.ret;
import com.dragon.flow.enm.flowable.CommentTypeEnum;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: Bruce.liu
 * @Since:19:24 2019/11/24
 * 爱拼才会赢 2019 ~ 2030 版权所有
 */
public class CommentVo implements Serializable {
    /**
     * 任务id
     */
    protected String taskId;
    /**
     * 添加人
     */
    protected String userId;
    /**
     * 用户的名称
     */
    protected String userName;
    /**
     * 用户的头像链接
     */
    protected String userUrl;
    /**
     * 流程实例id
     */
    protected String processInstanceId;
    /**
     * 意见信息
     */
    protected String message;
    /**
     * 时间
     */
    protected Date time;
    /**
     *  @see  com.dragon.flow.enm.flowable.CommentTypeEnum
      */
    private String type;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 评论全信息
     */
    private String fullMsg;
    public CommentVo(){}
    public CommentVo(String userId, String processInstanceId, String type, String message) {
        this.userId = userId;
        this.processInstanceId = processInstanceId;
        this.message = message;
        this.type = type;
    }
    public CommentVo(String taskId, String userId, String processInstanceId,String type, String message) {
        this.taskId = taskId;
        this.userId = userId;
        this.processInstanceId = processInstanceId;
        this.message = message;
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.typeName = CommentTypeEnum.getEnumMsgByType(type);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFullMsg() {
        return fullMsg;
    }

    public void setFullMsg(String fullMsg) {
        this.fullMsg = fullMsg;
    }
}
