package cn.isuyu.tencent.face.h5.controller;

import cn.isuyu.tencent.face.h5.dtos.ResultDTO;
import cn.isuyu.tencent.face.h5.dtos.SendUserInfoDTO;
import cn.isuyu.tencent.face.h5.dtos.SyncResultDTO;
import cn.isuyu.tencent.face.h5.service.TencentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 上午11:44
 */
@Controller
public class IndexController {

    @Autowired
    private TencentService tencentService;


    /**
     * 发送用户信息去申请视频录制页面
     * @return
     */
    @RequestMapping(value = "send")
    @ResponseBody
    public String sendInfo() {

        SendUserInfoDTO sendUserInfoDTO = new SendUserInfoDTO();
        sendUserInfoDTO.setName("苏雨")
                .setIdNo("3622021995******15")
                .setUserId(System.currentTimeMillis()+"");

        return tencentService.getFacePage(sendUserInfoDTO);
    }

    /**
     * 同步获取结果
     * @param resultDTO
     * @return
     */
    @RequestMapping(value = "page")
    @ResponseBody
    public String result(ResultDTO resultDTO) {

        return null;
    }

    /**
     * 通过orderNo获取具体的用户信息
     * @param syncResultDTO
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "result")
    @ResponseBody
    public String getSyncResult(SyncResultDTO syncResultDTO) throws IOException {
        return tencentService.syncGetUserInfo(syncResultDTO);
    }
}
