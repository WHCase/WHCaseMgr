var m_receiveStatus = 0;
var m_caseInfo_Object = {};
var m_caseInfo_dlg;
var m_casePush_dlg;
var m_caseNo;
var m_caseId;
/**
 * 列表中选中的行号和行数据
 */
var m_rowIndex = -1;
var m_rowData;

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
	$("#openPushCaseDlg").bind("click", CasePushManage.openPushCaseDlg);
	$("#editCase").bind("click", CasePushManage.editCase);
	$("#showCaseInfo").bind("click", CasePushManage.showCaseInfo);
	$("#showCaseBackInfo").bind("click", CasePushManage.showCaseBackInfo);
	$("#btnPushCase").bind("click", CasePushManage.pushCase);
	$("#btnCancelSave").bind("click", CasePushManage.cancelSave);

});

var CasePushManage = { 
		packageObject : function(row) {
			m_caseInfo_Object.receiveStatus = m_receiveStatus;
			//m_caseInfo_Object.startTime = $("#sch_startTime").datebox("getValue");
			//m_caseInfo_Object.endTime = $("#sch_endTime").datebox("getValue"); 
		},
		/**
		 * 加载案件列表
		 */
		loadCaseList:function(){
			CasePushManage.packageObject();
			$('#casePushListGrid').datagrid({
				url : 'case/getCasePushList.do',
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
				onClickRow:CasePushManage.setRowInfoBySelect,
				onDblClickRow : CasePushManage.showCaseInfo,
				toolbar : "#casePushTb",
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '案件状态', field : 'status', align : 'center', width : 150 },
				              { title : '案件编号', field : 'code', align : 'center', width : 150 },
				              { title : '案件名称', field : 'name', align : 'center', width : 150 },
				              { title : '案件类型', field : 'categoryName', align : 'center', width : 150 },
				              { title : '案件时间', field : 'startTime', align : 'center', width : 150 },
				              { title : '案件所属区域', field : 'organizationame', align : 'center', width : 150 },
				              { title : '简要案情', field : 'summary', align : 'center', width : 150 },
				              { title : '案件编号', field : 'code', align : 'center', width : 150 }
				          ] ]
			});
		},
		/**
		 * 编辑
		 */
		editCase:function(){
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
			try {
				var caseId = 0;
				var caseNo = 0;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '案件信息编辑 查看', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/caseInfoEdit.jsp?caseId="+m_rowData.id+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		/**
		 * 打开案件分配弹出框
		 */
		openPushCaseDlg:function(){
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
			try {
				m_casePush_dlg = art
						.dialog({
							id : 'dlgpushCase',
							title : '案件分配',
							height: 380,
							width: 290,
							content : document.getElementById("div_orgtree"),
							lock : true,
							initFn : function() {
								$('#txtCaseLevel').combobox({
									value:0
								});
								$('#organTreeView').tree({
									checkbox : true
								});
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		/**
		 * 列表单击响应
		 * @param rowIndex
		 * @param rowData
		 */
		setRowInfoBySelect:function(rowIndex,rowData){
			m_rowIndex = rowIndex;
			m_rowData = rowData;
		},
		/**
		 * 查看详情
		 */
		showCaseInfo:function(rowIndex, rowData){
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
			try {
				var caseId = 0;//target[0].id;
				var caseNo = 0;//target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '案件信息详情查看', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/casedetailInfo.jsp?caseId="+m_rowData.id+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		/**
		 * 查看反馈
		 */
		showCaseBackInfo:function(){
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
			try {
				var caseId = 0;//target[0].id;
				var caseNo = 0;//target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '案件反馈信息查看', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/caseBackInfo.jsp?caseId="+m_rowData.id+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		/**
		 * 案件分配
		 */
		pushCase:function(){
			/**
			 * 将案件分配给选中的机构
			 */
			//获取案件类型
			var caseLevel = $('#txtCaseLevel').combobox('getValue');
			
			//获取选中机构
			var organNodes = $('#organTreeView').tree('getChecked');
			if(caseLevel==""||organNodes==undefined||organNodes.length ==0){
				$.messager.alert('操作提示', "请先选择案件需要推送的派出所", "warning");
				m_casePush_dlg.close();
				return;
			}
			var organIdArray = [];
			var count = 0;
			$.each(organNodes,function(index,item){
				if(index !=0){
					organIdArray[count++] = item.id;
				}
			});
			$.ajax('CaseOrgan/pushCaseToOrgans.do',{
				type:'POST',
				data:{caseOrgan:JSON.stringify({caseId:m_rowData.id,organList:organIdArray})},
				success:function(responce){
					if(responce.isSuccess){
						$.ajax('CaseLevel/setCaseLevel.do',{
							type:'POST',
							data:{caseLevel:JSON.stringify({caseId:m_rowData.id,caseLevel:caseLevel})},
							success:function(responce){
								if(responce.isSuccess){
									$.messager.confirm("提示","案件推送成功。");
									CasePushManage.loadCaseList();
								}else{
									$.messager.alert("提示",responce.msg,"warning");
								}
							}
						});
					}else{
						$.messager.alert("提示",responce.msg,"warning");
					}
				}
			});
			
			
		},
		/**
		 * 关闭分配
		 */
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