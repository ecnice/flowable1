import com.dragon.flow.manager.main.FlowManagerApplication;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.User;
import org.flowable.ui.idm.service.UserService;
import org.flowable.ui.idm.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : bruce.liu
 * @title: : UserTest
 * @projectName : flowable
 * @description: TODO
 * @date : 2019/11/1921:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowManagerApplication.class)
public class UserTest {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private IdmIdentityService idmIdentityService;

    @Test
    public void testSetUserInfo() {
        idmIdentityService.setUserInfo("00000001","age","34");

    }

    @Test
    public void testAddUser() {
        User user = identityService.newUser("00000001");
        user.setFirstName("军哥");
        user.setLastName("军哥");
        user.setEmail("军哥@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000002");
        user.setFirstName("张三");
        user.setLastName("张三");
        user.setEmail("张三@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000003");
        user.setFirstName("李四");
        user.setLastName("李四");
        user.setEmail("李四@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000004");
        user.setFirstName("王五");
        user.setLastName("王五");
        user.setEmail("王五@qq.com");
        identityService.saveUser(user);
    }

}
