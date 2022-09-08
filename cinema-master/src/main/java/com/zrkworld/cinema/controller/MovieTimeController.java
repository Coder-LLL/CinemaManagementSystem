package com.zrkworld.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrkworld.cinema.pojo.*;

import com.zrkworld.cinema.service.MovieTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "MovieTimeController", tags = { "电影场次相关的访问接口" })
@CrossOrigin
@RestController
public class MovieTimeController {
    @Resource
    MovieTimeService movieTimeService;


    /**
     * //添加电影场次数据，需要返回电影票全部数据，因为电影票id需要服务端生成
     * export const insertMovie = query=>{
     *     return request({
     *         url:'insertMovieTime',
     *         method:'post',
     *         params:query
     *     })
     * };
     */
    @ApiOperation(value = "添加电影场次")
    @ApiImplicitParams({
            @ApiImplicitParam(name="movieTime",value="电影时间实体类",required=true,paramType = "query",dataType="MovieTime"),
    })
    @RequestMapping("insertMovieTime")
    public CinemaResult insertMovieTime(MovieTime movieTime){
        return movieTimeService.insertMovieTime(movieTime);
    }

    @ApiOperation(value = "获取所有电影场次数据")
    @RequestMapping("getAllMovieTime")
    public CinemaResult getAllMovieTime(){
        List lists = movieTimeService.getMovieTimeData();
        return CinemaResult.ok(lists);
    }
    /**
     *  //获取电影场次数据，请求参数：pageIndex，pageSize，返回电影列表和总记录数
     * export const movieData = query =>{
     *     return request({
     *         url:'getMovieTime',
     *         method:'get',
     *         params:query
     *     });
     * };
     */
    @ApiOperation(value = "获取所有电影场次数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageIndex",value="当前页面下标",required=true,paramType = "query",dataType="String"),
            @ApiImplicitParam(name="pageSize",value="页面大小",required=true,paramType = "query",dataType="String"),
    })
    @RequestMapping("getMovieTime")
    public CinemaResult getMovieTime(String pageIndex,String pageSize){
        //这里使用分页插件pagehelper
        PageResult pageResult = new PageResult();
        PageHelper.startPage(Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
        List lists = movieTimeService.getMovieTimeData();
        PageInfo<Card> pageInfo = new PageInfo<>(lists);
//        System.out.println(pageInfo);
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.ok(pageResult);
    }
    /**
     * //删除电影场次数据，请求参数：movieId
     * export const delMovie = query=>{
     *     return request({
     *         url:'delMovieTime',
     *         method:'get',
     *         params:query
     *     });
     * };

     */
    @ApiOperation(value = "删除电影数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="电影Id",required=true,paramType = "query",dataType="String"),
    })
    @RequestMapping("delMovieTime")
    public CinemaResult delMovieTime(String id){
        movieTimeService.delMovieTime(id);
        return CinemaResult.ok();
    }

    /**
     *      * //修改电影场次数据，请求参数:movieId、name、price、time
     *      * export const modifyMovie = query=>{
     *      *     return request({
     *      *         url:'modifyMovieTime',
     *      *         method:'get',
     *      *         params:query
     *      *     });
     *      * };
     */
    @ApiOperation(value = "修改电影场次数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="movieTime",value="电影Id",required=true,paramType = "query",dataType="MovieTime"),
    })
    @RequestMapping("modifyMovieTime")
    public CinemaResult modifyMovieTime(MovieTime movieTime){
        return movieTimeService.modifyMovieTime(movieTime);
    }
}
