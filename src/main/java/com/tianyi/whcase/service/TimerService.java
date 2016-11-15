package com.tianyi.whcase.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.viewmodel.CaseVM;

public class TimerService {
	@Autowired CaseMapper caseMapper;
	@Autowired JieShangService jieshangService;
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	/**
	 * 获取案件更新（三月内）
	 * @return code：成功0，失败其他
	 */
	public int TimerService() {
		// TODO Auto-generated constructor stub
		Calendar c = Calendar.getInstance();
		Date endTime = new Date();
		c.setTime(endTime);
		c.add(Calendar.MONTH, -3);
		Date StartTime = c.getTime();
		String et = sdf.format(StartTime);
		String st = sdf.format(endTime);
		int pageIndex = 0;
		int pageSize = 20;
		int size = 20;
		int temp = 0;
		List<CaseVM> list = new ArrayList<CaseVM>();
		while(size ==20){
			list = jieshangService.QueryCases4WuHou(st, et, pageIndex, pageSize);			
			size = list.size();
			if(size > 0){
				temp = insertCase(list);
				if(temp != 0)
					break;
			}			
		}
		return temp;
	}

	/**
	 * 案件更新插入数据库
	 * @param list
	 * @return
	 */
	private int insertCase(List<CaseVM> list){
		int code = 1;
		Case ccase = new Case();
		try{
			for(Case c:list){
				ccase = caseMapper.selectByPrimaryKey(c.getId());
				if(ccase != null){
					caseMapper.updateByPrimaryKey(c);
				}else{
					caseMapper.insert(c);
				}
			}
			code = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return code;		
	}
	
	
}
