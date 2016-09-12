package com.michael.poi.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael
 */
public class CellUtils {

    private static Set<String> dateFormats = new HashSet<String>();

    static {
        dateFormats.add("14");    // yyyy-MM-dd
        dateFormats.add("31");    // yyyy年m月d日
        dateFormats.add("57");    // yyyy年m月
        dateFormats.add("58");    // m月d日
        dateFormats.add("32");    // h时mm分
    }

    /**
     * 获得单元格的真实值类型，包括：
     * String、Double、Integer、Boolean、Date
     * 其中，如果是数值类型，且小数点超过2位或者小数第一位不为0，即（123.00或123.1）这样的才会被视为double，否则都将转为Integer
     * 不支持Float类型
     * 日期格式支持：YYYY/MM/DD  时间格式：HH:mm:ss
     *
     * @param cell 单元格
     * @return String、Double、Integer、Boolean、Date类型的值
     */
    public static Object getCellRealValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        Object cellValue = null;
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {           // 是否为日期
                cellValue = DateUtil.getJavaDate(cell.getNumericCellValue());
            } else if (cell.toString().matches("\\d+\\.0")) {   // 整形
                Double tmp = cell.getNumericCellValue();
                if (dateFormats.contains(cell.getCellStyle().getDataFormat() + "")) {  // 也是时间类型
                    cellValue = DateUtil.getJavaDate(tmp);
                } else {

                    DecimalFormat decimalFormat = new DecimalFormat("##");
                    cellValue = Integer.parseInt(decimalFormat.format(tmp));
                }
            } else {
                cellValue = cell.getNumericCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cellType == Cell.CELL_TYPE_BLANK) {
            cellValue = null;
        } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = cell.getBooleanCellValue();
        } else if (cellType == Cell.CELL_TYPE_FORMULA) {    // 公式，直接返回字符串类型的值
            cellValue = cell.getStringCellValue();
        } else {
            cellValue = cell.getStringCellValue();
        }
        return cellValue;
    }
}
