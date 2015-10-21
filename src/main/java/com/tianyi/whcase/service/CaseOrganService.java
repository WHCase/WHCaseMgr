package com.tianyi.whcase.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.viewmodel.CaseTJVM;
import com.tianyi.whcase.viewmodel.caseOrganVM;

@Service
public interface CaseOrganService {

	String insertCaseOrgan(String caseId, int indexOf);

	List<String> selectCaseLiseByOrganId(int organId);

	List<caseOrganVM> selectRecordLiseByCaseId(String caseId);

	CaseTJVM getCaseTJInfo(int organId);

	String updateReiceive(String caseId, int organId);

	CaseTJVM getCaseTJInfo(Map<String, Object> map);

}
