package com.zrkworld.cinema.pojo;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lzh
 */
@ToString
public class Record implements Serializable {
    private Integer id;
    /**
     * 与数据库的cardid映射
     */
    private String cardId;

    private Integer value;

    private String time;
    /**
     * 与数据库的spendtype映射
     */
    private Integer spendType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcardId() {
        return cardId;
    }

    public void setcardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getspendType() {
        return spendType;
    }

    public void setspendType(Integer spendType) {
        this.spendType = spendType;
    }
}