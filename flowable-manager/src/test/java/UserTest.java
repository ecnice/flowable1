import com.dragon.flow.manager.main.FlowManagerApplication;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
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
    public void initDatas() {
        this.testAddUser();
        this.testAddGroup();
        this.testSetPassword();
    }

    public void testSetPassword() {
        for (int i=1; i<10; i++){
            User user = new UserEntityImpl();
            user.setId("0000000"+i);
            user.setPassword("test");
            idmIdentityService.updateUserPassword(user);
            idmIdentityService.addUserPrivilegeMapping("7cade8be05db11eab327dc8b287b3603","0000000"+i);
        }
    }

    public void testAddGroup(){
        Group group = identityService.newGroup("treasurer");
        group.setName("财务人员");
        identityService.saveGroup(group);

        group = identityService.newGroup("cashier");
        group.setName("出纳员");
        identityService.saveGroup(group);

        group = identityService.newGroup("supportCrew");
        group.setName("后勤人员");
        identityService.saveGroup(group);
        /**
         * 张三是 财务人员 出纳员
         * 李四是 后勤人员
         */
        identityService.createMembership("00000003","treasurer");
        identityService.createMembership("00000003","cashier");
        identityService.createMembership("00000004","supportCrew");
    }


    public void testAddUser() {
        User user = identityService.newUser("00000001");
        user.setFirstName("军哥");
        user.setLastName("军哥");
        user.setDisplayName("军哥");
        user.setEmail("军哥@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000002");
        user.setFirstName("刘二");
        user.setLastName("刘二");
        user.setDisplayName("刘二");
        user.setEmail("刘二@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000003");
        user.setFirstName("张三");
        user.setLastName("张三");
        user.setDisplayName("张三");
        user.setEmail("张三@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000004");
        user.setFirstName("李四");
        user.setLastName("李四");
        user.setDisplayName("李四");
        user.setEmail("李四@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000005");
        user.setFirstName("王五");
        user.setLastName("王五");
        user.setDisplayName("王五");
        user.setEmail("王五@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000006");
        user.setFirstName("赵六");
        user.setLastName("赵六");
        user.setDisplayName("赵六");
        user.setEmail("赵六@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000007");
        user.setFirstName("陈七");
        user.setLastName("陈七");
        user.setDisplayName("陈七");
        user.setEmail("陈七@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000008");
        user.setFirstName("谢八");
        user.setLastName("谢八");
        user.setDisplayName("谢八");
        user.setEmail("谢八@qq.com");
        identityService.saveUser(user);

        user = identityService.newUser("00000009");
        user.setFirstName("徐九");
        user.setLastName("徐九");
        user.setDisplayName("徐九");
        user.setEmail("徐九@qq.com");
        identityService.saveUser(user);
    }

}
