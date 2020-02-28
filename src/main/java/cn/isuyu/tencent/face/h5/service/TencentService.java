package cn.isuyu.tencent.face.h5.service;

import cn.isuyu.tencent.face.h5.constant.TencentConstant;
import cn.isuyu.tencent.face.h5.dtos.SendUserInfoDTO;
import cn.isuyu.tencent.face.h5.dtos.SyncResultDTO;
import cn.isuyu.tencent.face.h5.utils.OkHttp;
import cn.isuyu.tencent.face.h5.utils.RedisTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 上午11:01
 */
@Component
@Slf4j
public class TencentService {

    @Autowired
    private RedisTools redisTools;



    /**
     * 获取录制视频的页面
     * @param userInfoDTO
     * @return
     */
    public String getFacePage(SendUserInfoDTO userInfoDTO) {
        String result = getH5FaceId(userInfoDTO);
        JSONObject resultJson = JSON.parseObject(result);
        //只有0时表示请求成功
        if (resultJson.getIntValue("code") == 0) {
            String faceId = resultJson.getJSONObject("result").getString("h5faceId");
            String nonce = UUID.randomUUID().toString().replaceAll("-","");
            List<String> signList = new ArrayList<>(6);
            signList.add(TencentConstant.APPID);
            signList.add(userInfoDTO.getUserId());
            signList.add(nonce);
            signList.add("1.0.0");
            signList.add(faceId);
            signList.add(userInfoDTO.getOrderNo());
            //签名
            String sign = sign(signList,getTicket(userInfoDTO.getUserId()));

            StringBuffer facePageBuffer = new StringBuffer(TencentConstant.START_FACE_LOGIN)
                    .append("?webankAppId=").append(TencentConstant.APPID)
                    .append("&version=").append("1.0.0")
                    .append("&nonce=").append(nonce)
                    .append("&orderNo=").append(userInfoDTO.getOrderNo())
                    .append("&h5faceId=").append(faceId)
                    .append("&url=").append("https://www.fangxinqian.cn")
                    .append("&resultType=1")
                    .append("&userId=").append(userInfoDTO.getUserId())
                    .append("&sign=").append(sign)
                    .append("&from=browser")
                    .append("&redirectType=1");

            return facePageBuffer.toString();
        }
        return null;

    }


    /**
     * 获取用户身份结果
     * @param syncResultDTO
     * @return
     */
    public String syncGetUserInfo(SyncResultDTO syncResultDTO) {

        return "srt";
    }

    /**
     * 传递用户信息给腾讯云
     * @param userInfoDTO
     */
    private String getH5FaceId(SendUserInfoDTO userInfoDTO) {
        userInfoDTO.setWebankAppId(TencentConstant.APPID)
                .setOrderNo(UUID.randomUUID().toString().replaceAll("-",""));

        List<String> signList = new ArrayList<>(7);
        signList.add(userInfoDTO.getWebankAppId());
        signList.add(userInfoDTO.getOrderNo());
        signList.add(userInfoDTO.getName());
        signList.add(userInfoDTO.getIdNo());
        signList.add(userInfoDTO.getVersion());
        signList.add(userInfoDTO.getUserId());

        String sign = sign(signList,getTicket());

        userInfoDTO.setSign(sign);
        String result = null;
        try {
            log.info(JSON.toJSONString(userInfoDTO));
            result = OkHttp.doPost(TencentConstant.URL_SEND_USER_INFO, JSON.toJSONString(userInfoDTO));
            log.info("tencent h5 face response:{}",result);
        } catch (IOException e) {
            log.error("request tencent h5 face error:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 获取登录ticket
     * @return
     */
    public String getTicket(String userId) {
        String ticket = null;
        String token = getToken();
        StringBuffer requestUrl = new StringBuffer(TencentConstant.GET_TICKET_URL)
                .append("?app_id=").append(TencentConstant.APPID)
                .append("&access_token=").append(token)
                .append("&type=NONCE&version=1.0.0")
                .append("&user_id=").append(userId);
        String result = null;
        try {
            log.info("tencentCloud ticket request params：[{}]",requestUrl.toString());
            result = OkHttp.get(requestUrl.toString());
            log.info("tencentCloud ticket response params：[{}]",result);
            JSONObject jsonObject = JSON.parseObject(result);
            if (jsonObject.getIntValue("code") == 0) {
                ticket =  jsonObject.getJSONArray("tickets").getObject(0,JSONObject.class).getString("value");
            } else {
                log.error("获取腾讯云h5视频认证ticket异常：[{}]",result);
            }
        } catch (IOException e) {
            log.error("获取腾讯云h5视频认证ticket异常：[{}]",e.getMessage());
        }
        return ticket;
    }

    /**
     * 获取faceId的ticket
     * @return
     */
    public String getTicket() {
        String ticket = null;
        //由于token过期 该token签发的所有的ticket全部失效 所以在获取ticket的时候必须判断缓存中有没有token 如果没有 也需要重写获取ticket
        if (redisTools.hasKey(TencentConstant.APPID) && redisTools.hasKey(TencentConstant.SECRET_KEY)){
            ticket = redisTools.get(TencentConstant.SECRET_KEY);
        } else {
            String token = getToken();
            StringBuffer requestUrl = new StringBuffer(TencentConstant.GET_TICKET_URL)
                    .append("?app_id=").append(TencentConstant.APPID)
                    .append("&access_token=").append(token)
                    .append("&type=SIGN&version=1.0.0");
            String result = null;
            try {
                log.info("tencentCloud h5 ticket request params：[{}]",requestUrl.toString());
                result = OkHttp.get(requestUrl.toString());
                log.info("tencentCloud h5 ticket response params：[{}]",result);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.getIntValue("code") == 0) {
                    ticket =  jsonObject.getJSONArray("tickets").getObject(0,JSONObject.class).getString("value");
                    //将获取到的ticket放入缓存 我们设置8分钟
                    redisTools.save(TencentConstant.SECRET_KEY,ticket,25);
                } else {
                    log.error("获取腾讯云h5视频认证ticket异常：[{}]",result);
                    //删除掉缓存中腾讯云的token
                    redisTools.delete(TencentConstant.APPID);
                }
            } catch (IOException e) {
                log.error("获取腾讯云h5视频认证ticket异常：[{}]",e.getMessage());
            }
        }
        return ticket;
    }

    /**
     * 获取token
     * @return
     */
    private synchronized String getToken() {
        String token = null;
        //查询token是否存在 存在直接从缓存中获取
        if (!redisTools.hasKey(TencentConstant.APPID)) {
            log.info("tencentCloud  redis's token out of time , begin require token and delete this token's all ticket");
            //由于token 失效后 token签发的ticket 全部失效 所有删除掉缓存中的ticket
            redisTools.delete(TencentConstant.SECRET_KEY);
            //构建请求token的地址
            StringBuffer requestUrl = new StringBuffer(TencentConstant.GET_TOKEN_URL)
                    .append("?app_id=").append(TencentConstant.APPID)
                    .append("&secret=").append(TencentConstant.SECRET_KEY)
                    .append("&grant_type=client_credential&version=1.0.0");
            String result = null;
            try {
                log.info("tencentCloud token request params：[{}]",requestUrl.toString());
                result = OkHttp.get(requestUrl.toString());
                log.info("tencentCloud token response params：[{}]",result);
                JSONObject jsonObject = JSON.parseObject(result);
                if (jsonObject.getIntValue("code") == 0) {
                    token =  jsonObject.getString("access_token");
                    //将获取到的token放入缓存 有效期120分钟 我们设置20分钟
                    redisTools.save(TencentConstant.APPID,token,60);
                } else {
                    log.error("获取腾讯云h5视频认证token异常：[{}]",result);
                }
            } catch (IOException e) {
                log.error("获取腾讯云h5视频认证token异常：[{}]",e.getMessage());
            }
        } else {
            token = redisTools.get(TencentConstant.APPID);
        }
        return token;
    }



    /**
     * 签名
     * @param values
     * @param ticket
     * @return
     */
    private String sign(List<String> values, String ticket) {
        if (values == null) {
            throw new NullPointerException("values is null");
        }
        values.removeAll(Collections.singleton(null));
        values.add(ticket);
        java.util.Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        System.out.println(sb);
        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
    }
}
