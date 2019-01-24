package com.ipampas.example.util;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * User : liulu
 * Date : 2017/11/2 16:47
 * version $Id: BeanTools.java, v 0.1 Exp $
 */
public class BeanTools {

    public static <S, T> T convert(S source, T target) {
        return convert(source, target, null);
    }

    public static <S, T> T convert(S source, T target, String[] ignoreProperties) {
        return convert(source, target, ignoreProperties, null);
    }

    private static <S, T> T convert(S source, T target, String[] ignoreProperties, ConvertCallBack<S, T> convertCallBack) {
        if (source == null || target == null) {
            return target;
        }
        copyProperties(source, target, null, ignoreProperties);
        if (convertCallBack != null) {
            convertCallBack.callBack(source, target);
        }
        return target;
    }

    public static <S, T> List<T> convert(Class<T> clazz, List<S> sources) {
        return convert(clazz, sources, (ConvertCallBack<S, T>) null);
    }

    public static <S, T> List<T> convert(Class<T> clazz, List<S> sources, ConvertCallBack<S, T> convertCallBack) {
        if (sources == null || sources.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(sources.size());
        for (S source : sources) {
            T target = null;
            try {
                target = clazz.newInstance();
            } catch (Exception e) {
                // do nothing
            }
            if (target != null) {
                result.add(convert(source, target, null, convertCallBack));
            }
        }
        return result;
    }

    public interface ConvertCallBack<S, T> {
        void callBack(S source, T target);
    }

    @SneakyThrows
    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            return null;
        }

        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
        Map<String, Object> result = new HashMap<>(pds.length);
        for (PropertyDescriptor pd : pds) {
            if (pd.getReadMethod() == null) {
                continue;
            }
            Object value = pd.getReadMethod().invoke(bean);
            if (!StringUtils.isEmpty(value)) {
                result.put(pd.getName(), value);
            }
        }
        return result;
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param editable         the class (or interface) to restrict property setting to
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (value != null) {
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

}
