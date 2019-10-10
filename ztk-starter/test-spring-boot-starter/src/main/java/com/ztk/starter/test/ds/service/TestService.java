package com.ztk.starter.test.ds.service;

import com.ztk.starter.ds.annotation.service.DS;
import com.ztk.starter.ds.annotation.service.DSH;
import com.ztk.starter.ds.enums.DataType;
import com.ztk.starter.ds.enums.DataType.EntityType;
import com.ztk.starter.ds.model.DSRequest;
import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.test.ds.handler.BookHandler;
import com.ztk.starter.test.ds.handler.BookInfoHandler;
import com.ztk.starter.test.ds.model.TianTianBookInfoEntity;
import com.ztk.starter.test.ds.model.TianTianBookPageEntity;
import org.springframework.http.HttpMethod;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:35
 */
@DS
public interface TestService {

    @DSH(method = HttpMethod.GET,
            dataType = DataType.JSON,
            entity = TianTianBookPageEntity.class,
            entityType = EntityType.OBJECT,
            handler = BookHandler.class)
    DSResponse test(DSRequest request);

    @DSH(method = HttpMethod.GET,
            dataType = DataType.JSON,
            entity = TianTianBookInfoEntity.class,
            entityType = EntityType.OBJECT,
            handler = BookInfoHandler.class)
    DSResponse test2(DSRequest request);

}
