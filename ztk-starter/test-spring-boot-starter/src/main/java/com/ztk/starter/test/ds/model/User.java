package com.ztk.starter.test.ds.model;

import com.ztk.starter.ds.annotation.data.DC;
import com.ztk.starter.ds.annotation.data.DF;
import com.ztk.starter.ds.annotation.data.DFInner;
import com.ztk.starter.ds.enums.DataType;
import com.ztk.starter.ds.storage.Entity;
import lombok.Data;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:23
 */
@Data
@DC(value = "user", type = DataType.JSON)
public class User implements Entity<User> {

    @DF(value = "id")
    private String id;

    @DF(value = "name")
    private String name;

    @DF(value = "sex")
    private String sex;

    @DFInner
    private Work work;
}
