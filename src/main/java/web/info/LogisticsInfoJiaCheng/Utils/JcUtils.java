package web.info.LogisticsInfoJiaCheng.Utils;
import com.sun.deploy.net.URLEncoder;
import com.sun.xml.internal.messaging.saaj.util.Base64;
import java.io.UnsupportedEncodingException;


public class JcUtils {

    /**
     * 佳成 数据接口设计  返回
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeParam(String data) throws UnsupportedEncodingException {
        String dataCode = URLEncoder.encode(data, "UTF-8");
        Base64 base64 = new Base64();
        byte[] base64Bs = base64.encode(dataCode.getBytes("UTF-8"));
        String encodeParam = new String(base64Bs, "UTF-8");
        return encodeParam;
    }
}
