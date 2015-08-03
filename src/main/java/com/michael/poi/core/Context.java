package com.michael.poi.core;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 在读取数据过程中当前的上下文信息
 *
 * @author Michael
 */
public class Context {

    /**
     * 当前工作簿
     */
    private Workbook workbook;
    /**
     * 当前正在读取的单元表
     */
    private Sheet sheet;
    /**
     * 当前正在读取的单元表的索引
     */
    private int sheetIndex;
    /**
     * 当前正在读取的行
     */
    private Row row;
    /**
     * 当前正在读取的行的索引
     */
    private int rowIndex;
    /**
     * 当前正在读取的列
     */
    private Cell cell;
    /**
     * 当前正在读取的列的索引
     */
    private int cellIndex;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(int cellIndex) {
        this.cellIndex = cellIndex;
    }
}
