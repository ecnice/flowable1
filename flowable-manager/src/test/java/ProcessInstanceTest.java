import com.dragon.flow.manager.main.FlowManagerApplication;
import com.dragon.flow.model.leave.Leave;
import com.dragon.flow.service.flowable.IFlowableProcessInstanceService;
import com.dragon.flow.service.leave.ILeaveService;
import com.dragon.flow.vo.flowable.RevokeProcessVo;
import com.dragon.flow.vo.flowable.StartProcessInstanceVo;
import com.dragon.tools.common.DateUtil;
import com.dragon.tools.common.UUIDGenerator;
import com.dragon.tools.vo.ReturnVo;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : bruce.liu
 * @title: : Process
 * @projectName : flowable
 * @description:
 * @date : 2019/11/2118:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowManagerApplication.class)
public class ProcessInstanceTest {

    @Autowired
    private IFlowableProcessInstanceService flowableProcessInstanceService;
    @Autowired
    private ILeaveService leaveService;

    @Test
    public void testStartProcess() throws Exception{
        Leave leave = new Leave();
        String leaveId = UUIDGenerator.generate();
        leave.setId(leaveId);
        leave.setDays(2);
        leave.setName("刘文军");
        Date date = new Date();
        leave.setStartTime(date);
        leave.setEndTime(DateUtil.addDate(date,1));
        StartProcessInstanceVo startProcessInstanceVo = new StartProcessInstanceVo();
        startProcessInstanceVo.setBusinessKey(leaveId);
        startProcessInstanceVo.setCreator("00000001");
        startProcessInstanceVo.setCurrentUserCode("00000001");
        startProcessInstanceVo.setFormName("请假流程");
        startProcessInstanceVo.setSystemSn("flow");
        startProcessInstanceVo.setProcessDefinitionKey("qingjia");
        Map<String,Object> variables = new HashMap<>();
        variables.put("days",leave.getDays());
        startProcessInstanceVo.setVariables(variables);
        ReturnVo<ProcessInstance> returnStart = flowableProcessInstanceService.startProcessInstanceByKey(startProcessInstanceVo);
        String processInstanceId = returnStart.getData().getProcessInstanceId();
        leave.setProcessInstanceId(processInstanceId);
        this.leaveService.insertLeave(leave);
    }
    @Test
    public void testRevokeProcess() {
        RevokeProcessVo revokeVo = new RevokeProcessVo();
        revokeVo.setProcessInstanceId("021d89c116a011ea89b4dc8b287b3603");
        flowableProcessInstanceService.revokeProcess(revokeVo);
    }
}
