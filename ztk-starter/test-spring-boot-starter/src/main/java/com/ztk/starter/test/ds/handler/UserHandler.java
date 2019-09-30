package com.ztk.starter.test.ds.handler;

import com.ztk.starter.ds.storage.handler.DSHandler;

/**
 * 类的描述
 *
 * @author sunyue
 * @date 2019/9/30 下午12:38
 */
public class UserHandler implements DSHandler {
    @Override
    public void handle() {
        System.out.println("this is handler test");
    }
}
