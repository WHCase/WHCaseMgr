<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<script src='<%=basePath%>script/index.js' 	type='text/javascript'></script>
    <title>案件分配系统</title>
    
</head> 
<body id="cc" class="easyui-layout"  style="width:100%;height:100%; overflow: hidden;" oncontextmenu=self.event.returnValue=false>
	<div region="north" style="background-color: #ffffff; height: 80px; overflow:hidden">
		<div style="float: left; padding-left: 15px;">
			<img height="75px" src="resource/icon/login/logo1.png" />
		</div> 
	</div> 
	<div region="west" title="功能菜单" split=true style="width:220px;">
		<ul id="treeMenu">
			<li>
				<span>案件分配(分局)</span>
					<ul>
						<li>
							<span>案件分配</span>
						</li>
						<li>
							<span>案件查看</span>
						</li> 
					</ul>  
			</li>
			<li>
				<span>案件接收(派出所)</span> 
					<ul>
						<li>
							<span>案件接收</span>
						</li>
						<li>
							<span>案件反馈</span>
						</li> 
					</ul> 
			</li>
			<li>
				<span>统计报表</span>
					<ul>
						<li>
							<span>统计1</span>
						</li>
						<li>
							<span>统计2</span>
						</li> 
					</ul> 
			</li> 
			<li>
				<span>督查考核</span>
					<ul>
						<li>
							<span>考核1</span>
						</li>
						<li>
							<span>考核2</span>
						</li> 
					</ul> 
			</li> 
		</ul>
	</div> 
	<div region=center title="案件分配推送" > 
		<iframe id="ifrContent" frameborder='0'  style="width:100%;height:98%;" onLoad="iframeSize()">
		</iframe>
	</div> 
	<div region="south"  style="background-color: #4974a4; height: 30px; overflow:hidden">
		<table style="padding-top: 4px;">
			<tr>
				<td>
					<label style="font-size: 13px; color: white;">部门：</label>
				</td>
				<td>
					<label style="font-size: 13px; color: white;" id='labOrgName'>
					</label>
				</td>
			</tr>
		</table>
	</div> 
</body>
</html>
