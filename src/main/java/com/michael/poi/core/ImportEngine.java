package com.michael.poi.core;

import com.michael.poi.exceptions.ImportConfigException;
import com.michael.poi.imp.cfg.ColMapping;
import com.michael.poi.imp.cfg.Configuration;
import com.michael.poi.utils.CellUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入引擎
 *
 * @author Michael
 */
public class ImportEngine {

    private Configuration configuration;


    /**
     * 使用配置对象初始化引擎
     *
     * @param configuration 配置对象
     */
    public ImportEngine(Configuration configuration) {
        this.configuration = configuration;
    }

    public void execute() {
        // 获得文件路径
        String filePath = configuration.getPath();
        // 获得工作表
        List<Integer> sheets = configuration.getSheets();

        // 获得起始行
        int startRow = configuration.getStartRow();

        Class<? extends DTO> targetClass = configuration.getClazz();

        // 获得映射
        List<ColMapping> mappings = configuration.getMappings();

        Handler handler = configuration.getHandler();
        if (handler == null) {
            throw new ImportConfigException("没有注册处理器!");
        }

        // 上下文对象
        Context context = new Context();

        Workbook workbook = getWorkbook(filePath);
        int sheetCount = workbook.getNumberOfSheets();
        context.setWorkbook(workbook);
        sheets = initSheetList(sheets, sheetCount);
        for (Integer i : sheets) {
            Sheet sheet = workbook.getSheetAt(i);
            context.setSheet(sheet);
            context.setSheetIndex(i);

            int lastRowNum = sheet.getLastRowNum() + 1;
            if (lastRowNum == 1) {  // 空sheet
                continue;
            }
            if (lastRowNum < startRow) {
                throw new ImportConfigException("起始行超出最大行的索引!");
            }

            for (int j = startRow; j < lastRowNum; j++) {
                // 读取具体的单元格
                Row row = sheet.getRow(j);
                context.setRow(row);
                context.setRowIndex(j);
                DTO targetInstance = null;
                try {
                    targetInstance = targetClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (targetInstance == null) {
                    throw new ImportConfigException("初始化目标类[" + targetClass.getName() + "]失败!");
                }

                for (ColMapping colMapping : mappings) {
                    int index = colMapping.getIndex();
                    Cell cell = row.getCell(index);
                    context.setCell(cell);
                    context.setCellIndex(index);

                    if (cell == null) {
                        if (colMapping.getRequired() != null && colMapping.getRequired()) {
                            throw new RuntimeException("单元格内容缺失!工作表[" + sheet.getSheetName() + "],第[" + (j + 1) + "]行[" + (index + 1) + "]列!");
                        }
                        continue;
                    }
                    Object cellValue = CellUtils.getCellRealValue(cell);
                    String fieldName = colMapping.getColName();
                    try {
                        Field field = targetClass.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        // TODO 是否进行转换
                        field.set(targetInstance, cellValue);
                    } catch (NoSuchFieldException e) {
                        throw new ImportConfigException(String.format("类[%s]中不存在属性[%s]", targetClass.getName(), fieldName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                handler.execute(targetInstance);

            }
        }
    }

    private Object getCellValue(Cell cell) {
        Object cellValue = null;
        int cellType = cell.getCellType();
        if (cellType == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {   // 是否为日期
                cellValue = DateUtil.getJavaDate(cell.getNumericCellValue());
            } else {
                cellValue = cell.getNumericCellValue();
            }
        } else if (cellType == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cellType == Cell.CELL_TYPE_BLANK) {
            cellValue = "";
        } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = cell.getBooleanCellValue();
        } else {
            throw new RuntimeException(String.format("不支持的单元格类型[%d]!发生在%d行%d列", cellType, cell.getRowIndex(), cell.getColumnIndex()));
        }
        return cellValue;
    }


    private List<Integer> initSheetList(List<Integer> sheets, int sheetCount) {
        if (sheets == null) {
            sheets = new ArrayList<Integer>();
            for (int i = 0; i < sheetCount; i++) {
                sheets.add(i);
            }
        }
        return sheets;
    }

    /**
     * 根据文件路径获得Workbook对象
     *
     * @param filePath 文件路径
     */
    private Workbook getWorkbook(String filePath) {
        Workbook workbook = null;
        try {
            if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(new FileInputStream(filePath));
            } else if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(new FileInputStream(filePath));
            } else {
                throw new ImportConfigException("不支持的文件格式!" + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
}
