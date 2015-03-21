package com.michael.poi.core;

/**
 * 每读取一行数据，组装成DTO对象后要执行的操作
 *
 * @author Michael
 */
public interface Handler<T extends DTO> {

    public void execute(T dto);

}
