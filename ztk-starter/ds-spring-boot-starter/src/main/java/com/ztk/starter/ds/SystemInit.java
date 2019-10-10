package com.ztk.starter.ds;

import com.ztk.starter.ds.enums.DataType;
import com.ztk.starter.ds.resolver.JsonResolver;
import com.ztk.starter.ds.resolver.ResolverFactory;
import com.ztk.starter.ds.resolver.XmlResolver;
import com.ztk.starter.ds.scanner.DSAnnotationHandler;
import com.ztk.starter.ds.scanner.DSMappingHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

/**
 * 初始化入口
 *
 * @author sunyue
 * @date 2019/9/30 下午1:21
 */

//@EnableConfigurationProperties({***.class})
public class SystemInit implements ApplicationContextAware, InitializingBean, BaseConfiguration {

    private ApplicationContext applicationContext;


    private ResolverFactory resolverFactory() {
        return ResolverFactory
                .builder()
                .bindResolver(DataType.JSON, new JsonResolver())
                .bindResolver(DataType.XML, new XmlResolver())
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DSContext dsContext = getBean(DSContext.class);
        dsContext.setApplicationContext(this.applicationContext);
        dsContext.registerDSMappingHandler(getBean(DSMappingHandler.class));
        dsContext.registerResolverFactory(resolverFactory());
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
