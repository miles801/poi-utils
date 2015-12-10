package com.michael.poi.converter;

import com.michael.poi.core.Context;
import com.michael.poi.core.Converter;
import com.michael.poi.core.DTO;
import com.michael.poi.core.RuntimeContext;
import com.michael.poi.utils.CellUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 用于读取合并行数据
 * 即当前单元格的数据如果为空，则读取之前行的相同列的单元格的数据
 *
 * @author Michael
 */
public class PreRowConverter implements Converter {

    private static PreRowConverter converter = new PreRowConverter();

    public static PreRowConverter getInstance() {
        return converter;
    }

    @Override
    public Object execute(DTO dto, Object value) {
        Context context = RuntimeContext.get();
        if (value == null || "".equals(value.toString()) || "null".equals(value.toString())) {
            int index = context.getRowIndex();
            int cellIndex = context.getCellIndex();
            Sheet sheet = context.getSheet();
            while (index > context.getStartRow()) {
                index--;
                Row row = sheet.getRow(index);
                Cell cell = row.getCell(cellIndex);
                if (cell != null) {
                    Object cellValue = CellUtils.getCellRealValue(cell);
                    if (cellValue != null) {
                        return cellValue;
                    }
                }
            }
        }
        return null;
    }
}
