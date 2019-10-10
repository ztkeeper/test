package com.ztk.starter.test.ds.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztk.starter.ds.annotation.data.DC;
import com.ztk.starter.ds.annotation.data.DF;
import com.ztk.starter.ds.enums.DataType;
import com.ztk.starter.ds.storage.Entity;
import lombok.Data;

import java.util.List;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:26
 */
@Data
public class Book implements Entity<Book> {

    @JSONField(name = "id")
    private Integer id;

    @JSONField(name = "status")
    private String status;

    @JSONField(name = "info")
    private String name;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "coverUrl")
    private String coverUrl;

    @JSONField(name = "lessons")
    private List<Lesson> lessons;

}
