package com.ztk.starter.ds.http;

import com.alibaba.fastjson.JSONObject;
import com.ztk.starter.ds.model.DSRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpOptions;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * http 请求执行器
 *
 * @author sunyue
 * @date 2019/10/10 上午10:49
 */
@Slf4j
public class HttpRequestActuator {

    public static String request(HttpMethod httpMethod, DSRequest request) {
        String url = request.getUrl() + request.getPath();
        Map<String, Object> params = JSONObject.parseObject(request.getParam());
        Map<String, Object> headers = JSONObject.parseObject(request.getHeaders());
        switch (httpMethod) {
            case POST:
                return post(url, params, headers, null);
            case GET:
                return get(url, params, headers, null);
            default:
                return null;
        }
    }

    private static String get(String url, Map<String, ?> params, Map<String, ?> headers, HttpOptions httpOptions) {
        try {
            if (httpOptions != null) {
                return HttpClientUtil.httpGet(url, params, httpOptions);
            }
            if (headers == null || headers.isEmpty()) {
                return HttpClientUtil.httpGet(url, params);
            }
            return HttpClientUtil.httpGet(url, params, headers);
        } catch (Exception e) {
            log.error("http get request error! error:{}", e);
            return null;
        }
    }

    private static String post(String url, Map<String, ?> headers, Map<String, ?> params, HttpOptions httpOptions) {
        try {
            if (httpOptions != null) {
                return HttpClientUtil.httpPost(url, params, httpOptions);
            }
            if (headers == null || headers.isEmpty()) {
                return HttpClientUtil.httpPost(url, params);
            }
            return HttpClientUtil.httpPost(url, params, headers);
        } catch (Exception e) {
            log.error("http post request error! error:{}", e);
            return null;
        }
    }

    private static String put() {
        return null;
    }

    private static String delete() {
        return null;
    }

}
