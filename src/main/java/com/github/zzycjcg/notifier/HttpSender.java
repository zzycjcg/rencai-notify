package com.github.zzycjcg.notifier;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by zzy on 2017/3/1.
 */
@Service
public class HttpSender {
    private static final Logger log = LoggerFactory.getLogger(HttpSender.class);
    private static final CloseableHttpClient httpClient;

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(30000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public String send() throws IOException {
        HttpGet get = new HttpGet("http://rsdl.zjrc.com/queryFile.do?zihan=1");
        get.setHeader("Upgrade-Insecure-Requests", "1");
        get.setHeader("Referer", "http://rsdl.zjrc.com/");
        get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        get.setHeader("Accept-Language", "en,zh-CN;q=0.8,zh;q=0.6");
        get.setHeader("Cookie", "JSESSIONID=33B9D58EFB32D08CDD1D86A104C66CE5; _gscu_689210409=84928471nzx1ss13; _gscbrs_689210409=1; clientlanguage=zh_CN");
        CloseableHttpResponse response = httpClient.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        String msg = EntityUtils.toString(response.getEntity(), "utf-8");
        if (statusCode / 200 != 1) {
            log.error("status={}, msg={}", response.getStatusLine(), msg);
            return null;
        }
        response.close();
        return msg;
    }
}

