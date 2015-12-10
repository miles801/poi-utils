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
    private boolean required;

    // 是否读取上一行，当当前单元格内容为空时，考虑是否需要读取上一行的单元格的数据
    private boolean preRowIfNull;

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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isPreRowIfNull() {
        return preRowIfNull;
    }

    public void setPreRowIfNull(boolean preRowIfNull) {
        this.preRowIfNull = preRowIfNull;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
}
