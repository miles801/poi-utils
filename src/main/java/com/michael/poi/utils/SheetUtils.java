package com.michael.poi.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author Michael
 */
public class SheetUtils {
    /**
     * 拷贝指定单元格的“合并列”到目标单元格
     * 如果源单元格并没有使用合并，则放弃
     *
     * @param sheet  sheet表
     * @param source 源单元格
     * @param target 目标单元格
     */
    public static void copyCellCol(Sheet sheet, Cell source, Cell target) {
        int num = sheet.getNumMergedRegions();
        int rowIndex = source.getRowIndex();
        int colIndex = source.getColumnIndex();
        CellStyle cs = target.getCellStyle();
        for (int i = 0; i < num; i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.isInRange(rowIndex, colIndex)) {
                CellRangeAddress cra = cellRangeAddress.copy();
                cra.setFirstRow(target.getRowIndex());
                cra.setLastRow(target.getRowIndex());
                sheet.addMergedRegion(cra);

            }
        }
    }

    /**
     * 新增单元格，如果在此索引之前的单元格不存在，则创建
     *
     * @param row   单元格所属行
     * @param index 单元格索引
     * @return 新创建的单元格对象
     */
    public static Cell createCells(Row row, int index) {
        if (index == 0) {
            return row.createCell(index);
        }
        for (int i = 0; i < index; i++) {
            Cell c = row.getCell(i);
            if (c == null) {
                c = row.createCell(i);
            }
        }
        return row.createCell(index);
    }
}
