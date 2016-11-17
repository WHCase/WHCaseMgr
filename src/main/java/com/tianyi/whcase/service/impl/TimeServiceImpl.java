package com.tianyi.whcase.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.service.JieShangService;
import com.tianyi.whcase.service.TimeService;
import com.tianyi.whcase.viewmodel.CaseVM;

@Component
public class TimeServiceImpl implements TimeService {


	@Autowired CaseMapper caseMapper;   //这里注解有问题， 不能用auto哪个， 要用Resource
	@Autowired JieShangService jieshangService;
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@Scheduled(fixedDelay = 10*1000)
	public void initTimer() {
		System.out.println("更新案件????条!");
		try{
			int count = -1;
			String time = sdf.format(new Date());
			String hours = time.substring(13, 15);
			System.out.println(hours+"点");	
	//		if("10".equals(hours))
				count = updateCCase();			
			System.out.println("更新案件"+count+"条!");			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 更新一月内案件信息
	 * @return temp：更新案件总数
	 */
	private int updateCCase(){
		Calendar c = Calendar.getInstance();
		Date endTime = new Date();
		c.setTime(endTime);
		c.add(Calendar.MONTH, -1);
		Date StartTime = c.getTime();
		String et = sdf.format(endTime);
		String st = sdf.format(StartTime);
		int pageIndex = 0,pageSize = 20,code = 0,temp = 0;
		List<CaseVM> list = new ArrayList<CaseVM>();
		//每次更新20条
		while(pageSize ==20){
			//list = jieshangService.QueryCases4WuHou(st, et, pageIndex, pageSize);			
			pageSize = list.size();
			pageIndex++;
			if(pageSize > 0){
				code = insertCase(list);
				temp += code;
				if(code != 20)
					break;
			}			
		}
		return temp;
	}
	
	/**
	 * 案件更新插入数据库
	 * @param list
	 * @return code:插入数据库案件总数
	 */
	private int insertCase(List<CaseVM> list){
		int code = 0;
		Case ccase = new Case();
		try{
			for(Case c:list){
				ccase = caseMapper.selectByPrimaryKey(c.getId());
				if(ccase != null){
					caseMapper.updateByPrimaryKey(c);
				}else{
					caseMapper.insert(c);
				}
				code ++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return code;		
	}
	
}
