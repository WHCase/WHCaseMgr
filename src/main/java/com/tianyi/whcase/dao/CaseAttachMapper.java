package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseAttach;
@MyBatisRepository
public interface CaseAttachMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseAttach record);

    int insertSelective(CaseAttach record);

    CaseAttach selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseAttach record);

    int updateByPrimaryKey(CaseAttach record);
}