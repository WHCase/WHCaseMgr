package com.tianyi.whcase.dao;

import java.util.List;
import java.util.Map;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.model.CaseCategory;
import com.tianyi.whcase.model.CaseUnit;
import com.tianyi.whcase.viewmodel.CaseVM;
@MyBatisRepository
public interface CaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(Case record);
 
    Case selectByPrimaryKey(String id);
    
    CaseVM selectVMByPrimaryKey(String id);
    
    List<CaseVM> selectByReceiveStatus(Map<String, Object> map);

    int updateByPrimaryKey(Case record);

	List<CaseCategory> selectCaseCategory();

	List<CaseUnit> selectCaseUnit();

	int selectVMCountByReceiveStatus(Integer receiveStatus);

	int countByMap(Map<String, Object> map);

	List<CaseVM> loaCaselistWithPage(Map<String, Object> map);

	int updateCaseReceiveStatusAndLevel(int receiveStatus, String level, String caseId);
	int updateCaseReceiveStatus(int receiveStatus, String caseId);

	CaseVM selectByCaseIdAndReceiveStatus(int receiveStatus, String string);

	List<CaseVM> selectDistributedCase(Integer receiveStatus);

	int selectCountDistributedCase(int i);

	List<CaseVM> selectUnFeedBackDistributedCase(Map<String, Object> map);

	int selectUnFeedBackVMCount();

	int selectCountCaseByReceiveStatus(int i);

	int selectCountCase();

	int selectCountCaseByCondition(Map<String, Object> map);

	int selectTotalCountByCondition(Map<String, Object> map);

	List<CaseVM> selectByorganId(Map<String, Object> map);

	List<CaseVM> selectFeedCaseByorganId(Map<String, Object> map);

	int selectCountByorganId(Map<String, Object> map);

	int selectCountFeedCaseByorganId(Map<String, Object> map);
}