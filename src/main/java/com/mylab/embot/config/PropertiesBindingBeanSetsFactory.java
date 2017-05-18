package com.mylab.embot.config;

import com.google.common.primitives.Primitives;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.FactoryBean;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class PropertiesBindingBeanSetsFactory<T> implements FactoryBean<Set<T>> {

    private String propertyPrefix;
    private Class<T> targetType;
    private Properties properties;
    private Set<T> loadedBeans;

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
        loadedBeans = new HashSet<>();
        int lastIndex = -1;

        T item = null;

        for (String property: prefixFilteredProperties()) {
            // Actual value
            final String propertyValue = properties.getProperty(property);

            // Remove Prefix
            property = StringUtils.substringAfter(property, propertyPrefix + ".");

            // Split by "."
            String tokens[] = property.split("\\.");

            if (tokens.length != 2) {
                throw new IllegalArgumentException("Each list property must be in form of: <prefix>.<index>.<property name>");
            }

            final int index = Integer.valueOf(tokens[0]);
            final String propertyName = tokens[1];

            // New index
            if (lastIndex != index) {
                if (lastIndex != -1) {
                    loadedBeans.add(item);
                }
                lastIndex = index;

                item = targetType.newInstance();
            }

            // Set the property
            setProperty(item, propertyName, convertIfNecessary(propertyName, propertyValue));
        }

        // Add last item
        if (lastIndex != -1) {
            loadedBeans.add(item);
        }

        return loadedBeans;
    }

    private Set<String> prefixFilteredProperties() {
        Set<String> filteredProperties = new TreeSet<>();

        for (String propertyName: properties.stringPropertyNames()) {
            if (propertyName.startsWith(this.propertyPrefix)) {
                filteredProperties.add(propertyName);
            }
        }

        return filteredProperties;
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

    private static void setProperty(Object item, String propertyName, Object value) throws Exception {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item.getClass(), propertyName);
        descriptor.getWriteMethod().invoke(item, value);
    }
}
