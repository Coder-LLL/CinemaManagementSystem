package com.zrkworld.cinema.pojo;

import lombok.ToString;

@ToString
public class MonthIncome {
    private Integer value;
    private Integer day;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
