package com.zrkworld.cinema.controller;

import com.zrkworld.cinema.pojo.CinemaResult;
import com.zrkworld.cinema.pojo.Manager;
import com.zrkworld.cinema.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lzh
 */
@Api(value = "ManagerController", tags = { "管理员相关的访问接口" })
@CrossOrigin
@RestController
public class ManagerController {
    @Resource
    ManagerService managerService;

    /**
     *  //获取管理员数据，请求参数managerId
     * export const managerData = query=>{
     *     return request({
     *         url:'getManagerData',
     *         method:'get',
     *         params:query
     *     });
     * };
     * @param managerId
     * @return
     */
    @ApiOperation(value = "获取管理员数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="managerId",value="管理员Id",required=true,paramType = "query",dataType="String")
    })
    @RequestMapping("getManagerData")
    public CinemaResult getManagerData(String managerId){
        return CinemaResult.ok(managerService.selectByManagerId(managerId));
    }



    /**
     *  //  管理员登陆，请求参数managerId和password
     * export const managerLogin = query=>{
     *     return request({
     *         url:'getManagerData',
     *         method:'post',
     *         params:query
     *     });
     * };
     * @param managerId
     * @return
     */
    @ApiOperation(value = "管理员登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name="managerId",value="管理员Id",required=true,paramType = "query",dataType="String"),
            @ApiImplicitParam(name="password",value="管理员密码",required=true,paramType = "query",dataType="String")
    })
    @RequestMapping("managerLogin")
    public CinemaResult managerLogin(String managerId, String password){
        if (managerService.managerLogin(managerId, password) == null) {
            return CinemaResult.build(500,"error");
        } else {
            return CinemaResult.ok();
        }
    }
}
