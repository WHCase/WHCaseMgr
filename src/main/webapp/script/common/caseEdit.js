var m_caseInfo_id; 
var m_caseInfo_no; 
$(function() { 
	var obj = getUrlArgs(); 
	m_caseInfo_no = obj.caseNo;  
	m_caseInfo_id = obj.caseId;  
	CaseDetailsManage.loadCaseInfo();
	$("#btnCancelSave").bind("click", CaseDetailsManage.cancelSave);
	$("#btnSaveCaseInfo").bind("click", CaseDetailsManage.SaveCaseInfo);
});

var CaseDetailsManage = {  
		loadCaseInfo:function(){
			CaseDetailsManage.loadCaseRelative();
			CaseDetailsManage.loadCaseAttchMents();
		},  
		loadCaseRelative:function(){
			$('#caseRelative').datagrid({
				url : 'case/getCaseRelative.do?caseId='+m_caseInfo_id,
				fitColumns : true,
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true }, 
				              { title : '串案编号', field : 'caseNo', align : 'center', width : 150 },
				              { title : '串案名称', field : 'caseName', align : 'center', width : 150 }, 
				              { title : '创建时间', field : 'caseTime', align : 'center', width : 150 },
				              { title : '负责人', field : 'leader', align : 'center', width : 150 },
				              { title : '审核状态', field : 'status', align : 'center', width : 150 },
				              { title : '操作', field : 'operation', align : 'center', width : 150 }
				          ] ]
			});
		},
		loadCaseAttchMents:function(){
			$('#caseAttchMents').datagrid({
				url : 'case/getCaseAttchMents.do?caseId='+m_caseInfo_id, 
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '附件名称', field : 'caseStatus', align : 'center', width : 150 },
				              { title : '附件类型', field : 'caseNo', align : 'center', width : 150 },
				              { title : '链接', field : 'caseName', align : 'center', width : 150 }
				          ] ]
			});
		},
		SaveCaseInfo:function(){
			parent.m_caseInfo_dlg.close();
		},
		cancelSave:function(){
			parent.m_caseInfo_dlg.close();
		}
};