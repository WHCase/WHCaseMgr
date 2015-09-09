package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseSender;
@MyBatisRepository
public interface CaseSenderMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseSender record);

    int insertSelective(CaseSender record);

    CaseSender selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseSender record);

    int updateByPrimaryKey(CaseSender record);
}