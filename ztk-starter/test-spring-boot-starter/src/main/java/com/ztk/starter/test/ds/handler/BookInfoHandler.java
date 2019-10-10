package com.ztk.starter.test.ds.handler;

import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.ds.storage.handler.DSHandler;
import com.ztk.starter.test.ds.model.TianTianBookInfoEntity;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/10/10 下午5:48
 */
public class BookInfoHandler implements DSHandler<TianTianBookInfoEntity, DSResponse> {

    @Override
    public DSResponse handle(TianTianBookInfoEntity entity) {
        System.out.println("this is handler test");
        return new DSResponse()
                .setCode(0)
                .setMessage("同步处理完成")
                .setData(entity);
    }
}
