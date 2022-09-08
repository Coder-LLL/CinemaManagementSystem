package com.zrkworld.cinema.service.impl;

import com.zrkworld.cinema.mapper.RecordMapper;
import com.zrkworld.cinema.pojo.Record;
import com.zrkworld.cinema.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lzh
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Resource
    RecordMapper recordMapper;

    @Override
    public List<Record> selectRecordByMemberId(String memberId) {
        return recordMapper.selectRecordByMemberId(memberId);
    }

    @Override
    public void deleteRecordByCardId(String cardId) {
        recordMapper.deleteRecordByCardId(cardId);
    }

    @Override
    public void insertRecord(Record record) {
        recordMapper.insertRecord(record);
    }

    @Override
    public Long getMovieNum() {
        return recordMapper.selectMovieCount();
    }
    @Override
    public Long getConsumeCount() {
        return recordMapper.selectConsumeCount();
    }

    @Override
    public List getRecharge() {
        return recordMapper.selectRecharge();
    }

    @Override
    public Long getIntegralExchangeCount() {
        return recordMapper.selectIntegralExchangeCount();
    }

    @Override
    public Long getRechargeCount() {
        return recordMapper.selectRechargeCount();
    }


}
