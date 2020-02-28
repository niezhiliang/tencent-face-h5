package cn.isuyu.tencent.face.h5.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 下午2:38
 */
@Data
@Accessors(chain = true)
public class FaceStartDTO implements Serializable {

    /**
     * 请添加小助手微信 faceid001，进行线下对接获取
     */
    private String webankAppId;

    /**
     * 接口版本号，默认参数值：1.0.0
     */
    private String version = "1.0.0";

    /**
     * 随机数：32位随机串（字母+数字组成的随机数）
     */
    private String nonce;

    /**
     * 订单号，由合作方上送，每次唯一，
     * 此信息为本次人脸核身上送的信息，不能超过32位
     */
    private String orderNo;

    /**
     * h5/geth5faceid 接口返回的唯一标识
     */
    private String h5faceId;

    /**
     * H5 人脸核身完成后回调的第三方 URL，需要第三方提供完整 URL 且做 URL Encode
     * 完整 URL Encode 示例：
     * 原 URL 为`https://cloud.tencent.com`
     * Encode 后为`https%3a%2f%2fcloud.tencent.com`
     */
    private String url;

    /**
     * 是否显示结果页面
     * 参数值为“1”：直接跳转到 url 回调地址
     * null 或其他值：跳转提供的结果页面
     */
    private String resultType;

    /**
     * 用户 ID，用户的唯一标识（不要带有特殊字符）
     */
    private String userId;

    /**
     * 签名
     */
    private String sign;

    /**
     * browser：表示在浏览器启动刷脸
     * App：表示在 App 里启动刷脸，默认值为 App
     */
    private String from;

    /**
     * 	跳转模式，参数值为“1”时，刷脸页面使用 replace 方式跳转，
     * 	不在浏览器 history 中留下记录；不传或其他值则正常跳转
     */
    private String redirectType;

}
