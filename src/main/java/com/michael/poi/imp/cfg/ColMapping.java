package com.michael.poi.imp.cfg;

import com.michael.poi.core.Converter;

/**
 * 列映射
 *
 * @author Michael
 */
public class ColMapping {
    // 单元格列下标
    private Integer index;

    // 列名称，对应的字段名称，这个属性必须直接存在于所在类中，不会去查找父类中的属性
    private String colName;

    // 是否必须，如果设置为true，那么在读取的时候发现该单元格为空则会抛出异常
    private Boolean required;

    // 转化器，可以对该列进行转换
    private Converter converter;


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
}
