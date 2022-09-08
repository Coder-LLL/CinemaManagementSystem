package com.zrkworld.cinema.mapper;


import com.zrkworld.cinema.pojo.MovieTime;

import java.util.List;

public interface MovieTimeMapper {
    /**
     * 需求：使用MyBatis往MySQL数据库中插入一条记录后，需要返回该条记录的自增主键值。
     * 方法：在定义xml映射器时设置属性useGeneratedKeys值为true，并分别指定属性keyProperty和keyColumn为对应的数据库记录主键字段与Java对象的主键属性。
     * 由于需要返回的还有日期，所以这里就不返回主键了
     * @param movietime
     */
    void insertMovieTime(MovieTime movietime);

    /**
     * 取出来的时间要进行date_format，否则会取出来.0的时间数据
     * @param
     * @return
     */
    List<MovieTime> selectAllMovieTime();

    /**
     * 根据movieId删除电影信息
     * @param id
     */
    void deleteMovieTimeById(String id);

    /**
     * 根据movieId更新电影信息
     * @param movietime
     */
    void updateMovieTimeById(MovieTime movietime);
}
