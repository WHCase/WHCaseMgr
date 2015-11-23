package com.tianyi.whcase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyi.whcase.dao.OrganMapper;
import com.tianyi.whcase.model.Organ;
import com.tianyi.whcase.service.OrganService;

@Service
public class OrganServiceImpl implements OrganService {

	@Autowired
	OrganMapper organMapper;

	public int insert(Organ organ) {
		if (organMapper.selectByPrimaryKey(organ.getId()) != null) {
			return -1;
		} else
			return organMapper.insert(organ);
	}

}
