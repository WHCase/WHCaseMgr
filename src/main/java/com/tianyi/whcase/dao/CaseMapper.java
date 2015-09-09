package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.Case;
@MyBatisRepository
public interface CaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(Case record);

    int insertSelective(Case record);

    Case selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Case record);

    int updateByPrimaryKey(Case record);
}