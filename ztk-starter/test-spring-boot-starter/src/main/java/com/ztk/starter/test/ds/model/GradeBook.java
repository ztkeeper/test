package com.ztk.starter.test.ds.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztk.starter.ds.storage.Entity;
import lombok.Data;

import java.util.List;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:23
 */
@Data
public class GradeBook implements Entity<GradeBook> {

    @JSONField(name = "bookId")
    private String bookId;

    @JSONField(name = "book")
    private List<Book> book;
}
