package web.info.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * 封装 http get post
 */
public class HttpUtils {


    /**
     * 封装超时时间
     *
     * @return
     */
    public static RequestConfig httpTime(int timeOut) {
        return RequestConfig.custom().setConnectTimeout(timeOut)  //setConnectTimeout 设置建立连接超时  5秒
                .setConnectionRequestTimeout(timeOut)  //setConnectionRequestTimeout设置 请求超时时间 5秒
                .setSocketTimeout(timeOut) //setSocketTimeout socket连接超时 5秒
                .setRedirectsEnabled(true)//setRedirectsEnabled 允许重定向
                .build();//通过build去构建;
    }

    /**
     * 封装post
     *
     * @return
     */
    public static String doPost(String url, String data, int timeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置url请求
        HttpPost httpPost = new HttpPost(url);
        //超时设置
        httpPost.setConfig(httpTime(timeout));
        httpPost.addHeader("Content-Type", "text/html; chartset=UTF-8");
        if (data != null && data instanceof String) { //使用字符串传参
            StringEntity stringEntity = new StringEntity(data, "UTF-8");
            httpPost.setEntity(stringEntity);
        }
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpEntity);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 佳程物流 对接接口
     *
     * @param url
     * @param params
     * @return
     */
    public static String postJcString(String url, List<NameValuePair> params, int timeout) {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String result = "";
        try {
            //超时设置
            httpPost.setConfig(httpTime(timeout));
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
