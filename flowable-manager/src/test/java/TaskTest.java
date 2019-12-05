import com.dragon.flow.manager.main.FlowManagerApplication;
import com.dragon.flow.service.flowable.IFlowableTaskService;
import com.dragon.flow.vo.flowable.TurnTaskVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : bruce.liu
 * @title: : TaskTest
 * @projectName : flowable
 * @description: TODO
 * @date : 2019/12/510:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowManagerApplication.class)
public class TaskTest {

    @Autowired
    private IFlowableTaskService flowableTaskService;

    @Test
    public void testTurnTask() {
        TurnTaskVo turnTaskVo = new TurnTaskVo();
        turnTaskVo.setTaskId("4b48af1616a911eab464dc8b287b3603");
        turnTaskVo.setProcessInstanceId("61cebe8b16a711eab464dc8b287b3603");
        turnTaskVo.setTurnToUserId("00000004");
        turnTaskVo.setMessage("转办");
        flowableTaskService.turnTask(turnTaskVo);
    }
}
