package web.info.toos;

public interface Constants {
    /**
     * 佳成物流 货物跟踪url
     */
    String URL_Track = "http://api.jcex.com/JcexJson/api/notify/sendmsg";

    String TRACK_SIZE = "0/3000 * * * * ?";

    /**
     * 响应请求成功
     */
    String HTTP_RES_CODE_200_VALUE = "success";
    /**
     * 响应请求成功code
     */
    Integer HTTP_RES_CODE_200 = 200;
    /**
     * 系统错误
     */
    Integer HTTP_RES_CODE = -1;


}
