<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script src='<%=basePath%>script/policestation/caseTJ.js' 	type='text/javascript'></script>
    
    <title>案件统计详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
	<div style="padding:5px" id="selectOrgan">
		<label style="font-size:14px">选择派出所:</label>
		<input id="selectOrganTree" class="easyui-combotree" data-options="url:'data/tree_organ.json',method:'get'" style="width:200px;">
		<a id="btnSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-search" plain="true" onclick="caseTJManage.doSearch();">查询</a>
	</div>
  	<div id=""></div>
    <div id="caseTJListGrid" style="margin:5px"></div>
  </body>
</html>
