package cn.isuyu.tencent.face.h5.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 上午11:04
 */
@Data
@Accessors(chain = true)
public class SendUserInfoDTO implements Serializable {

    private String webankAppId;

    /**
     * 订单号，由合作方上送，每次唯一，不能超过32位
     */
    private String orderNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 用户 ID ，用户的唯一标识（不能带有特殊字符）
     */
    private String userId;

    /**
     * 比对源照片，注意：原始图片不能超过500KB，且必须为 JPG 或 PNG 格式
     * 参数有值：使用合作伙伴提供的比对源照片进行比对，必须注照片是正脸可信照片，照片质量由合作方保证
     * 参数为空 ：根据身份证号+姓名使用权威数据源比对
     */
    private String sourcePhotoStr;

    /**
     * 比对源照片类型，注意： 如合作方上送比对源则必传，使用权威数据源可不传
     * 参数值为1：水纹正脸照
     * 参数值为2：高清正脸照
     */
    private String sourcePhotoType;

    /**
     * 默认参数值为：1.0.0
     */
    private String version = "1.0.0";

    /**
     * 签名
     */
    private String sign;

}
