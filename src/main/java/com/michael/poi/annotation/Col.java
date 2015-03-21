package com.michael.poi.annotation;

import java.lang.annotation.*;

/**
 * 导入数据的详细配置
 *
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Col {
    /**
     * 所在列号
     */
    int index();

    /**
     * 是否必须
     */
    boolean required() default false;

}
