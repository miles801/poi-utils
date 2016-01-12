package com.michael.poi.exp;

import com.google.gson.JsonObject;

/**
 * 数据注入器
 *
 * @author Michael
 */
public interface DataInjector {

    /**
     * 获取要注入的数据对象
     *
     * @param start 开始索引
     * @param limit 查询的条数
     */
    JsonObject fetch(int start, int limit);

}
