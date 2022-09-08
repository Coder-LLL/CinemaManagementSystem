package com.zrkworld.cinema.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrkworld.cinema.pojo.CinemaResult;
import com.zrkworld.cinema.pojo.PageResult;
import com.zrkworld.cinema.pojo.Record;
import com.zrkworld.cinema.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.eclipse.jdt.internal.compiler.codegen.CachedIndexEntry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lzh
 */

@Api(value = "RecordController", tags = { "交易记录相关的访问接口" })
@CrossOrigin
@RestController
public class RecordController {
    @Resource
    RecordService recordService;

    /**
     * //交易记录查询，请求参数会员id，pageIndex,pageSize
     * export const queryRecord = query=>{
     * return request({
     * url:"queryRecord",
     * method:'post',
     * params:query
     * })
     * }
     */
    @ApiOperation(value = "交易记录查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberId",value="用户Id",required=true,paramType = "query",dataType="String"),
            @ApiImplicitParam(name="pageIndex",value="当前页面索引",required=true,paramType = "query",dataType="String"),
            @ApiImplicitParam(name="pageSize",value="页面大小",required=true,paramType = "query",dataType="String")
    })
    @RequestMapping("queryRecord")
    public CinemaResult queryRecord(String memberId, String pageIndex, String pageSize) {
        PageResult pageResult = new PageResult();
        PageHelper.startPage(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
        List<Record> records = recordService.selectRecordByMemberId(memberId);
        PageInfo<Record> pageInfo = new PageInfo<>(records);
        System.out.println(pageInfo);
        pageResult.setList(pageInfo.getList());
        pageResult.setPageTotal(pageInfo.getTotal());
        return CinemaResult.ok(pageResult);
    }


    /**
     * // 所有人民币充值记录查询
     * export const queryRecord = query=>{
     * return request({
     * url:"rechargeRecord",
     * method:'post',
     * })
     * }
     */
    @ApiOperation(value = "所有人民币充值记录查询")
    @RequestMapping("rechargeRecord")
    public CinemaResult rechargeRecord() {
        PageResult pageResult = new PageResult();
        List<Record> records = recordService.getRecharge();
        return CinemaResult.ok(records);
    }
}
