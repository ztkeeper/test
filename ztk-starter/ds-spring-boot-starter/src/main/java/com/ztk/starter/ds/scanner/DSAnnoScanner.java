package com.ztk.starter.ds.scanner;

import com.ztk.starter.ds.DSContext;
import com.ztk.starter.ds.annotation.service.DS;
import com.ztk.starter.ds.annotation.service.DSH;
import com.ztk.starter.ds.storage.handler.DSHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务扫描器
 *
 * @author sunyue
 * @date 2019/9/30 下午1:58
 */
@Slf4j
public class DSAnnoScanner extends ClassPathBeanDefinitionScanner {

    private transient final Class dsContext = DSContext.class;
    private transient final Class dsMappingHandler = DSMappingHandler.class;

    private final transient Map<Class, DSMapping> dsMappingCache = new ConcurrentHashMap<>();

    private BeanDefinitionRegistry registry;

    public DSAnnoScanner(BeanDefinitionRegistry registry) {
        super(registry);
        this.registry = registry;
        registerBeanDefinition(registry, dsContext);
    }

    private void registerBeanDefinition(BeanDefinitionRegistry registry, Class clazz, Object... params) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        registry.registerBeanDefinition(clazz.getName(), builder.getBeanDefinition());
        registry.getBeanDefinition(clazz.getName());
        ConstructorArgumentValues constructorArgumentValues = builder.getBeanDefinition().getConstructorArgumentValues();
        if (params != null) {
            for (Object param : params) {
                constructorArgumentValues.addGenericArgumentValue(param);
            }
        }
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitions) {
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(genericBeanDefinition.getBeanClassName());
            genericBeanDefinition.setBeanClass(DSFactoryBean.class);
        }
        registerBeanDefinition(registry, dsMappingHandler, dsMappingCache);
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // 只处理 @DS 标注的 service
        if (beanDefinition.getMetadata().isInterface()
                && beanDefinition.getMetadata().isIndependent()
                && beanDefinition.getMetadata().hasAnnotation(DS.class.getName())) {
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> beanClazz;
            try {
                beanClazz = Class.forName(beanClassName);
                Method[] methods = beanClazz.getDeclaredMethods();
                Map<Method, DSHandler> methodHandlerMap = new HashMap<>(4);
                for (Method method : methods) {
                    DSH dsh = method.getAnnotation(DSH.class);
                    if (dsh != null) {
                        Class<? extends DSHandler> dsHandlerClass = dsh.handler();
                        try {
                            methodHandlerMap.put(method, dsHandlerClass.newInstance());
                        } catch (InstantiationException | IllegalAccessException e) {
                            log.error("error when create bean DSHandler:{}, No parametric constructor found", dsHandlerClass.getSimpleName());
                            e.printStackTrace();
                        }
                    }
                }
                if (methodHandlerMap.size() > 0) {
                    DSMapping dsMapping = new DSMapping(beanClazz, methodHandlerMap);
                    dsMappingCache.put(beanClazz, dsMapping);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }
}