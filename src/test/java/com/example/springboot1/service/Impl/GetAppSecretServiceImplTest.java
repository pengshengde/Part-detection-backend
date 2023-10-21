package com.example.springboot1.service.Impl;

import com.example.springboot1.Entity.GetAppSecret;
import com.example.springboot1.mapper.GetAppSecretMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
// SpringJUnit支持，由此引入Spring-Test框架支持！

//启动整个spring的工程
@SpringBootTest
public class GetAppSecretServiceImplTest {

    /*private GetAppSecretServiceImpl getAppSecretServiceImplUnderTest;

    @BeforeEach
    void setup(){
        getAppSecretServiceImplUnderTest = new GetAppSecretServiceImpl();
        getAppSecretServiceImplUnderTest.getAppSecretMapper = mock(GetAppSecretMapper.class);
    }

    @Test
    void testgetSecret(){
        // 构造GetAppSecretMapper.getSecret()的参数
        final String appId;
        appId = "a";

        // 构造GetAppSecretMapper.getSecret()的返回值
        final GetAppSecret getAppSecret = new GetAppSecret();
        getAppSecret.setAppSecret("ashdfgjashgfdjkhsafghj");

        when(getAppSecretServiceImplUnderTest.getAppSecretMapper.getSecret(appId)).thenReturn(getAppSecret);

        // 执行getAppSecretService.getSecret()
        final GetAppSecret getAppSecret1 = getAppSecretServiceImplUnderTest.getSecret(appId);

        // 验证结果
        System.out.println(getAppSecret1.toString()); } */
    @Autowired
    private GetAppSecretServiceImpl getAppSecretService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testgetSecret(){
            String expectedAppSecret = "fbbbf0c4cc644ade98d21deed4d04fb2";

            GetAppSecret getAppSecret = getAppSecretService.getSecret("a");
            Assert.assertEquals(expectedAppSecret,getAppSecret.getAppSecret());
    }

}



