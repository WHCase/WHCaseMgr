package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseAttachItem;
@MyBatisRepository
public interface CaseAttachItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseAttachItem record);

    int insertSelective(CaseAttachItem record);

    CaseAttachItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseAttachItem record);

    int updateByPrimaryKey(CaseAttachItem record);
}