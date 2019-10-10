package com.ztk.starter.test.ds.handler;

import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.ds.storage.handler.DSHandler;
import com.ztk.starter.test.ds.model.TianTianBookPageEntity;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:38
 */
public class BookHandler implements DSHandler<TianTianBookPageEntity, DSResponse> {

    @Override
    public DSResponse handle(TianTianBookPageEntity entity) {
        System.out.println("this is handler test");
        return new DSResponse()
                .setCode(0)
                .setMessage("同步处理完成")
                .setData(entity);
    }

}
