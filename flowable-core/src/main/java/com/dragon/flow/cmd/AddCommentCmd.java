package com.dragon.flow.cmd;

import com.dragon.flow.enm.flowable.CommentTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.compatibility.Flowable5CompatibilityHandler;
import org.flowable.engine.impl.persistence.entity.CommentEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.Flowable5Util;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.task.Comment;
import org.flowable.engine.task.Event;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

/**
 * @Description:
 * @Author: Bruce.liu
 * @Since:19:01 2018/11/24
 * 爱拼才会赢 2019 ~ 2030 版权所有
 */
public class AddCommentCmd implements Command<Comment> {

    protected String taskId;
    protected String userId;
    protected String processInstanceId;
    protected String type;
    protected String message;

    public AddCommentCmd(String userId, String processInstanceId, String type, String message) {
        this.processInstanceId = processInstanceId;
        this.message = message;
        this.type = type;
        this.userId = userId;
    }

    public AddCommentCmd(String taskId, String userId, String processInstanceId, String type, String message) {
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.type = type;
        this.message = message;
        this.userId = userId;
    }

    @Override
    public Comment execute(CommandContext commandContext) {
        TaskEntity task = null;
        ExecutionEntity execution = null;
        if (processInstanceId != null) {
            execution = CommandContextUtil.getExecutionEntityManager(commandContext).findById(processInstanceId);
            if (execution == null) {
                throw new FlowableObjectNotFoundException("execution " + processInstanceId + " doesn't exist", Execution.class);
            }
            if (execution.isSuspended()) {
                throw new FlowableException(getSuspendedExceptionMessage());
            }
        }

        String processDefinitionId = null;
        if (execution != null) {
            processDefinitionId = execution.getProcessDefinitionId();
        } else if (task != null) {
            processDefinitionId = task.getProcessDefinitionId();
        }

        if (processDefinitionId != null && Flowable5Util.isFlowable5ProcessDefinitionId(commandContext, processDefinitionId)) {
            Flowable5CompatibilityHandler compatibilityHandler = Flowable5Util.getFlowable5CompatibilityHandler();
            if (StringUtils.isBlank(message)){
                message = "";
            }
            return compatibilityHandler.addComment(taskId, processInstanceId, type, message);
        }
        CommentEntity comment = CommandContextUtil.getCommentEntityManager(commandContext).create();
        comment.setUserId(userId);
        comment.setType((type == null) ? CommentTypeEnum.SP.toString() : type);
        comment.setTime(CommandContextUtil.getProcessEngineConfiguration(commandContext).getClock().getCurrentTime());
        comment.setTaskId(taskId);
        comment.setProcessInstanceId(processInstanceId);
        comment.setAction(Event.ACTION_ADD_COMMENT);

        String eventMessage = StringUtils.isNotBlank(message)?message.replaceAll("\\s+", " "):"";
        if (eventMessage.length() > 3900) {
            eventMessage = eventMessage.substring(0, 3900) + "...";
        }
        comment.setMessage(eventMessage);
        comment.setFullMessage(message);
        CommandContextUtil.getCommentEntityManager(commandContext).insert(comment);

        return comment;
    }
    protected String getSuspendedTaskException() {
        return "Cannot add a comment to a suspended task";
    }
    protected String getSuspendedExceptionMessage() {
        return "Cannot add a comment to a suspended execution";
    }
}
