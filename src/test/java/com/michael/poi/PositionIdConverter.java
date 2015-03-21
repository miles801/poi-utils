package com.michael.poi;

import com.michael.poi.core.Context;
import com.michael.poi.core.Converter;

/**
 * 根据岗位名称获得岗位id
 *
 * @author Michael
 */
public class PositionIdConverter implements Converter<FlowNodeDTO> {
    public Object execute(FlowNodeDTO current, Object value, Context context) {
        // value是节点名称
        return value;
    }

}
