package com.tianyi.whcase.service;

import org.springframework.stereotype.Service;

import com.tianyi.whcase.model.Organ;

@Service
public interface OrganService {
	int insert(Organ organ);
}
