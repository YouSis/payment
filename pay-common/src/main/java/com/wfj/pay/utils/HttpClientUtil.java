package com.wfj.pay.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * Created by wjg on 2017/7/19.
 */
public class HttpClientUtil {
    private static int readTimeoutMs = 10 * 1000;
    private static int connectTimeoutMs = 6 * 1000;

    /**
     * 发送post请求
     * @param url
     * @param data
     * @param contentType
     * @return
     * @throws Exception
     */
    public static String sendPost(String url, String data, String contentType) throws Exception {
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClientBuilder.create()
                    .setConnectionManager(connManager)
                    .build();
            HttpPost httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
            httpPost.setConfig(requestConfig);

            StringEntity postEntity = new StringEntity(data, "UTF-8");
            httpPost.addHeader("Content-Type", contentType);
            httpPost.setEntity(postEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    /**
     * 发送json数据
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public static String sendPostJson(String url,String data) throws Exception{
        return sendPost(url,data,"application/json");
    }
}
