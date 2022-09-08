package com.zrkworld.cinema.service;

import com.zrkworld.cinema.pojo.Manager;

import java.util.List;



/**
 * @author lzh
 */
public interface ManagerService {
    /**
     * 接收managerId，返回Manager数据
     * @param managerId
     * @return Manager
     */
   Manager selectByManagerId(String managerId);

    /**
     * 管理员登录验证
     * @param managerId
     * @param password
     * @return
     */
   Manager managerLogin(String managerId,String password);
}
