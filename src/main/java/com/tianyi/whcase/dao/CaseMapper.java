package com.tianyi.whcase.dao;

import java.util.List;

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
}