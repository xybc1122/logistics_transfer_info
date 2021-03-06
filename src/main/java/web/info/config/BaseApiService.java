package web.info.config;


import org.springframework.stereotype.Component;
import web.info.toos.Constants;

@Component
public class BaseApiService {

    // 返回错误 ，可以传msg
    public static ResponseBase setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE, msg, null);
    }

    // 返回错误 ，可以传msg
    public static ResponseBase setResultError(String msg, Object data) {
        return setResult(Constants.HTTP_RES_CODE, msg, data);
    }

    // 返回错误 ，可以传msg 跟Code
    public static ResponseBase setResultError(Integer code, String msg) {
        return setResult(code, msg);
    }

    // 返回成功 ，可以传data值
    public static ResponseBase setResultSuccess(Object data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    // 返回成功 ，没有data值
    public static ResponseBase setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    // 返回成功 ，没有data值
    public static ResponseBase setResultSuccess(String msg) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }

    // 返回成功 ，没有data值
    public static ResponseBase setResultSuccess(String msg, Object data) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, data);
    }

    // 通用封装
    public static ResponseBase setResult(Integer code, String msg, Object data) {
        return new ResponseBase(code, msg, data);
    }

    // 通用封装
    public static ResponseBase setResult(Integer code, String msg) {
        return new ResponseBase(code, msg);
    }


}
