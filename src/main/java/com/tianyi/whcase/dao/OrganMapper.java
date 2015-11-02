package com.tianyi.whcase.dao;

import com.tianyi.whcase.core.MyBatisRepository;
import com.tianyi.whcase.model.Organ;

@MyBatisRepository
public interface OrganMapper {
	Organ selectByPrimaryKey(int id);
}
