package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.model.CaseAttach;
import com.tianyi.whcase.model.CaseAttachItem;

@Service
public interface CaseAttchService {

	ListResult<CaseAttachItem> getCaseRelativeByCaseId(String id,int resourceType);

}
