package com.michael.poi.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael
 */
public class ReflectUtils {

    /**
     * 获得具有指定类中具有指定Annotation的属性集合
     *
     * @param clazz           类
     * @param annotationClass 注解类型
     */
    public static List<Field> searchAnnotationFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Field fields[] = clazz.getDeclaredFields();
        List<Field> data = new ArrayList<Field>();
        for (Field field : fields) {
            if (field.getAnnotation(annotationClass) != null) {
                data.add(field);
            }
        }
        return data;
    }
}
