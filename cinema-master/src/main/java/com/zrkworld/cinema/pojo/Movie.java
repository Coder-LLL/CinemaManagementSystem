package com.zrkworld.cinema.pojo;

import lombok.ToString;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author lzh
 */
@ToString
public class Movie implements Serializable {
    /**
     * 与数据库的movieid映射
     */

    private Integer movieId;

    private String name;

    private Integer price;

    private Integer integral;

    private String time;

    private static final long serialVersionUID = 1L;

    public Integer getmovieId() {
        return movieId;
    }

    public void setmovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", integral=" + integral +
                ", time='" + time + '\'' +
                '}';
    }
}