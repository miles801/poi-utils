package com.michael.poi.exp;


/**
 * 批次数据
 *
 * @author Michael
 */
public class BatchData {
    private Integer total;                      // 一共多少数据
    private int start = 0;                      // 从多少条开始
    private int limit = 50;                 // 每次读取多少条数据
    private boolean isBatch = false;        // 是否是批次数据
    private DataInjector dataInjector;      // 数据注入器,每次注入一批数据


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public DataInjector getDataInjector() {
        return dataInjector;
    }

    public void setDataInjector(DataInjector dataInjector) {
        this.dataInjector = dataInjector;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public void setBatch(boolean batch) {
        isBatch = batch;
    }
}
