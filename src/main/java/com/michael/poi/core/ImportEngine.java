package com.michael.poi.core;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.FieldConvert;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.utils.ReflectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 导入引擎
 *
 * @author Michael
 */
public class ImportEngine {
    private Class<?> clazz;
    private Handler handler;

    public ImportEngine(Class<? extends DTO> clazz, Handler handler) {
        this.clazz = clazz;
        this.handler = handler;
    }

    public void execute() {
        ImportConfig config = clazz.getAnnotation(ImportConfig.class);
        // 获得文件路径
        String filePath = config.file();
        // 获得工作表
        int[] sheets = config.sheets();
        // 获得起始行
        int startRow = config.startRow();


        // 获得所有具有ColIndex的属性
        Context context = new Context();
        List<Field> fields = ReflectUtils.searchAnnotationFields(clazz, Col.class);

        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("没有找到文件：" + filePath);
        }
        context.setWorkbook(workbook);
        for (int i = 0; i < sheets.length; i++) {
            Sheet sheet = workbook.getSheetAt(sheets[i]);
            int rows = sheet.getLastRowNum() + 1;
            context.setSheet(sheet);
            context.setSheetIndex(i);
            for (int j = startRow; j < rows; j++) {
                // 读取具体的单元格
                Row row = sheet.getRow(j);
                context.setRow(row);
                context.setRowIndex(j);
                DTO instance = null;
                try {
                    instance = (DTO) clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                for (Field field : fields) {
                    Col col = field.getAnnotation(Col.class);
                    Cell cell = row.getCell(col.index());
                    if (cell == null) {
                        if (col.required()) {
                            throw new RuntimeException("单元格内容缺失!工作表[" + sheet.getSheetName() + "],第[" + (j + 1) + "]行[" + (col.index() + 1) + "]列!");
                        }
                        continue;
                    }
                    Object cellValue = null;
                    int cellType = cell.getCellType();
                    if (cellType == Cell.CELL_TYPE_NUMERIC) {
                        cellValue = cell.getNumericCellValue();
                    } else if (cellType == Cell.CELL_TYPE_STRING) {
                        cellValue = cell.getStringCellValue();
                    } else if (cellType == Cell.CELL_TYPE_BLANK) {
                        cellValue = "";
                    } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
                        cellValue = cell.getBooleanCellValue();
                    } else {
                        throw new RuntimeException("不支持的格式-->" + cellType + "!");
                    }
                    context.setCell(cell);
                    context.setCellIndex(col.index());
                    // 包含转换器
                    field.setAccessible(true);
                    FieldConvert fieldConvert = field.getAnnotation(FieldConvert.class);
                    try {
                        if (fieldConvert != null) {
                            Class<? extends Converter> converter = fieldConvert.convertorClass();
                            Object value = converter.newInstance().execute(instance, cellValue, context);
                            field.set(instance, value);
                        } else {
                            field.set(instance, cellValue);
                        }
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                // 到这里instance就包装完成了
                handler.execute(instance);
            }
        }
    }
}
