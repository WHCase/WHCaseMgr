package com.tianyi.whcase.dao;

import java.util.List;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseLevel;
@MyBatisRepository
public interface CaseLevelMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseLevel record);

    int insertSelective(CaseLevel record);

    CaseLevel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseLevel record);

    int updateByPrimaryKey(CaseLevel record);

    List<CaseLevel> selectByCaseId(String caseId);
}