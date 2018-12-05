package web.info.LogisticsInfoJiaCheng.paramsConfig;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import web.info.LogisticsInfoJiaCheng.Utils.JcUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ParamsConfig {
    /**
     *
     * @param SingleNumber 单号
     * @return 返回数据
     */
    public static List<NameValuePair> trackParamsConfig(String SingleNumber) {
        List<NameValuePair> params = new ArrayList<>();
        JSONObject json = new JSONObject();
        json.put("customerid", "-1");
        json.put("waybillnumber", SingleNumber);
        NameValuePair pair1 = null;
        NameValuePair pair2 = null;
        try {
            pair1 = new BasicNameValuePair("service", "track");
            pair2 = new BasicNameValuePair("data_body", JcUtils.encodeParam(json.toJSONString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.add(pair1);
        params.add(pair2);
        return params;
    }

}
