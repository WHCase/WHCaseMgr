<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>案件详细信息</title> 
	<script src='<%=basePath%>script/common/caseBackdetail.js' 	type='text/javascript'></script>
  </head>
  
  <body>
 	 <div>
 	 	<p style="padding:2px;border-bottom:1px solid black;width:99%;overflow:auto;"> 
			<a id="btnCancelSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-close" plain="true">关闭</a> 
		</p>
	</div>
     <div id="caseDeatilsInfo"  class="easyui-tabs" style="width:680px;height:380px;overflow:auto;" >
       <div title="案件主体信息" style="padding:10px">
       	  <p style="padding:5px">
       		<label style="font-size:12px">案件编号：</label><input id="txtCaseNo" class="easyui-validatebox" readonly="readonly" />
       		<label style="font-size:12px">案件名称：</label><input id="txtCaseName" class="easyui-validatebox" readonly="readonly" />
       	  </p>
       	  <p style="padding:5px">
       		<label style="font-size:12px">案件类型：</label><input id="txtCaseType" class="easyui-validatebox" readonly="readonly" />
       		<label style="font-size:12px">案发时间：</label><input id="txtCaseTime" class="easyui-validatebox" readonly="readonly" />
       	 </p>
       	  <p style="padding:5px">
       		<label style="font-size:12px">案发地域：</label><input id="txtCaseArea" class="easyui-validatebox" readonly="readonly" />
       	 </p>
       	 <p>
       		<label style="font-size:12px">简要案情：</label><textarea id="txtCaseDesc" style="width:63%" rows="4" readonly="readonly"></textarea>
       	 </p>
       	 <p style="padding:5px">
       	  	<label style="font-size:12px">案件状态：</label><input id="txtCaseStatus" class="easyui-validatebox" readonly="readonly" />
       		<label style="font-size:12px">破案单位：</label><input id="txtCaseUnit" class="easyui-validatebox"  readonly="readonly"/>
       	 </p>
       	 <p style="padding:5px">
       		<!-- <label style="font-size:12px">录入单位：</label><input id="txtCaseOrgan" class="easyui-validatebox" readonly="readonly" /> -->
       		<label style="font-size:12px">录入人员：</label><input id="txtCaseCreator" class="easyui-validatebox" readonly="readonly" />
       	 </p>
       </div> 
       <div title="案件反馈信息" style="padding:10px">
       
 	 <div>
 	 	<p style="padding:2px;border-bottom:1px solid black;width:99%;"> 
 	 		<label style="font-size:12px">反馈单位：</label>
			<input id="feedbackOrgan" class="easyui-combobox" data-options="editable:false" />
		</p>
	</div>
       	  <p>
       		<label style="font-size:12px">案件编号：</label><input id="txtCaseBackNo" class="easyui-validatebox" readonly="readonly" />
       		<label style="font-size:12px">案件名称：</label><input id="txtCaseBackName" class="easyui-validatebox" readonly="readonly" />
       	  </p>
       	 <p>
       		<label style="font-size:12px">反馈内容：</label><textarea id="txtBackWords" style="width:63%" rows="4" readonly="readonly"></textarea>
       	 </p>
       	  <p>
       		<label style="font-size:12px">反馈结果：</label><input id="txtCaseBackResult" class="easyui-validatebox" readonly="readonly" />
       		<label style="font-size:12px">反馈时间：</label><input id="txtCaseBackTime" class="easyui-validatebox" readonly="readonly" />
       	 </p>  
       	 <p>
       		<label style="font-size:12px">录入单位：</label><input id="txtCaseBackOrgan" class="easyui-validatebox" readonly="readonly" />
       		<label style="font-size:12px">录入人员：</label><input id="txtCaseBackCreator" class="easyui-validatebox" readonly="readonly" />
       	 </p>
       	 <div>
       	 	<div id="caseBackAttchMents"></div>
       	 </div>
       </div>
      <!--  <div title="案件串并案件" style="padding:10px">
       		<div id="caseRelative"></div>
       </div> -->
       <div title="案件附件信息" style="padding:10px;overflow:auto;">
       		<div id="caseAttchMents"></div>
       </div>
    </div>
  </body>
</html>
