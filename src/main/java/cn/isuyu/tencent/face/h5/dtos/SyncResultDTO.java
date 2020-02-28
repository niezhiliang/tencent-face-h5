package cn.isuyu.tencent.face.h5.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 下午5:27
 */
@Data
@Accessors(chain = true)
public class SyncResultDTO implements Serializable {

    private String app_id;

    /**
     * 版本号，默认值：
     */
    private String version = "1.0.0";

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 订单号，合作方订单的唯一标识
     */
    private String order_no;

    /**
     * 签名值，使用本文生成的签名
     */
    private String sign;

    /**
     * 是否需要获取人脸识别的视频和文件
     * 值为1：返回视频和照片
     * 值为2：返回照片
     * 值为3：返回视频
     * 其他：不返回
     */
    private String get_file;

}
