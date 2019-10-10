package com.ztk.starter.test.ds.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztk.starter.ds.storage.Entity;
import lombok.Data;

import java.util.List;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/10/10 下午3:13
 */
@Data
public class TianTianBookInfoEntity implements Entity<TianTianBookInfoEntity> {

    @JSONField(name = "book")
    private Book book;

}
