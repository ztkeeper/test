package com.ztk.starter.ds.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * http 请求处理工具
 *
 * @author sunyue
 * @date 2019/10/8 上午10:13
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final Integer RETRY_COUNT = 0;
    private static final Integer POOL_HTTP_CONNECT_MAX = 100;
    private static final Integer ROUTE_HTTP_CONNECT_MAX = 50;
    private static final Integer DEFAULT_SOCKET_TIMEOUT = 3600;
    private static final Integer DEFAULT_CONNECT_TIMEOUT = 600;

    /**
     * HttpClient 连接池
     */
    private static PoolingHttpClientConnectionManager CONNECTION_MANAGER = initPoolingHttpClientConnectionManager();

    public static String httpGet(String url) throws Exception {
        return httpGet(url, null, null, null);
    }

    public static String httpGet(String url, HttpOptions httpOptions) throws Exception {
        return httpGet(url, null, null, httpOptions);
    }

    public static String httpGet(String url, Map<String, ?> params) throws Exception {
        return httpGet(url, null, params, null);
    }

    public static String httpGet(String url, Map<String, ?> params, HttpOptions httpOptions) throws Exception {
        return httpGet(url, null, params, httpOptions);
    }

    public static String httpGet(String url, Map<String, ?> headers, Map<String, ?> params) throws Exception {
        return httpGet(url, headers, params, null);
    }

    /**
     * 发送 HTTP GET请求
     *
     * @param url
     * @param headers     请求头
     * @param params      请求参数
     * @param httpOptions 配置参数，如重试次数、超时时间等。
     * @return
     * @throws Exception
     */
    public static String httpGet(String url, Map<String, ?> headers, Map<String, ?> params, HttpOptions httpOptions) throws Exception {
        // 1、设置请求地址
//        URIBuilder ub = new URIBuilder();
//        ub.setCharset(StandardCharsets.UTF_8);
//        ub.setPath(url);
        URI uri = new URI(url);
        URIBuilder ub = new URIBuilder(uri);
        // 2、设置请求参数
        List<NameValuePair> pairs = convertParams2NVPS(params);
        if (!pairs.isEmpty()) {
            ub.setParameters(pairs);
        }
        HttpGet httpGet = new HttpGet(ub.build());
        // 3、设置请求头
        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, ?> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        return doHttp(httpGet, httpOptions);
    }


    public static String httpPost(String url, Map<String, ?> params) throws Exception {
        return httpPost(url, null, params, null);
    }

    public static String httpPost(String url, Map<String, ?> params, HttpOptions httpOptions) throws Exception {
        return httpPost(url, null, params, httpOptions);
    }


    public static String httpPost(String url, Map<String, ?> headers, Map<String, ?> params) throws Exception {
        return httpPost(url, headers, params, null);
    }

    /**
     * 发送 HTTP POST请求
     *
     * @param url
     * @param headers     请求头
     * @param params      请求参数
     * @param httpOptions 配置参数，如重试次数、超时时间等。
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, ?> headers, Map<String, ?> params, HttpOptions httpOptions) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        // 转换请求参数
        List<NameValuePair> pairs = convertParams2NVPS(params);
        if (!pairs.isEmpty()) {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8.name()));
        }

        // 设置请求头
        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, ?> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        return doHttp(httpPost, httpOptions);
    }


    /**
     * 发送 HTTP POST请求，参数格式JSON
     * <p>请求参数是JSON格式，数据编码是UTF-8</p>
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String httpPostJson(String url, String param, HttpOptions httpOptions) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");

        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));

        return doHttp(httpPost, httpOptions);
    }

    /**
     * 发送 HTTP POST请求，参数格式XML
     * <p>请求参数是XML格式，数据编码是UTF-8</p>
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String httpPostXml(String url, String param, HttpOptions httpOptions) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        httpPost.addHeader("Content-Type", "application/xml; charset=UTF-8");

        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));

        return doHttp(httpPost, httpOptions);
    }


    /**
     * 转换请求参数，将Map键值对拼接成QueryString字符串
     *
     * @param params
     * @return
     */
    public static String convertParams2QueryStr(Map<String, ?> params) {
        List<NameValuePair> pairs = convertParams2NVPS(params);
        return URLEncodedUtils.format(pairs, StandardCharsets.UTF_8.name());
    }

    /**
     * 转换请求参数
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> convertParams2NVPS(Map<String, ?> params) {
        if (params == null) {
            return Collections.emptyList();
        }
        return params.entrySet().stream().map(param -> new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue()))).collect(Collectors.toList());
    }

    /**
     * 发送 HTTP 请求
     *
     * @param request
     * @return
     * @throws Exception
     */
    private static String doHttp(HttpRequestBase request, HttpOptions httpOptions) throws Exception {
        if (Objects.isNull(httpOptions)) {
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                    .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                    .build();
            httpOptions = new HttpOptions();
            httpOptions.setConfig(config);
        }
        // 1、设置超时时间
        if (Objects.nonNull(httpOptions.getConfig().getSocketTimeout())) {
            request.setConfig(RequestConfig.custom().setSocketTimeout(httpOptions.getConfig().getSocketTimeout()).build());
        }
        // 2、设置重试策略
        HttpRequestRetryHandler httpRequestRetryHandler = null;
        if (Objects.nonNull(httpOptions.getConfig())) {
            httpRequestRetryHandler = new DefaultHttpRequestRetryHandler(RETRY_COUNT, false);
        }

        // 3、通过连接池获取连接对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).setRetryHandler(httpRequestRetryHandler).build();
        return doRequest(httpClient, request);

    }

    /**
     * 处理Http/Https请求，并返回请求结果
     * <p>注：默认请求编码方式 UTF-8</p>
     *
     * @param httpClient
     * @param request
     * @return
     * @throws Exception
     */
    private static String doRequest(CloseableHttpClient httpClient, HttpRequestBase request) throws Exception {
        String result = null;
        CloseableHttpResponse response = null;

        try {
            // 获取请求结果
            response = httpClient.execute(request);
            // 解析请求结果
            HttpEntity entity = response.getEntity();
            // 转换结果
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
            // 关闭IO流
            EntityUtils.consume(entity);
        } finally {
            if (null != response) {
                response.close();
            }
        }
        return result;
    }


    /**
     * 初始化连接池
     *
     * @return
     */
    private static PoolingHttpClientConnectionManager initPoolingHttpClientConnectionManager() {
        // 初始化连接池，可用于请求HTTP/HTTPS（信任所有证书）
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(getRegistry());
        // 连接池的最大连接数
        connectionManager.setMaxTotal(POOL_HTTP_CONNECT_MAX);
        // 每个路由的最大连接数
        connectionManager.setDefaultMaxPerRoute(ROUTE_HTTP_CONNECT_MAX);
        return connectionManager;
    }


    /**
     * 获取 HTTPClient注册器
     *
     * @return
     * @throws Exception
     */
    private static Registry<ConnectionSocketFactory> getRegistry() {
        try {
            return RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", new SSLConnectionSocketFactory(SSLContext.getDefault()))
                    .build();
        } catch (Exception e) {
            logger.error("[ERROR_getRegistry]", e);
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("customConfigId", "custom_config_id_2");
        params.put("token", "002684ba533f61234d527871f0c8db51ae6c6b93");
        params.put("type", "3");
        try {
            String result = HttpClientUtil.httpGet("https://www.6tiantian.com/api/teacher/book/list", params);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
