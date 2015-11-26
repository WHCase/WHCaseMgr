<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户列表</title>
<script src='<%=basePath%>script/policestation/casereceiveList.js'
	type='text/javascript'></script>
</head>

<body>
	<div id="caseBackTb">
		<div>
			<p>
				<a id="receiveCase" name="receiveCase" doc="caseReceive"
					href="javascript:void(0);" class="easyui-linkbutton"
					style="background-color:red;color:red">案件接收</a>
				<!-- iconcls="icon-udq-add" plain="true" -->
				<a id="rebackCase" name="rebackCase" doc="caseReBack"
					href="javascript:void(0);" class="easyui-linkbutton"
					iconcls="icon-udq-add" plain="true">案件反馈</a> <a id="showCaseInfo"
					name="showCaseInfo" doc="caseReBack" href="javascript:void(0);"
					class="easyui-linkbutton" iconcls="icon-udq-show" plain="true">查看详情</a>
				<a id="exportCaseInfo" name="showCaseInfo" doc="casePushAction"
					href="javascript:void(0);" class="easyui-linkbutton"
					iconcls="icon-udq-show" plain="true">导出案件相关信息</a>
			</p>
		</div>
	</div>
	<div id="caseReceiveListGrid" style="margin:5px"></div>
	<div style="display:none">
		<div id="div_backInfo" style="width:520px;height:420px">
			<p id="tb_operation"
				style="padding:2px;border-bottom:1px solid black;width:99%;">
				<a id="btnSaveBackInfo" href="javascript:void(0);"
					class="easyui-linkbutton" iconcls="icon-udq-save" plain="true">反馈</a>
				<a id="btnCancelSave" href="javascript:void(0);"
					class="easyui-linkbutton" iconcls="icon-udq-close" plain="true">关闭</a>
			</p>
			<p>
				<label style="font-size:12px">案件编号：</label><input id="txtCaseNo"
					class="easyui-validatebox" /> <label style="font-size:12px">案件名称：</label><input
					id="txtCaseName" class="easyui-validatebox" />
			</p>
			<p>
				<label style="font-size:12px">反馈内容：</label>
				<textarea id="txtBackWords" style="width:79%" rows="3"></textarea>
			</p>
			<p>
				<label style="font-size:12px">反馈结果：</label><input id="txtCaseResult"
					class="easyui-validatebox" /> <label style="font-size:12px">反馈时间：</label><input
					id="txtCaseTime" class="easyui-datebox" />
			</p>
			<p>
				<!-- <label style="font-size:12px">录入单位：</label><input id="txtCaseOrgan" class="easyui-validatebox"  /> -->
				<!-- <label style="font-size:12px">录入人员：</label><input id="txtCaseCreator" class="easyui-validatebox"  />-->
			</p>
			<div>
				<div id="caseBackAttchs"></div>
			</div>
		</div>
	</div>
	<div style="display:none">
		<div id="caseBackAttchMentsTb" style="height:40px">
			<p>
				<a id="btnUploadFile" href="javascript:void(0);"
					class="easyui-linkbutton" plain="true">上传</a>
			</p>
		</div>
	</div>
</body>
</html>
