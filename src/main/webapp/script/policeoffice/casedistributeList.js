var m_caseType;
var m_caseInfo_Object = {};
var m_caseInfo_dlg;
var m_casePush_dlg;
var m_caseNo;
var m_caseId;
$(function() { 
	var obj = getUrlArgs();
	m_caseType = obj.caseType; 
	if(m_caseType==1||m_caseType == "1"){
		$("#casePushTb a[doc='caseReback']").attr("style","display:none");
	}else{
		$("#casePushTb a[doc='casePushAction']").attr("style","display:none");
	}
	CasePushManage.packageObject();
	CasePushManage.loadCaseList();
	$("#pushCase").bind("click", CasePushManage.pushCase);
	$("#editCase").bind("click", CasePushManage.editCase);
	$("#showCaseInfo").bind("click", CasePushManage.showCaseInfo);
	$("#showCaseBackInfo").bind("click", CasePushManage.showCaseBackInfo);
	$("#btnSavePushInfo").bind("click", CasePushManage.savePushInfo);
	$("#btnCancelSave").bind("click", CasePushManage.cancelSave);
});

var CasePushManage = { 
		packageObject : function(row) {
			m_caseInfo_Object.caseType = m_caseType;
			m_caseInfo_Object.startTime = $("#sch_startTime").datebox("getValue");
			m_caseInfo_Object.endTime = $("#sch_endTime").datebox("getValue"); 
		},
		loadCaseList:function(){
			$('#casePushListGrid').datagrid({
				url : 'case/getCasePushList.do',
				queryParams : {
					'case_Query' : JSON.stringify(m_caseInfo_Object)
				},
				fitColumns : true,
				rownumbers : true,
				pagination : true,
				pageNumber : 1,
				pageSize : 20,
				nowrap : false,
				idField : 'id', 
				onDblClickRow : CasePushManage.showCaseInfo,
				toolbar : "#casePushTb",
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '案件状态', field : 'caseStatus', align : 'center', width : 150 },
				              { title : '案件编号', field : 'caseNo', align : 'center', width : 150 },
				              { title : '案件名称', field : 'caseName', align : 'center', width : 150 },
				              { title : '案件类型', field : 'caseType', align : 'center', width : 150 },
				              { title : '案件时间', field : 'caseTime', align : 'center', width : 150 },
				              { title : '案件所属区域', field : 'regionName', align : 'center', width : 150 },
				              { title : '简要案情', field : 'caseNote', align : 'center', width : 150 },
				              { title : '案件编号', field : 'caseNo', align : 'center', width : 150 }
				          ] ]
			});
		},
		editCase:function(){
			try {
				/*var dataRows = $('#casePushListGrid').datagrid('getRows');
				if (dataRows.length == 0) {
					$.messager.alert('操作提示', "没有可操作数据", "warning");
					return;
				}
				var target = $("#casePushListGrid").datagrid("getChecked");
				if (!target || target.length == 0) {
					$.messager.alert('操作提示', "请选择操作项!", "warning");
					return;
				} */
				var caseId = 0;//target[0].id;
				var caseNo = 0;//target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '案件信息查看', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/caseInfoEdit.jsp?caseId="+caseId+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		pushCase:function(){
			try {
				/*var dataRows = $('#casePushListGrid').datagrid('getRows');
				if (dataRows.length == 0) {
					$.messager.alert('操作提示', "没有可操作数据", "warning");
					return;
				}
				var target = $("#casePushListGrid").datagrid("getChecked");
				if (!target || target.length == 0) {
					$.messager.alert('操作提示', "请选择操作项!", "warning");
					return;
				} 
				m_caseId = target[0].id;
				m_caseNo = target[0].caseNo;*/
				m_casePush_dlg = art
						.dialog({
							id : 'dlgpushCase',
							title : '案件分配',
							height: 380,
							width: 290,
							content : document.getElementById("div_orgtree"),
							lock : true,
							initFn : function() {
								$('#organTreeView').tree({
									checkbox : true
								});
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		showCaseInfo:function(){
			try {
				/*var dataRows = $('#casePushListGrid').datagrid('getRows');
				if (dataRows.length == 0) {
					$.messager.alert('操作提示', "没有可操作数据", "warning");
					return;
				}
				var target = $("#casePushListGrid").datagrid("getChecked");
				if (!target || target.length == 0) {
					$.messager.alert('操作提示', "请选择操作项!", "warning");
					return;
				} */
				var caseId = 0;//target[0].id;
				var caseNo = 0;//target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '案件信息查看', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/casedetailInfo.jsp?caseId="+caseId+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		showCaseBackInfo:function(){
			try {
				/*var dataRows = $('#casePushListGrid').datagrid('getRows');
				if (dataRows.length == 0) {
					$.messager.alert('操作提示', "没有可操作数据", "warning");
					return;
				}
				var target = $("#casePushListGrid").datagrid("getChecked");
				if (!target || target.length == 0) {
					$.messager.alert('操作提示', "请选择操作项!", "warning");
					return;
				} */
				var caseId = 0;//target[0].id;
				var caseNo = 0;//target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '案件反馈信息查看', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/caseBackInfo.jsp?caseId="+caseId+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		savePushInfo:function(){
			
		},
		cancelSave:function(){
			m_casePush_dlg.close();
		},
		doSearch:function(){
			CasePushManage.packageObject();
			$('#casePushListGrid').datagrid("reload",{'case_Query' : JSON.stringify(m_caseInfo_Object)});
		},
		doClean:function(){
			$("#sch_startTime").datebox("setValue","");
			$("#sch_endTime").datebox("setValue",""); 
		}
};