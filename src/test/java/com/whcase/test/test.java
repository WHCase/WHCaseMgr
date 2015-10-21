package com.whcase.test;

import org.junit.Test;

import com.tianyi.whcase.util.DbConfig;

public class test {
	@Test
	public  void main()  {
        genCfg();
    }

	private void genCfg() {
		// TODO Auto-generated method stub 
		String url = DbConfig.getInstance().getIpUrl();
		String port =DbConfig.getInstance().getPort();
		System.out.println("\n\r 返回结果："+url+":"+port);  
	}
}
