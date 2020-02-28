package cn.isuyu.tencent.face.h5.constant;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2020/2/28 上午10:56
 */
public class TencentConstant {

    public static final String APPID = "";

    public static final String SECRET_KEY = "";


    /**
     * 获取token的地址
     */
    public static final String GET_TOKEN_URL = "https://idasc.webank.com/api/oauth2/access_token";

    /**
     * 获取ticket
     */
    public static final String GET_TICKET_URL = "https://idasc.webank.com/api/oauth2/api_ticket";

    /**
     * 上传用户身份信息
     */
    public static final String URL_SEND_USER_INFO = "https://idasc.webank.com/api/server/h5/geth5faceid";

    /**
     * 启动h5人脸核身接口
     */
    public static final String START_FACE_LOGIN = "https://ida.webank.com/api/web/login";

    /**
     * 身份认证查询接口
     */
    public static final String SYNC_QUERY_URL = "https://idasc.webank.com/api/server/sync";

}
