package com.zrkworld.cinema.service;

import com.zrkworld.cinema.pojo.CinemaResult;
import com.zrkworld.cinema.pojo.MovieTime;

import java.util.List;

public interface MovieTimeService {
    /**
     * 新增电影
     * @param movietime
     * @return
     */
    CinemaResult insertMovieTime(MovieTime movietime);

    /**
     * 获取所有电影信息
     * @return
     */
    List<MovieTime> getMovieTimeData();

    /**
     * 删除电影信息
     * @param id
     */
    void delMovieTime(String id);

    /**
     * 修改电影信息
     * @param movietime
     * @return
     */
    CinemaResult modifyMovieTime(MovieTime movietime);

}
