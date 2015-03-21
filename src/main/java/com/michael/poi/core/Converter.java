package com.michael.poi.core;

/**
 * 转换器接口
 * 用于将读取到的单元格的值转成希望的值
 *
 * @author Michael
 */
public interface Converter<T extends DTO> {

    /**
     * 将当前单元格中获取到的值进行转换后返回新的值
     * 这个新的值，将会被用于注解的属性上
     *
     * @param dto     当前的dto对象
     * @param value   当前单元格中的值
     * @param context 上下文对象，持有当前环境的各种参数
     * @return 转换后的值
     */
    Object execute(T dto, Object value, Context context);

}
