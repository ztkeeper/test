package com.ztk.starter.ds;

import com.ztk.starter.ds.scanner.DSAnnotationHandler;
import com.ztk.starter.ds.scanner.DSMappingHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 初始化入口
 *
 * @author sunyue
 * @date 2019/9/30 下午1:21
 */

//@EnableConfigurationProperties({***.class})
public class SystemInit implements ApplicationContextAware, InitializingBean, BaseConfiguration {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        DSContext dsContext = getBean(DSContext.class);
        dsContext.setApplicationContext(this.applicationContext);
        dsContext.registerDSMappingHandler(getBean(DSMappingHandler.class));
        // todo for other beans
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private <T> T getBean(Class<T> clazz) {
        return this.applicationContext.getBean(clazz);
    }
}
