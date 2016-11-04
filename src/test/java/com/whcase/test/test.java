package com.whcase.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.whcase.dao.CaseAttachMapper;
import com.tianyi.whcase.model.CaseAttach;

public class test extends BaseTest{
	/*@Test
	public  void main()  {
        genCfg();
    }*/

	
	
	/*private void genCfg() {
		// TODO Auto-generated method stub 
		String url = DbConfig.getInstance().getIpUrl();
		String port =DbConfig.getInstance().getPort();
		System.out.println("\n\r 返回结果："+url+":"+port);  
	}*/
	@Resource
	private CaseAttachMapper caseAttachMapper;
	
	@Test
	public void deleteTest() {
        //genCfg();
		int temp = caseAttachMapper.deleteByPrimaryKey("12310cc5-d27f-413f-8b58-9bd840a0c1c3");
		System.err.println(temp);
    }
}
