package com.ztk.starter.ds.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午1:10
 */
@Setter
@Accessors(chain = true)
public class DSResponse {

    @Getter
    private Integer code;

    @Getter
    private String message;

    private Object data;

    public <T> T getData() {
        return (T) this.data;
    }
}
