var m_caseInfo_id; 
var m_caseInfo_no; 
$(function() { 
	var obj = getUrlArgs(); 
	m_caseInfo_no = obj.caseNo;  
	m_caseInfo_id = obj.caseId;  
	CaseDetailsManage.loadCaseInfo();
	$("#btnCancelSave").bind("click", CaseDetailsManage.cancelSave);
});

var CaseDetailsManage = {  
		loadCaseInfo:function(){
			CaseDetailsManage.loadCaseMainInfo();
			CaseDetailsManage.loadCaseBackInfo();
			CaseDetailsManage.loadCaseRelative();
			CaseDetailsManage.loadCaseAttchMents();
		},  
		/**
		 * 加载案件主体信息
		 */
		loadCaseMainInfo:function(){
			$.ajax('case/getCaseMainInfo.do',{
				type:'POST',
				data:{caseId:m_caseInfo_id},
				success:function(responce){
					if(responce.isSuccess){
						CaseDetailsManage.mainCaseInfoBind(responce.data);
					}else{
						$.messager.alert('查询数据', responce.msg, "warning");
					}
					
				}
			});
			
		},
		loadCaseBackInfo:function(){
			CaseDetailsManage.loadCaseBackMainInfo();
			CaseDetailsManage.loadCaseBackAttchMents();
		},
		loadCaseRelative:function(){
			$('#caseRelative').datagrid({
				url : 'caseGroup/getCaseRelative.do?caseId='+m_caseInfo_id,
				fitColumns : true,
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true }, 
				              { title : '串案编号', field : 'caseId', align : 'center', width : 150 },
				              { title : '串案名称', field : 'name', align : 'center', width : 150 }, 
				              { title : '创建时间', field : 'createTime', align : 'center', width : 150 },
				              { title : '负责人', field : 'creator', align : 'center', width : 150 },
				              { title : '审核状态', field : 'auditstate', align : 'center', width : 150 },
				              { title : '操作', field : 'operation', align : 'center', width : 150 }
				          ] ]
			});
		},
		loadCaseAttchMents:function(){
			$('#caseAttchMents').datagrid({
				url : 'caseAttch/getCaseAttchMents.do?caseId='+m_caseInfo_id, 
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '附件名称', field : 'name', align : 'center', width : 150 },
				              { title : '附件类型', field : 'itemType', align : 'center', width : 150 },
				              { title : '链接', field : 'uri', align : 'center', width : 150 }
				          ] ]
			});
		},
		loadCaseBackMainInfo:function(){
			$.ajax('caseFeed/getCaseBackMainInfo.do',{
				type:'POST',
				data:{caseId:m_caseInfo_id},
				success:function(responce){
					if(responce.isSuccess){
						CaseDetailsManage.mainCaseBackInfoBind(responce.data);
					}else{
						$.messager.alert('查询数据', responce.msg, "warning");
					}
					
				}
			});
		},
		loadCaseBackAttchMents:function(){
			$('#caseBackAttchMents').datagrid({
				url : 'caseFeed/getCaseBackAttchMents.do?caseId='+m_caseInfo_id, 
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '附件名称', field : 'name', align : 'center', width : 150 },
				              { title : '附件类型', field : 'itemType', align : 'center', width : 150 },
				              { title : '链接', field : 'uri', align : 'center', width : 150 }
				          ] ]
			});
		},
		cancelSave:function(){
			parent.m_caseInfo_dlg.close();
		},
		/**
		 * 案件主体信息绑定到响应的控件上
		 */
		mainCaseInfoBind:function(caseInfo){
			if(caseInfo==undefined){
				return;
			}
			$('#txtCaseNo').val(caseInfo.id);
			$('#txtCaseName').val(caseInfo.name);
			$('#txtCaseType').val(caseInfo.categoryName);
			$('#txtCaseTime').val(caseInfo.startTime);
			$('#txtCaseArea').val("经度："+caseInfo.latitude+",纬度："+caseInfo.longitude);
			$('#txtCaseDesc').val(caseInfo.summary);
			$('#txtCaseStatus').val(caseInfo.status);
			$('#txtCaseUnit').val(caseInfo.detectedunitNname);
			$('#txtCaseOrgan').val(caseInfo.organizationame);
			$('#txtCaseCreator').val(caseInfo.creator);
		},
		mainCaseBackInfoBind:function(caseFeedInfo){
			if(caseFeedInfo==null){
				return;
			}
			$('#txtCaseBackNo').val(caseFeedInfo.id);
			$('#txtCaseBackName').val(caseFeedInfo.caseName);
			$('#txtBackWords').val(caseFeedInfo.content);
			$('#txtCaseBackResult').val(caseFeedInfo.caseResult);
			$('#txtCaseBackTime').val(caseFeedInfo.createTime);
			$('#txtCaseBackOrgan').val(caseFeedInfo.organizationId);
			$('#txtCaseBackCreator').val(caseFeedInfo.creator);
		}
};