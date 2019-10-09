package com.ztk.starter.ds.scanner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztk.starter.ds.DSContext;
import com.ztk.starter.ds.annotation.data.DC;
import com.ztk.starter.ds.annotation.data.DF;
import com.ztk.starter.ds.annotation.service.DSH;
import com.ztk.starter.ds.enums.DataType;
import com.ztk.starter.ds.http.HttpClientUtil;
import com.ztk.starter.ds.model.DSRequest;
import com.ztk.starter.ds.model.DSResponse;
import com.ztk.starter.ds.storage.handler.DSHandler;
import io.swagger.models.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class DSAnnotationHandler<T> extends AbstractInvocation<T> {

    public DSAnnotationHandler(Class<T> target) {
        super(target);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DSMappingHandler dsMappingHandler = DSContext.getContext().getBean(DSMappingHandler.class);
        DSMapping dsMapping = dsMappingHandler.dsMapping(target);
        if (!dsMapping.checkMethod(method)) {
            log.info("method {} is not a abstract method, execute the method with default content!", method.getName());
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(target.getClass());
            constructor.setAccessible(true);
            return constructor.newInstance(target)
                    .unreflectSpecial(method, target)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        } else {
            if (args == null || args.length == 0) {
                throw new RuntimeException("参数错误，为了相对容易控制，请求参数未使用注解，使用参数方式传递");
            }
            Object param = args[0];
            if (!param.getClass().isAssignableFrom(DSRequest.class)) {
                throw new RuntimeException("参数错误，参数为 DSRequest");
            }
            DSRequest request = (DSRequest) param;
            String url = request.getUrl() + request.getPath();
            Map<String, Object> params = JSONObject.parseObject(request.getParam());
            Map<String, Object> headers = JSONObject.parseObject(request.getHeaders());
            String result = null;
            // 1、请求数据
            DSH dsh = method.getAnnotation(DSH.class);
            HttpMethod httpMethod = dsh.method();
            if (HttpMethod.GET.equals(httpMethod)) {
                result = HttpClientUtil.httpGet(url, headers, params);
            }
            if (HttpMethod.POST.equals(httpMethod)) {
                result = HttpClientUtil.httpPost(url, headers, params);
            }
            // 2、解析数据
            final Class<?> entityClazz = dsh.entity();
            DC dc = entityClazz.getAnnotation(DC.class);
            if (dc == null) {
                return new DSResponse()
                        .setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                        .setMessage("entity " + entityClazz.getSimpleName() + " need annotated by " + dc.getClass().getSimpleName());
            }
            // baseKey 为第三方返回数据的主 key name
            String baseKey = dc.value();
            DataType dataType = dc.type();
            if (DataType.JSON.equals(dataType)) {
                // 返回结果
                JSONObject resultJson = JSONObject.parseObject(result);
                final Object entityClass = resultJson.get(baseKey);
                if (entityClass.getClass().isAssignableFrom(JSONObject.class)) {
                    // 返回结果为对象
                }
                if (entityClass.getClass().isAssignableFrom(JSONArray.class)) {
                    // 返回结果为数组
                    JSONArray jsonArray = (JSONArray) entityClass;
                    final List<Object> entityList = new ArrayList<>();
                    jsonArray.stream().forEach(entityArr -> {
                        try {
                            Constructor constructor = entityClazz.getDeclaredConstructor();
                            if (constructor != null) {
                                constructor.setAccessible(true);
                                Object entity = constructor.newInstance();
                                Field[] fields = entity.getClass().getDeclaredFields();
                                Arrays.stream(fields).forEach(field -> {
                                    // set parameter
                                    DF df = field.getAnnotation(DF.class);
                                    if (df != null) {
                                        String thirdField = df.value();
                                        JSONObject entityObj = (JSONObject) entityArr;
                                        Object fieldVal = entityObj.get(thirdField);
                                        if (Objects.nonNull(fieldVal)) {
                                            try {
                                                field.setAccessible(true);
                                                // 参数类型转换 - 本地实体统一使用 String 类型处理数据
                                                if (fieldVal.getClass().isAssignableFrom(String.class)) {
                                                    field.set(entity, fieldVal);
                                                } else {
                                                    // todo 类型转换 - Integer、Long、Data、LocalDateTime、LocalDate ...
                                                    if (fieldVal.getClass().isAssignableFrom(Integer.class)) {
                                                        field.set(entity, Integer.toString((Integer) fieldVal));
                                                    }
                                                }
                                            } catch (IllegalAccessException e) {
                                                throw new RuntimeException("error when set value of field " + field + " in class " + entityClazz.getSimpleName());
                                            }
                                        }
                                    }
                                });
                                entityList.add(entity);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    return new DSResponse()
                            .setCode(0)
                            .setMessage("测试执行完成")
                            .setData(entityList);
                }
            }
            if (DataType.XML.equals(dataType)) {
                // todo xml 需要 xml 解析工具
            }
            // 3、转换数据

            // 4、用户 handler
            DSHandler handler = dsMapping.getHandler(method);
            // todo handler 使用 动态代理处理
            handler.handle();
            return new DSResponse().setMessage("测试执行完成");
        }
    }
}