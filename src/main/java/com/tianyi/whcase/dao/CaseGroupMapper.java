package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseGroup;
@MyBatisRepository
public interface CaseGroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseGroup record);

    int insertSelective(CaseGroup record);

    CaseGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseGroup record);

    int updateByPrimaryKey(CaseGroup record);
}