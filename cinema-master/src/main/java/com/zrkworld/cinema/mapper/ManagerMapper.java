package com.zrkworld.cinema.mapper;

import com.zrkworld.cinema.pojo.Manager;


/**
 * @author lzh
 */
public interface ManagerMapper {
    /**
     * 接收managerId，返回Manager
     *
     * @param managerId
     * @return Manager
     */
    Manager selectByManagerId(String managerId);

    /**
     *
     * @param managerId
     * @param password
     * @return
     */
    Manager managerLogin(String managerId,String password);

    void updateManagerTime(String managerId);

}