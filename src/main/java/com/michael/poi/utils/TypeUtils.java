package com.michael.poi.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Michael
 */
public class TypeUtils {

    /**
     * 将指定对象转换成指定类型的对象并返回
     *
     * @param cellValue  对象
     * @param fieldClass 目标类型
     * @return 新对象
     */
    public static Object convertValueType(Object cellValue, Class<?> fieldClass) {
        if (cellValue == null) {
            return null;
        }
        if (fieldClass == null) {
            throw new RuntimeException("目标类型不可为空!");
        }
        String value = cellValue.toString();
        if (fieldClass.isAssignableFrom(String.class)) {
            // 解决科学计数法的问题
            if (value.matches("\\d\\.\\d+E\\d+")) {
                return new BigDecimal(value).toPlainString();
            }
            return value;
        } else if (cellValue instanceof Number) {
            if (fieldClass.isAssignableFrom(Double.class)) {
                return Double.parseDouble(value);
            } else if (fieldClass.isAssignableFrom(Float.class)) {
                return Float.parseFloat(value);
            } else {
                // 去0操作
                String noDotValue = value.replaceAll("\\.+", "");
                if (fieldClass.isAssignableFrom(Integer.class)) {
                    return Integer.parseInt(noDotValue);
                } else if (fieldClass.isAssignableFrom(Long.class)) {
                    return Long.parseLong(noDotValue);
                } else if (fieldClass.isAssignableFrom(Short.class)) {
                    return Short.parseShort(noDotValue);
                }
            }
        } else if (fieldClass.isAssignableFrom(Boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (fieldClass.isAssignableFrom(Date.class)) {
            try {
                if (value.matches("\\d+")) {
                    return new Date(Long.parseLong(value));
                } else if (value.matches("yyyy-MM-dd")) {
                    return new SimpleDateFormat("yyyy-mm-dd").parse(value);
                } else if (value.matches("yyyy-MM-dd HH:mm:ss")) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                } else {
                    throw new RuntimeException("不支持的时间类型的值:" + value);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return cellValue;
    }
}
