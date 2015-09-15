package com.tianyi.whcase.dao;

import java.util.List;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseOrgan;
@MyBatisRepository
public interface CaseOrganMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseOrgan record); 

    CaseOrgan selectByPrimaryKey(String id); 

    int updateByPrimaryKey(CaseOrgan record);

    List<CaseOrgan> selectByCaseIdAndOrganId(String caseId, int indexOf);
}