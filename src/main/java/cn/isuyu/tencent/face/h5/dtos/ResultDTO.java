package cn.isuyu.tencent.face.h5.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 下午2:44
 */
@Data
@Accessors(chain = true)
public class ResultDTO implements Serializable {

    /**
     * 	人脸核身结果的返回码
     * 0：人脸核身成功
     * 其他错误码：失败
     */
    private String code;

    /**
     * 订单号，由合作方上送，每次唯一，
     * 此信息为本次人脸核身上送的信息
     */
    private String orderNo;

    /**
     * 本次请求返回的唯一标识，
     * 此信息为本次人脸核身上送的信息
     */
    private String h5faceId;

    /**
     * 对 URL 参数 AppID、orderNo
     * 和 SIGN ticket、code 的签名
     */
    private String newSignature;

    /**
     * 	活体检测得分，如活体检测不通过，
     * 	则不返回该字段
     */
    private String liveRate;

}
