package com.michael.poi.core;

/**
 * 转换器接口
 * 用于将读取到的单元格的值转成希望的值或者额外设置DTO中的一些数据
 * 常用方式：
 * 1. 将读取到的值进行截取/拼接/大小写转换等转换成指定的值
 * 2. 将读取到的值与数据库进行关联使用，使用查询以用于获得指定的值，例如根据名称获得ID、根据编号获得名称、获得关联表的ID等....
 * 3. 设置DTO中的其他列的值
 *
 * @author Michael
 */
public interface Converter<T extends DTO> {

    /**
     * 将当前单元格中获取到的值进行转换后返回新的值，一般用于复杂的数据转换（例如根据名称获得id/根据编号获得名称等）
     * 这个新的值，将会被用于注解的属性上
     * 如果返回null，则使用读取到的值
     * <p/>
     * PS：如果需要获得当前环境的各种参数，参考RuntimeContext
     *
     * @param dto   读取到当前单元格时的dto对象
     * @param value 当前单元格中的值（进行类型转换后的值）
     * @return 转换后的值
     * @see RuntimeContext
     */
    Object execute(T dto, Object value);

}
