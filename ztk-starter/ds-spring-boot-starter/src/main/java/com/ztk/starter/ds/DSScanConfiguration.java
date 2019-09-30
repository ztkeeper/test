package com.ztk.starter.ds;

import com.ztk.starter.ds.annotation.EnableDS;
import com.ztk.starter.ds.scanner.DSAnnoScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 扫描入口
 *
 * @author sunyue
 * @date 2019/9/30 下午2:42
 */
public class DSScanConfiguration implements ImportBeanDefinitionRegistrar, BaseConfiguration {

    private final Class ANNOTATION_IMPORT = EnableDS.class;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        if (!annotationMetadata.hasAnnotation(ANNOTATION_IMPORT.getName())) {
            throw new RuntimeException("error when import class by @EnableDS !");
        }
        Set<String> annons = annotationMetadata.getAnnotationTypes();
        if (Objects.nonNull(annons) && annons.size() > 0) {
            for (String annoName : annons) {
                if (annoName.equals(ANNOTATION_IMPORT.getName())) {
                    Map<String, Object> annoFieldMap = annotationMetadata.getAnnotationAttributes(ANNOTATION_IMPORT.getName());
                    String[] dsPackages = (String[]) annoFieldMap.get(DS_PACKAGE);
                    if (dsPackages == null) {
                        throw new RuntimeException("error when import class by @EnableDS ! factoryPackages is null, no field!");
                    }
                    setAttributes(DS_PACKAGE, dsPackages);
                    scan(beanDefinitionRegistry);
                }
            }
        }
    }

    private void scan(BeanDefinitionRegistry beanDefinitionRegistry) {
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanDefinitionRegistry;
        DSAnnoScanner dsAnnoScanner = new DSAnnoScanner(factory);
        dsAnnoScanner.doScan((String[]) getAttributes(DS_PACKAGE));
    }
}