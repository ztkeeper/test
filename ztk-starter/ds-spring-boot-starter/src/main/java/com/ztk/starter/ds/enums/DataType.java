/*
 * XML 指代 第三方数据为 xml 格式
 * JSON 指代 第三方数据为 json 格式
 */
package com.ztk.starter.ds.enums;

/**
 * 数据格式
 *
 * @author sunyue
 * @date 2019/9/30 上午10:51
 */
public enum DataType {

    /**
     * xml 格式结果
     */
    XML,
    /**
     * json 格式结果
     */
    JSON;

    public enum EntityType {
        /**
         * object 形式数据
         */
        OBJECT,
        /**
         * array 形式数据
         */
        ARRAY;
    }

}
