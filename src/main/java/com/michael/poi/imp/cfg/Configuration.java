package com.michael.poi.imp.cfg;

import com.michael.poi.core.DTO;
import com.michael.poi.core.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael
 */
public class Configuration {
    // 文件来源
    private String path;

    // 要导入哪几个sheet的数据
    // 如果为空，则表示加载所有
    private List<Integer> sheets;

    // 从哪一行开始，默认为0
    private int startRow;

    // 要导入到哪一个类中
    private Class<? extends DTO> clazz;

    // 处理器类
    private Handler handler;

    // 各个类的映射
    private List<ColMapping> mappings;


    /**
     * 添加一个sheet
     *
     * @param index 索引
     */
    public void addSheet(int index) {
        if (sheets == null) {
            sheets = new ArrayList<Integer>();
        }
        if (!sheets.contains(index)) {
            sheets.add(index);
        }
    }

    /**
     * 添加一个列映射
     *
     * @param colMapping 映射对象
     */
    public void addColMapping(ColMapping colMapping) {
        if (mappings == null) {
            mappings = new ArrayList<ColMapping>();
        }
        mappings.add(colMapping);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Integer> getSheets() {
        return sheets;
    }

    public void setSheets(List<Integer> sheets) {
        this.sheets = sheets;
    }


    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public List<ColMapping> getMappings() {
        return mappings;
    }

    public void setMappings(List<ColMapping> mappings) {
        this.mappings = mappings;
    }

    public Class<? extends DTO> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends DTO> clazz) {
        this.clazz = clazz;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
