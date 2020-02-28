package cn.isuyu.tencent.face.h5.service;


import cn.isuyu.tencent.face.h5.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 上午11:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TencentServiceTest {

    @Autowired
    private TencentService tencentService;

    @Test
    public void getTicket() {
        System.out.println(tencentService.getTicket("123456"));
        System.out.println("sdf");
    }
}