package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseFeed;
@MyBatisRepository
public interface CaseFeedMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseFeed record);

    int insertSelective(CaseFeed record);

    CaseFeed selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseFeed record);

    int updateByPrimaryKey(CaseFeed record);
}