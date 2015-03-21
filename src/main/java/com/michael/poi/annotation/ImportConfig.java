package com.michael.poi.annotation;

import java.lang.annotation.*;

/**
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = ElementType.TYPE)
public @interface ImportConfig {
    /**
     * 要导入数据的文件的名称
     */
    String file();

    /**
     * 要导入哪些工作表（下标从0开始）
     */
    int[] sheets() default 0;

    /**
     * 从哪一行开始导入
     */
    int startRow() default 0;
}
