var m_caseType;
var m_caseInfo_Object = {};
var m_caseInfo_dlg;
var m_caseBack_dlg;
var m_caseNo;
var m_caseId;
var isShow = false;
$(function() { 
	var obj = getUrlArgs();
	m_caseType = obj.caseType; 
	if(m_caseType==1||m_caseType == "1"){
		$("#caseBackTb a[doc='caseReBack']").attr("style","display:none");
	}else{
		isShow = true;
		$("#caseBackTb a[doc='caseReceive']").attr("style","display:none");
	}
	CaseManage.loadCaseList();
	$("#receiveCase").bind("click", CaseManage.receiveCase);
	$("#rebackCase").bind("click", CaseManage.rebackCase);
	$("#showCaseInfo").bind("click", CaseManage.showCaseInfo);
	$("#btnSaveBackInfo").bind("click", CaseManage.saveBackInfo);
	$("#btnCancelSave").bind("click", CaseManage.cancelSave);
});

var CaseManage = {  
		
		loadCaseList:function(){
			$('#caseReceiveListGrid').datagrid({
				url : 'case/getCaseList.do',
				queryParams : {
					'caseInfo' : JSON.stringify(m_caseInfo_Object)
				},
				fitColumns : true,
				rownumbers : true,
				pagination : true,
				pageNumber : 1,
				pageSize : 20,
				nowrap : false,
				idField : 'id', 
				onDblClickRow : CaseManage.showCaseInfo,
				toolbar : "#caseBackTb",
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '操作', field : 'operation', align : 'center', width : 150,formatter:function(value,rowData,index){
				            	  var html = '<a id="receiveCase" name="receiveCase"'
				            		  		+'href="javascript:void(0);" onclick="CaseManage.receiveCase('+rowData.id+')" class="easyui-linkbutton"'
				            		  		+'>接收</a>';
				            	  return html;
				              },hidden:isShow},
				              { title : '案件等级', field : 'caseLevel', align : 'center', width : 150 },
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
		receiveCase:function(){
			var dataRows = $('#caseReceiveListGrid').datagrid('getRows');
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
			m_caseNo = target[0].caseNo;
		},
		rebackCase:function(){
			try {
				/*var dataRows = $('#caseReceiveListGrid').datagrid('getRows');
				if (dataRows.length == 0) {
					$.messager.alert('操作提示', "没有可操作数据", "warning");
					return;
				}
				var target = $("#caseReceiveListGrid").datagrid("getChecked");
				if (!target || target.length == 0) {
					$.messager.alert('操作提示', "请选择操作项!", "warning");
					return;
				} 
				m_caseId = target[0].id;
				m_caseNo = target[0].caseNo;*/
				m_caseBack_dlg = art
						.dialog({
							id : 'dlgCase',
							title : '案件反馈',
							height: 440,
							width: 540,
							content : document.getElementById("div_backInfo"),
							lock : true,
							initFn : function() {
								 $("#caseBackAttchs").datagrid({
										url : 'case/getCaseBackAttchMents.do', 
										rownumbers : true,
										pagination : false, 
										nowrap : false,
										idField : 'id',  
										toolbar : '#caseBackAttchMentsTb',  
										columns : [ [ 
										              { title : 'id', field : 'id', hidden : true },
										              { title : '附件名称', field : 'caseStatus', align : 'center', width : 150 },
										              { title : '附件类型', field : 'caseNo', align : 'center', width : 150 },
										              { title : '操作', field : 'caseName', align : 'center', width : 150,formatter:function(value,rowData,index){
										            	  var html = '<a id="deleteAttchs" name="deleteAttchs"'
									            		  		+'href="javascript:void(0);" onclick="CaseManage.deleteAttchs(caseNo)" class="easyui-linkbutton" iconcls="icon-udq-add"'
									            		  		+'>删除</a>';
										            	  return html;
										              } }
										          ] ]
									});
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		showCaseInfo:function(){
			try {
				/*var dataRows = $('#caseReceiveListGrid').datagrid('getRows');
				if (dataRows.length == 0) {
					$.messager.alert('操作提示', "没有可操作数据", "warning");
					return;
				}
				var target = $("#caseReceiveListGrid").datagrid("getChecked");
				if (!target || target.length == 0) {
					$.messager.alert('操作提示', "请选择操作项!", "warning");
					return;
				} */
				var caseId =0;// target[0].id;
				var caseNo =0;// target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '查看案件信息', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/casedetailInfo.jsp?caseId="+caseId+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		saveBackInfo:function(){
			
		},
		cancelSave:function(){
			m_caseBack_dlg.close();
		} 
};