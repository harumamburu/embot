package com.mylab.embot.config;

import com.google.common.primitives.Primitives;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.FactoryBean;

import javax.swing.*;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class PropertiesBindingBeanSetsFactory<T> implements FactoryBean<Set<T>> {

    private String propertyPrefix;
    private Class<T> targetType;
    private Properties properties;

    @Override
    public Class<?> getObjectType() {
        return HashSet.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


    public String getPropertyPrefix() {
        return propertyPrefix;
    }

    public void setPropertyPrefix(String propertyPrefix) {
        this.propertyPrefix = propertyPrefix;
    }

    public Class<T> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<T> targetType) {
        this.targetType = targetType;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public Set<T> getObject() throws Exception {
        Function<Map.Entry<Object, Object>, Integer> mapClassifier =
                entry -> Integer.valueOf(StringUtils.substringBetween((String) entry.getKey(), ".", "."));

        Collector<Map.Entry<Object, Object>, ?, Map<String, Object>> mapCollector = Collectors.toMap(
                entry -> StringUtils.substringAfterLast((String) entry.getKey(), "."),
                Map.Entry::getValue);

        Map<Integer, Map<String, Object>> beansMap =
                properties.entrySet().stream()
                        .filter(entry -> ((String) entry.getKey()).contains(propertyPrefix))
                        .collect(groupingBy(mapClassifier, mapCollector));

        Set<T> beansSet = new HashSet<>();
        for(Map<String, Object> beanDefinition : beansMap.values()) {
            T item = targetType.newInstance();
            populateBeanFromMap(item, beanDefinition);
            beansSet.add(item);
        }

        return beansSet;
    }

    /**
     * Converts target object's propety to approptiate primitive, wrapper or enum type
     * @param propertyName
     * @param propertyValue
     * @return converted value instance
     * @throws Exception
     */
    private Object convertIfNecessary(String propertyName, String propertyValue) throws Exception {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(targetType, propertyName);
        Class<?> propertyType = Primitives.wrap(descriptor.getPropertyType());

        if (propertyType == String.class) {
            return propertyValue;
        }

        return propertyType.getDeclaredMethod("valueOf", String.class).invoke(propertyType, propertyValue);
    }

    private void populateBeanFromMap(Object item, Map<String, Object> params) {
        params.entrySet().stream()
                .forEach(entry -> {
                    try {
                        Object value = convertIfNecessary(entry.getKey(), String.valueOf(entry.getValue()));
                        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item.getClass(), entry.getKey());
                        descriptor.getWriteMethod().invoke(item, value);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
