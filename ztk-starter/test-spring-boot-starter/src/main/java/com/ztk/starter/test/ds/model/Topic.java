package com.ztk.starter.test.ds.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztk.starter.ds.storage.Entity;
import lombok.Data;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/10/10 下午4:55
 */
@Data
public class Topic implements Entity<Topic> {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "foreignTitle")
    private String foreignTitle;

    @JSONField(name = "nativeTitle")
    private String nativeTitle;

    @JSONField(name = "imgUrl")
    private String imageUrl;

}
