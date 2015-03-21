package com.michael.poi.annotation;

import java.lang.annotation.*;

/**
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = ElementType.FIELD)
public @interface FieldConvert {
    /**
     * 转化器
     */
    Class<? extends com.michael.poi.core.Converter> convertorClass();

}
