package com.zrkworld.cinema.mapper;

import com.zrkworld.cinema.pojo.Record;

import java.util.List;

/**
 * @author lzh
 */
public interface RecordMapper {
    /**
     * 根据会员卡id删除消费记录
     * @param cardId
     */
    void deleteRecordByCardId(String cardId);

    /**
     * 新增消费记录
     * @param record
     */
    void insertRecord(Record record);

    /**
     * 根据会员id查询消费记录
     * @param memberId
     * @return
     */
    List<Record> selectRecordByMemberId(String memberId);

    /**
     * 查询电影数量
     * @return
     */
    Long selectMovieCount();


    /**
     * 所有消费总额
     * @return
     */
    Long selectConsumeCount();

    /**
     * 所有积分消耗的总额
     * @return
     */
    Long selectIntegralExchangeCount();

    /**
     * 所有充值的总额
     * @return
     */
    Long selectRechargeCount();

    /**
     * 当日所有人民币充值记录，即某一天日期以及收入
     * @return
     */
    List selectRecharge();

    /**
     * 根据开始和结束时间，查询消费额
     * @param startDate
     * @param endDate
     * @return
     */
    Integer selectConsumeCountByDate(String startDate, String endDate);

    /**
     * 根据开始和结束时间，查询充值额
     * @param startDate
     * @param endDate
     * @return
     */
    Integer selectrechargeCountByDate(String startDate, String endDate);

    /**
     * 根据小时和结束时间，查询积分兑换额度
     * @param startDate
     * @param endDate
     * @return
     */
    Integer selectintegralCountByDate(String startDate, String endDate);

}