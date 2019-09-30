package com.ztk.starter.test.ds.service;

import com.ztk.starter.ds.annotation.service.DS;
import com.ztk.starter.ds.annotation.service.DSH;
import com.ztk.starter.ds.model.DSRequest;
import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.test.ds.handler.UserHandler;
import com.ztk.starter.test.ds.model.User;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:35
 */
@DS
public interface TestService {

    @DSH(handler = UserHandler.class, entity = User.class)
    DSResponse test(DSRequest request);
}
