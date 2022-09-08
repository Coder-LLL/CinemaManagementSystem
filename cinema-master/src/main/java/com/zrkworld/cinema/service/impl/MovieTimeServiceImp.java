package com.zrkworld.cinema.service.impl;

import com.zrkworld.cinema.mapper.MovieTimeMapper;
import com.zrkworld.cinema.pojo.CinemaResult;
import com.zrkworld.cinema.pojo.MovieTime;
import com.zrkworld.cinema.service.MovieTimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieTimeServiceImp implements MovieTimeService {
    @Resource
    MovieTimeMapper movieTimeMapper;

    @Override
    public CinemaResult insertMovieTime(MovieTime movietime) {
        movieTimeMapper.insertMovieTime(movietime);
        return CinemaResult.ok();
    }

    @Override
    public List<MovieTime> getMovieTimeData() {
        return movieTimeMapper.selectAllMovieTime();
    }

    @Override
    public void delMovieTime(String id) {
        movieTimeMapper.deleteMovieTimeById(id);
    }

    @Override
    public CinemaResult modifyMovieTime(MovieTime movietime) {
        movieTimeMapper.updateMovieTimeById(movietime);
        return CinemaResult.ok();
    }
}
