import com.goodtown.MainApplication;
import com.goodtown.utils.Result;
import com.goodtown.utils.UploadFile;
import com.qcloud.cos.COSClient;

import lombok.extern.slf4j.Slf4j;

import com.goodtown.config.TCCOSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.data.redis.core.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest(classes = {MainApplication.class})
@Slf4j
public class MyTest {
    @Autowired
    COSClient cosClient;
    @Autowired
    UploadFile uploadFile;
    @Test
    public void testUploadFile() throws IOException {
        // 准备测试文件
        File file = new File("/home/jrcd/sample/town/src/test/resources/testFile.txt"); // 这里假设存在一个名为testFile.txt的文件用于测试，你可根据实际情况替换
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("testFile.txt","testFile.txt","text/plain", inputStream);

        // 调用要测试的上传方法
        Result<String> result = uploadFile.upload(multipartFile);

        // 根据返回结果进行断言验证
        System.out.println(result.getCode()+" "+result.getMessage()+" "+result.getData());

        // 关闭输入流，避免资源泄漏
        inputStream.close();
    }
}
