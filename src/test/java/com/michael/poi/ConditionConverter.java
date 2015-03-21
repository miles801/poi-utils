package com.michael.poi;


import com.michael.poi.core.Context;
import com.michael.poi.core.Converter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 转换器：
 * 读取当前单元格之后的所有单元格，如果存在值，则设置成条件
 *
 * @author Michael
 */
public class ConditionConverter implements Converter<FlowNodeDTO> {
    @Override
    public Object execute(FlowNodeDTO dto, Object value, Context context) {
        Row row = context.getRow();
        String condition = getNextCondition(row, context.getCellIndex());
        if (condition != null && !"".equals(condition.trim())) {
            condition = condition.substring(2);
//            dto.setCondition(condition);
        }
        return condition;
    }

    public String getNextCondition(Row row, int cellIndex) {
        if (cellIndex > row.getLastCellNum()) {
            return "";
        }
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return getNextCondition(row, ++cellIndex);
        }
        int cellType = cell.getCellType();
        String cellValue = null;
        if (cellType == Cell.CELL_TYPE_NUMERIC) {
            cellValue = cell.getNumericCellValue() + "";
        } else if (cellType == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cellType == Cell.CELL_TYPE_BLANK) {
            return getNextCondition(row, ++cellIndex);
        } else {
            throw new RuntimeException("不支持的格式-->" + cellType + "!");
        }
        if (cellValue == null || "".equals(cellValue.trim())) {// 如果当前单元格为空，则查找下一个单元格
            return getNextCondition(row, ++cellIndex);
        }
        String condition = "";
        // 获得首行的当前列的值
        String conditionName = row.getSheet().getRow(0).getCell(cellIndex).getStringCellValue();
        condition += "&&@" + conditionName + "='" + cellValue + "'";
        if (cellIndex < row.getLastCellNum()) {
            return condition + getNextCondition(row, ++cellIndex);
        }
        return condition;
    }

}
