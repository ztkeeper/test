package com.ztk.starter.test.ds.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztk.starter.ds.storage.Entity;
import lombok.Data;

import java.util.List;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/10/10 下午2:29
 */
@Data
public class Lesson implements Entity<Lesson> {

    @JSONField(name = "id")
    private String id;

    @JSONField(name = "info")
    private String name;

    @JSONField(name = "imgPortraitUrl")
    private String coverUrl;

    @JSONField(name = "topics")
    private List<Topic> topics;
}
