package cn.isuyu.tencent.face.h5.controller;

import cn.isuyu.tencent.face.h5.dtos.ResultDTO;
import cn.isuyu.tencent.face.h5.dtos.SendUserInfoDTO;
import cn.isuyu.tencent.face.h5.service.TencentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "send")
    @ResponseBody
    public String sendInfo() {

        SendUserInfoDTO sendUserInfoDTO = new SendUserInfoDTO();
        sendUserInfoDTO.setName("聂志良")
                .setIdNo("362202199509052515")
                .setUserId(System.currentTimeMillis()+"");

        return tencentService.getFacePage(sendUserInfoDTO);
    }

    @RequestMapping(value = "/")
    public String index() {

        return "index";
    }

    @RequestMapping(value = "result")
    public String result(ResultDTO resultDTO) {

        return null;
    }
}
