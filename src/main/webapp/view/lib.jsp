<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<!--[if lt IE 9]>
    <script src='<%=basePath%>js/sys/html5shiv.js' type='text/javascript'></script>
    <![endif]-->
   
<link href='<%=basePath%>css/easyui/icon.css'	media='all' rel='stylesheet' type='text/css' />
<link href='<%=basePath%>css/jquery.loadmask.css'	media='all' rel='stylesheet' type='text/css' />
<link href='<%=basePath%>css/easyui/metro/easyui.css'	media='all' rel='stylesheet' type='text/css' /> 
<link href='<%=basePath%>css/fontawesome/css/font-awesome.min.css'	media='all' rel='stylesheet' type='text/css' />  
<link href='<%=basePath%>css/dialog.default.css'	media='all' rel='stylesheet' type='text/css' /> 

<script src='<%=basePath%>script/jquery-1.11.1.min.js'	type='text/javascript'></script>
<script src='<%=basePath%>script/easyui/jquery.easyui.1.4.1.min.js'	type='text/javascript'></script>
<script src='<%=basePath%>script/jquery.loadmask/jquery.loadmask.min.js'	type='text/javascript'></script>
<script src='<%=basePath%>script/jquery.artDialog.js'	type='text/javascript'></script>

<script src='<%=basePath%>script/json2.js' type='text/javascript'></script>
<script src='<%=basePath%>script/easyui/easyui-lang-zh_CN.js' type='text/javascript'></script>
<script src='<%=basePath%>script/common.js' type='text/javascript'></script>


