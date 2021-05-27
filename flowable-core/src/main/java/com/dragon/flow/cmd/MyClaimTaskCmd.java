package com.dragon.flow.cmd;

import org.apache.commons.lang.StringUtils;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.runtime.Clock;
import org.flowable.engine.impl.cmd.NeedsActiveTaskCmd;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

/**
 * @author : bruce.liu
 * @title: : MyClaimTaskCmd
 * @projectName : flowable
 * @description: 签收任务命令
 * @date : 2019/12/2316:39
 */
public class MyClaimTaskCmd extends NeedsActiveTaskCmd<Void> {

    private static final long serialVersionUID = 1L;

    protected String userId;

    public MyClaimTaskCmd(String taskId, String userId) {
        super(taskId);
        this.userId = userId;
    }

    @Override
    protected Void execute(CommandContext commandContext, TaskEntity task) {
        if (StringUtils.isNotBlank(userId)) {
            Clock clock = CommandContextUtil.getProcessEngineConfiguration(commandContext).getClock();
            task.setClaimTime(clock.getCurrentTime());
            TaskHelper.changeTaskAssignee(task, userId);
        } else {
            if (task.getAssignee() != null) {
                task.setClaimTime(null);
                TaskHelper.changeTaskAssignee(task, null);
            }
        }
        return null;
    }

    @Override
    protected String getSuspendedTaskException() {
        return "Cannot claim a suspended task";
    }
}
