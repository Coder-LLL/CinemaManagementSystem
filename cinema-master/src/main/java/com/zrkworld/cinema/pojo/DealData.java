package com.zrkworld.cinema.pojo;

import lombok.ToString;

import java.io.Serializable;

/**
 * @author lzh
 * 封装交易额总量
 */
@ToString
public class DealData implements Serializable {
    private static final long serialVersionUID = 1L;
    Long consume;
    Long recharge;
    Long integral;

    @Override
    public String toString() {
        return "DealData{" +
                "consume=" + consume +
                ", recharge=" + recharge +
                ", integral=" + integral +
                '}';
    }

    public Long getConsume() {
        return consume;
    }

    public void setConsume(Long consume) {
        this.consume = consume;
    }

    public Long getRecharge() {
        return recharge;
    }

    public void setRecharge(Long recharge) {
        this.recharge = recharge;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }
}
