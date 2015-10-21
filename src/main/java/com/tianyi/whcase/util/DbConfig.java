package com.tianyi.whcase.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class DbConfig {
	//捷尚接口地址配置文件路径
    private static final String ConfigPath = "jdbc.properties";  
    private static DbConfig instance=null;
    
    private String ipUrl=null;
    private String port=null;  
    public String getIpUrl() {
		return ipUrl;
	}
	public void setIpUrl(String ipUrl) {
		this.ipUrl = ipUrl;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	private DbConfig(){}

    public static DbConfig getInstance(){
        if(instance==null){
            instance= new DbConfig().getNewDbConfig();
        }
        return instance;
    }

    private DbConfig getNewDbConfig(){
        
        DbConfig dc=new DbConfig();
        Properties prop = new Properties();  
        String path=null;
        FileInputStream fis=null;
        
        try {
            path = this.getClass().getClassLoader().getResource("").toURI().getPath();
            fis = new FileInputStream(new File(path + ConfigPath));
            prop.load(fis);
            dc.ipUrl=prop.getProperty("IF_JSIpUrl"); 
            dc.port=prop.getProperty("IF_Port");   
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        
        return dc;
    }
}
