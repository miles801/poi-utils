package com.michael.poi.exp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 导出引擎，读取指定模板，加载数据后，输出到新的文件
 * 1. 读取模板文件
 * 2. 加载数据
 * 3. 将数据应用到模板文件中
 * <pre>
 * <b style="color:red">模板格式定义</b>
 *  $ 表示基本类型数据,通常配置方式为：$cc，会直接从JsonObject中获取
 *  # 表示数组、集合，通常配置方式为：#cc.xx，会从JsonObject指定对象（数组）中获取指定的属性值 （序号 #cc.@index），直接使用数组#cc.@array
 *  @ 表示内置变量  @col 跨列   @row 跨行 @color颜色 (暂未实现）
 *  [] 表示写入图片  []中的内容为文件的访问路径
 *  </pre>
 *
 * @author Michael
 */
public class ExportEngine {
    private Logger logger = Logger.getLogger(ExportEngine.class);


    /**
     * @param outputStream   输出流
     * @param templateStream 模板输入流
     * @param data           数据对象
     */
    public void export(OutputStream outputStream, InputStream templateStream, JsonObject data) {

        try {
            // 产生临时文件
            File tmpFile = File.createTempFile("excel", "");
            IOUtils.copy(templateStream, new FileOutputStream(tmpFile));

            // 从临时文件中读取模板内容
            Workbook workbook = new XSSFWorkbook(new FileInputStream(tmpFile));

            BatchData batchData = new BatchData();
            batchData.setDataInjector(new SimpleDataInjector(data));
            // 将数据写入到文件中
            writeData(workbook, batchData);

            // 输出到目标流中
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void export(OutputStream outputStream, InputStream templateStream, BatchData batchData) {

        try {
            // 产生临时文件
            File tmpFile = File.createTempFile("excel", "");
            IOUtils.copy(templateStream, new FileOutputStream(tmpFile));

            // 从临时文件中读取模板内容
            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(tmpFile));
            } catch (POIXMLException e) {
                workbook = new HSSFWorkbook(new FileInputStream(tmpFile));
            }

            writeData(workbook, batchData);

            // 输出到目标流中
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeData(Workbook workbook, BatchData batchData) {
        Sheet sheet = workbook.getSheetAt(0);
        logger.info("准备导出数据...");
        long start = System.currentTimeMillis();
        logger.info("获取数据...");
        JsonObject data = batchData.getDataInjector().fetch(batchData.getStart(), batchData.getLimit());
        if (data == null) {
            logger.error("导出失败!没有获得到需要导出的数据!");
            return;
        }
        // 替换文本内容 && 获得数组所在行
        Integer rowIndex = null;
        String key = null;      // 集合的key
        Map<Integer, String> keyMap = new HashMap<Integer, String>(); // key为cell索引，value为对应的对象的key值
        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell != null) {
                    int cellType = cell.getCellType();
                    if (cellType == Cell.CELL_TYPE_STRING) {
                        String value = cell.getStringCellValue();
                        if (value == null || "".equals(value.trim())) {
                            continue;
                        }
                        char flag = value.charAt(0);
                        if (flag == '$') {
                            JsonElement o = data.get(value.substring(1));
                            if (o == null || o.isJsonNull()) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue(o.getAsString());
                            }
                        } else if (flag == '#') { // 数组
                            rowIndex = row.getRowNum();
                            if (value.matches("^#\\w+\\.(@)?\\w+$")) {
                                key = value.substring(1, value.lastIndexOf("."));
                                String valueKey = value.substring(value.lastIndexOf(".") + 1);
                                keyMap.put(cell.getColumnIndex(), valueKey);
                            }
                        } else if (value.matches("\\[\\w+\\]")) {
                            // 图片
                            cell.setCellValue("");  // 不管是否能够成功写入图片，都需要将当前单元格的模板内容给情况
                            String filePath = value.substring(1, value.length() - 1);
                            JsonElement o = data.get(filePath);
                            if (o != null && !o.isJsonNull()) {
                                File file = new File(o.getAsString());
                                if (file.exists()) {
                                    try {
                                        // 写入图片
                                        FileInputStream inputStream = new FileInputStream(file);
                                        int index = workbook.addPicture(IOUtils.toByteArray(inputStream), Workbook.PICTURE_TYPE_JPEG);
                                        inputStream.close();
                                        CreationHelper creationHelper = workbook.getCreationHelper();
                                        ClientAnchor clientAnchor = creationHelper.createClientAnchor();
                                        clientAnchor.setCol1(cell.getColumnIndex());
                                        clientAnchor.setRow1(cell.getRowIndex());
                                        Drawing drawing = sheet.createDrawingPatriarch();
                                        Picture picture = drawing.createPicture(clientAnchor, index);
                                        picture.resize();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (rowIndex == null) {
            return;
        }
        // 写入数据
        Row row = sheet.getRow(rowIndex);
        Integer total = batchData.getTotal();
        JsonElement o = data.get(key);
        // 如果集合数据为空，清除集合行中的数据
        if (o == null || o.getAsJsonArray() == null) {
            sheet.removeRow(row);   // remove方法只会删掉所有的cell
            return;
        }

        // 先创建出所有的行
        JsonArray arr = o.getAsJsonArray();
        if (total == null) {
            total = arr.size();
        }
        int limit = batchData.getLimit();
        int batch = 0;
        // 是否为合并行
        CellRangeAddress cra = null;
        int mergedNum = sheet.getNumMergedRegions();
        for (int i = 0; i < mergedNum; i++) {
            CellRangeAddress address = sheet.getMergedRegion(i);
            int rows = address.getFirstRow();
            if (rowIndex == rows) {
                cra = address;
                break;
            }
        }

        // 是否是内置的简单注入器（即不采用批次加载）
        boolean isSimpleDataInjector = batchData.getDataInjector() instanceof SimpleDataInjector;
        for (int i = 0; i < total; i++) {
            if (!isSimpleDataInjector && i != 0 && i % limit == 0) {
                logger.info("导出数据:动态加载批次数据(" + (i / limit) + "/" + (total / limit) + ")...");
                arr = batchData.getDataInjector().fetch(i, limit).getAsJsonArray(key);
                batch = 0;
            }
            Row newRow = sheet.createRow(rowIndex + i + 1);
            if (row.getRowStyle() != null) {
                newRow.setRowStyle(row.getRowStyle());
            }
            Row preRow = sheet.getRow(rowIndex + i);
            // 是否为合并行
            for (int tmp = preRow.getFirstCellNum(); tmp < preRow.getLastCellNum(); tmp++) {
                Cell cell = newRow.createCell(tmp);
                cell.setCellStyle(preRow.getCell(tmp).getCellStyle());
            }
            if (cra != null) {
                int firstCol = cra.getFirstColumn();
                int lastCol = cra.getLastColumn();
                sheet.addMergedRegion(new CellRangeAddress(newRow.getRowNum(), newRow.getRowNum(), firstCol, lastCol));
            }
            for (Map.Entry<Integer, String> entry : keyMap.entrySet()) {
                int cellIndex = entry.getKey();
                String keyValue = entry.getValue();
                Cell newCell = newRow.getCell(cellIndex);
                JsonElement foo = null;
                if ("@array".equals(keyValue)) {        // 数组
                    foo = arr.get(i);
                } else if ("@index".equals(keyValue)) { // 序号
                    foo = new JsonPrimitive(i + 1);
                } else {                                // 获取对象
                    foo = arr.get(i).getAsJsonObject().get(keyValue);
                }
                if (foo == null || foo.isJsonNull()) {
                    newCell.setCellValue("");
                } else {
                    newCell.setCellValue(foo.getAsString());
                }
            }
        }
        sheet.removeRow(row);
        sheet.shiftRows(rowIndex + 1, sheet.getLastRowNum(), -1);   // 删掉空行，删除起始行与结束行之间的空行，数量为-n
        logger.info("导出数据:成功!共计" + total + "条!耗时" + (System.currentTimeMillis() / start) / 1000 + "秒!");
    }

}
