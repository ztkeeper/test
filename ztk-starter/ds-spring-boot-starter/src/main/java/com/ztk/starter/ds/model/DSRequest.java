package com.ztk.starter.ds.model;

import lombok.Data;

/**
 * 请求参数
 *
 * @author sunyue
 * @date 2019/9/30 下午1:09
 */
@Data
public class DSRequest {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 资源路径
     */
    private String path;

    /**
     * 请求参数 - json
     */
    private String param;

    /**
     * 请求参数 - json
     */
    private String headers;

}
