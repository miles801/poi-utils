package com.michael.poi.demo;

import com.michael.poi.core.Converter;

/**
 * @author Michael
 */
public class BooleanConverter implements Converter<DemoDTO> {
    @Override
    public Object execute(DemoDTO dto, Object value) {
        if (value != null && value instanceof String) {
            String strValue = (String) value;
            if ("是".equals(strValue)) {
                return true;
            } else if ("否".equals(strValue)) {
                return false;
            }
            return null;
        }
        return null;
    }
}
