package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseOrgan;
@MyBatisRepository
public interface CaseOrganMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseOrgan record);

    int insertSelective(CaseOrgan record);

    CaseOrgan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseOrgan record);

    int updateByPrimaryKey(CaseOrgan record);
}