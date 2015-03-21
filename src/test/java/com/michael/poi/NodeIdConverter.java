package com.michael.poi;

import com.michael.poi.core.Context;
import com.michael.poi.core.Converter;

/**
 * 根据节点的名称、流程的名称查询节点的id
 *
 * @author Michael
 */
public class NodeIdConverter implements Converter<FlowNodeDTO> {

    @Override
    public Object execute(FlowNodeDTO dto, Object value, Context context) {
        // value的值就是当前单元格传递过来的值

        // 可以在这里查询、转换、设置其他值等

        // 返回的值，就是最终要设置给DTO当前属性的值
        return value;
    }
}
