package com.michael.poi.core;

/**
 * 每读取一行数据，组装成DTO对象后要执行的操作
 * 一般用于将数据进行保存
 *
 * @author Michael
 */
public interface Handler<T extends DTO> {

    /**
     * 如需获取当前环境的数据，可以从RuntimeContext中获取
     *
     * @param dto 一行读取完后组装好的一个数据对象
     * @see RuntimeContext
     */
    public void execute(T dto);

}
