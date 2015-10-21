package com.tianyi.whcase.dao;

import java.util.List;
import java.util.Map;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.CaseFeed;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.viewmodel.CaseFeedVM;
@MyBatisRepository
public interface CaseFeedMapper {
    int deleteByPrimaryKey(String id);

    int insert(CaseFeed record);

    int insertSelective(CaseFeed record);

    CaseFeed selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CaseFeed record);

    int updateByPrimaryKey(CaseFeed record);

	CaseFeed selectByCaseId(String id);

	CaseFeed selectByCondition(Map<String, Object> map);

	List<Organ> getFeedBackOrganById(String id);

	List<CaseFeedVM> getCaseBackMainInfo(Map<String, Object> map);
}