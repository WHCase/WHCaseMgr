package com.tianyi.whcase.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tianyi.whcase.dao.CaseMapper;
import com.tianyi.whcase.model.Case;
import com.tianyi.whcase.service.CommonService;
import com.tianyi.whcase.service.JieShangService;
import com.tianyi.whcase.service.TimeService;
import com.tianyi.whcase.viewmodel.CaseVM;

@Component
public class TimeServiceImpl implements TimeService {


	@Autowired CaseMapper caseMapper;   
	@Autowired JieShangService jieshangService;
	@Autowired CommonService commomService;
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * 每天更新一次（凌晨3点）
	 */
	@Scheduled(fixedDelay = 30*60*1000)
	public void initTimer() {
		System.out.println("开启定时服务...");
		try{
			int count = -1;
			String time = sdf.format(new Date());
			String hours = time.substring(11, 13);
			//System.out.println("当前时间："+hours+"点");	
			//三点更新
			//if("03".equals(hours))
				count = updateCCase();			
			System.out.println("更新案件"+count+"条!");			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 更新三天内案件信息
	 * @return temp：更新案件总数
	 */
	private int updateCCase(){
		System.out.println("开始从捷尚获取数据...");
		Calendar c = Calendar.getInstance();
		Date endTime = new Date();
		c.setTime(endTime);
		c.add(Calendar.DAY_OF_MONTH, -3);
		Date StartTime = c.getTime();
		String et = sdf.format(endTime);
		String st = sdf.format(StartTime);
		int pageIndex = 0,pageSize = 20,code = 0,temp = 0;
		List<CaseVM> list = new ArrayList<CaseVM>();
		//每次更新20条
		while(pageSize ==20){
			list = jieshangService.QueryCases4WuHou(st, et, pageIndex, pageSize);			
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
		System.out.println("获取数据"+list.size()+"条，插入数据库...");
		int code = 0;
		CaseVM cv = new CaseVM();
		try{
			for(Case c:list){
				cv = jieshangService.getCase(c.getId());
				int temp = commomService.insertOrUpdateCaseVM(cv);
				if(temp == 400)
					System.out.println("更新案件及其信息失败，案件ID:"+cv.getId());
				code ++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("插入数据库"+code+"条数据!");
		return code;		
	}
	
}
