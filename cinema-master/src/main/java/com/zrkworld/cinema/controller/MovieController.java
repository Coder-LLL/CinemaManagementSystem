package com.zrkworld.cinema.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrkworld.cinema.pojo.*;
import com.zrkworld.cinema.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lzh
 */
@Api(value = "MovieController", tags = { "电影相关的访问接口" })
@CrossOrigin
@RestController
@Validated

public class MovieController {
    @Resource
    MovieService movieService;
    /**
     * //添加电影票数据，需要返回电影票全部数据，因为电影票id需要服务端生成
     * export const insertMovie = query=>{
     *     return request({
     *         url:'insertMovie',
     *         method:'post',
     *         params:query
     *     })
     * };
     */
    @ApiOperation(value = "添加电影")
    @ApiImplicitParams({
            @ApiImplicitParam(name="movie",value="电影实体类",required=true,paramType = "query",dataType="Movie"),
    })
    @RequestMapping("insertMovie")
    public CinemaResult insertMovie( Movie movie, BindingResult result){
        return movieService.insertMovie(movie);

    }

    /**
     *  //获取电影票数据，请求参数：pageIndex，pageSize，返回电影列表和总记录数
     * export const movieData = query =>{
     *     return request({
     *         url:'getMovie',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @ApiOperation(value = "获取电影票数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageIndex",value="当前页面索引",required=true,paramType = "query",dataType="String"),
            @ApiImplicitParam(name="pageSize",value="页面大小",required=true,paramType = "query",dataType="String")
    })
    @RequestMapping("getMovie")
    public CinemaResult getMovie(String pageIndex,String pageSize){
        //这里使用分页插件pagehelper
        PageResult pageResult = new PageResult();
        PageHelper.startPage(Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
        List lists = movieService.getMovieData();
        PageInfo<Card> pageInfo = new PageInfo<>(lists);
//        System.out.println(pageInfo);
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.ok(pageResult);
    }
    /**
     * //删除电影数据，请求参数：movieId
     * export const delMovie = query=>{
     *     return request({
     *         url:'delMovie',
     *         method:'get',
     *         params:query
     *     });
     * };

     */
    @ApiOperation(value = "删除电影数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="movieId",value="电影Id",required=true,paramType = "query",dataType="String"),
    })
    @RequestMapping("delMovie")
    public CinemaResult delMovie(String movieId){
        movieService.delMovie(movieId);
        return CinemaResult.ok();
    }
    /**
     *      * //修改电影数据，请求参数:movieId、name、price、time
     *      * export const modifyMovie = query=>{
     *      *     return request({
     *      *         url:'modifyMovie',
     *      *         method:'get',
     *      *         params:query
     *      *     });
     *      * };
     */
    @ApiOperation(value = "修改电影数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="movie",value="电影实体类",required=true,paramType = "query",dataType="Movie"),
    })
    @RequestMapping("modifyMovie")
    public CinemaResult modifyMovie(Movie movie){
        return movieService.modifyMovie(movie);
    }
}
/*
*
* //删除电影数据，请求参数：movieId
export const delMovie = query=>{
    return request({
        url:'delMovie',
        method:'get',
        params:query
    });
};
//修改电影数据，请求参数:movieId、name、price、time
export const modifyMovie = query=>{
    return request({
        url:'modifyMovie',
        method:'get',
        params:query
    });
};*/