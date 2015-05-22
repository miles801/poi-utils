package com.michael.poi.export;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.michael.poi.utils.SheetUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * // 读取模板文件
 * // 加载数据
 * // 将数据应用到模板文件中
 *
 * @author Michael
 */
public class ExportEngine {
    // 模板格式定义
    // $ 表示元素
    // # 表示数组
    // @表示内置变量  @col 跨列   @row 跨行 @color颜色


    // 导出
    public void export(OutputStream outputStream, InputStream templateStream, JsonObject data) {

        try {
            // 产生临时文件
            File tmpFile = File.createTempFile("excel", "");
            IOUtils.copy(templateStream, new FileOutputStream(tmpFile));

            // 从临时文件中读取模板内容
            Workbook workbook = new XSSFWorkbook(new FileInputStream(tmpFile));

            // 将数据写入到文件中
            writeData(workbook, data);

            // 输出到目标流中
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeData(Workbook workbook, JsonObject data) {
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            Set<Integer> cells = new HashSet<Integer>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell != null) {
                    int cellType = cell.getCellType();
                    if (cellType == Cell.CELL_TYPE_STRING) {
                        String value = cell.getStringCellValue();
                        char flag = value.charAt(0);
                        if (flag == '$') {
                            JsonElement o = data.get(value.substring(1));
                            if (o == null) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue(o.getAsString());
                            }
                        } else if (flag == '#') { // 数组
                            // 对象数组
                            String key = null;
                            String valueKey = null;
                            boolean isNormalArray = false;
                            if (value.matches("#\\w+\\.\\w+")) {
                                key = value.substring(1, value.lastIndexOf("."));
                                valueKey = value.substring(value.lastIndexOf(".") + 1);
                            } else if (value.matches("#\\w+[\\d+]")) {
                                isNormalArray = true;
                                key = value.substring(1, value.indexOf("[") + 1);
                                valueKey = value.substring(value.lastIndexOf("[") + 1, value.length() - 1);
                            } else {
                                System.out.println("不合法的值：" + value);
                                continue;
                            }
                            JsonElement o = data.get(key);
                            if (o == null || o.getAsJsonArray() == null) {
                                cell.setCellValue("");
                                continue;
                            }
                            JsonArray arr = o.getAsJsonArray();

                            int total = arr.size();
                            // 先创建出所有的行
                            int rowIndex = cell.getRowIndex();
                            if (!cells.contains(rowIndex)) {
                                cells.add(rowIndex);
                                for (int i = 1; i < total + 1; i++) {
                                    sheet.createRow(rowIndex + i).setRowStyle(row.getRowStyle());
                                }
                            }
                            int index = 0;
                            for (JsonElement elm : arr) {
                                Row newRow = sheet.getRow(rowIndex + index);
                                Cell newCell = newRow.getCell(cell.getColumnIndex());
                                if (newCell == null) {
                                    newCell = SheetUtils.createCells(newRow, cell.getColumnIndex());
                                    SheetUtils.copyCellCol(sheet, cell, newCell);
                                    newCell.setCellStyle(cell.getCellStyle());
                                }

                                if (isNormalArray) {
                                    if (index == Integer.parseInt(valueKey)) {
                                        newCell.setCellValue(elm.getAsString());
                                    }
                                } else {
                                    JsonElement objValue = elm.getAsJsonObject().get(valueKey);
                                    if (objValue != null) {
                                        newCell.setCellValue(objValue.getAsString());
                                    } else {
                                        newCell.setCellValue("");
                                    }
                                }
                                index++;
                            }
                        }
                    }
                }
            }
        }
    }

}
