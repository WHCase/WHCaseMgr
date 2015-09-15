<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/view/lib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户列表</title> 
	<script src='<%=basePath%>script/policeoffice/casedistributeList.js' 	type='text/javascript'></script>
  </head>
  
  <body>
     <div id="casePushTb" >
        <div>
            <p> 
                <a id="openPushCaseDlg" name="openPushCaseDlg" doc="casePushAction"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-add"
                    plain="true">案件分配</a>
                <a id="editCase" name="editCase" doc="casePushAction"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-edit"
                    plain="true">编辑</a>
                <a id="showCaseInfo" name="showCaseInfo"  doc="casePushAction"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-show"
                    plain="true">查看详情</a> 
                <a id="showCaseBackInfo" name="showCaseInfo" doc="caseReback"
                    href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-show"
                    plain="true">查看详情</a> 
                <span style="padding-left:25px">
					<label>起始时间:</label><input id="sch_startTime" class="easyui-datebox" style="width:120px" data-options="editable:false" />
            		<label>截止时间:</label><input id="sch_endTime" class="easyui-datebox" style="width:120px"  data-options="editable:false" />
            	</span>
            	<a id="btnSearch" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-search" plain="true" onclick="CasePushManage.doSearch();">查询</a>
            	<a id="btnclearUp" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-clear" plain="true" onclick="CasePushManage.doClean();">清空</a>
            </p>  
        </div>
    </div>
    <div id="casePushListGrid"  style="margin:5px" ></div>
    <div style="display:none">
    	<div id="div_orgtree" style="width:100%">
    		<p id="tb_operation" style="padding:2px;border-bottom:1px solid black;width:99%;">
		 		<a id="btnPushCase" href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-save" plain="true">分配</a> 
				<a id="btnCancelSave"  href="javascript:void(0);" class="easyui-linkbutton" iconcls="icon-udq-close" plain="true">关闭</a> 
		 	</p>
    		<label style="font-size:12px">案情等级：</label><input id="txtCaseLevel" class="easyui-combobox"  data-options="valueField:'id',textField:'name',url:'data/combobox_caseType.json'" />
    		<div style="height:300px;padding:5px">
    			<label style="font-size:14px">选择派出所推送案件:</label>
    			<ul id="organTreeView" class="easyui-tree" data-options="url:'data/tree_organ.json',method:'get',animate:true,checkbox:true"></ul>
    		</div>
    	</div>
    </div>
  </body>
</html>
