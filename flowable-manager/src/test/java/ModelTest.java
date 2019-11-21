import com.dragon.flow.manager.main.FlowManagerApplication;
import com.dragon.tools.common.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author : bruce.liu
 * @title: : ModelTest
 * @projectName : flowable
 * @description: TODO
 * @date : 2019/11/1414:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowManagerApplication.class)
public class ModelTest {

    @Autowired
    private FlowableModelQueryService flowableModelQueryService;

    @Test
    public void createModel() throws Exception{
        File file = new File("E:\\workspace\\test\\flowabletest\\src\\leave.bpmn");
        InputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("leave.bpmn",inputStream);
        flowableModelQueryService.importProcessModel(null,multipartFile);
    }
}
