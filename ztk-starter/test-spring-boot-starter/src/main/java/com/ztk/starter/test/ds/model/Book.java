package com.ztk.starter.test.ds.model;

import com.ztk.starter.ds.annotation.data.DC;
import com.ztk.starter.ds.annotation.data.DF;
import com.ztk.starter.ds.enums.DataType;
import com.ztk.starter.ds.storage.Entity;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:26
 */
@DC(value = "book", type = DataType.JSON)
public class Book implements Entity<Book> {

    @DF(value = "id")
    private Integer id;

    @DF(value = "info")
    private String name;

    @DF(value = "coverUrl")
    private String coverUrl;

}
