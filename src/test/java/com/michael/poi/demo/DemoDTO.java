package com.michael.poi.demo;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.FieldConvert;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

/**
 * @author Michael
 */
@ImportConfig(file = "", sheets = 0, startRow = 2)
public class DemoDTO implements DTO {

    @Col(index = 0)
    private Integer index;

    @Col(index = 1, required = true)
    private String name;

    @Col(index = 2, required = true)
    private String cardNo;

    @Col(index = 3)
    private String phone;

    @Col(index = 4)
    private Double salary;

    @Col(index = 5)
    @FieldConvert(convertorClass = BooleanConverter.class)
    private Boolean valid;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
