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
    
    List<CaseVM> selectByReceiveStatus(Integer receiveStatus);

    int updateByPrimaryKey(Case record);

	List<CaseCategory> selectCaseCategory();

	List<CaseUnit> selectCaseUnit();

	int selectVMCountByReceiveStatus(Integer receiveStatus);

	int countByMap(Map<String, Object> map);

	List<CaseVM> loaCaselistWithPage(Map<String, Object> map);

	int updateCaseReceiveStatus(int receiveStatus, String caseId);
}