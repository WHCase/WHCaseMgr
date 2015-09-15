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
			CaseDetailsManage.loadCaseMainInfo();
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
		/**
		 * 加载主体案件的串案信息
		 */
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
		/**
		 * 加载主体案件的附件信息
		 */
		SaveCaseInfo:function(){
			/**
			 * 判断修改是否合法
			 */
			var caseInfo = CaseDetailsManage.packageObject();
			
			$.ajax('case/SaveCaseInfo.do',{
				type:'POST',
				data:{'caseInfo':JSON.stringify(caseInfo)},
				success:function(responce){
					if(responce.isSuccess){
						$.messager.confirm("提示","案件修改成功。");
					}else{
						$.messager.alert("提示",responce.msg,"normal");
					}
				}
			});
		},
		packageObject : function(row) {
			var caseInfo = {};
			caseInfo.id = $('#txtCaseNo').val();
			caseInfo.name = $('#txtCaseName').val();
			caseInfo.categoriesId = $('#txtCaseType').combobox('getValue');
			caseInfo.startTime = $('#txtCaseTime').datebox('getValue');
			caseInfo.summary = $('#txtCaseDesc').val();
			caseInfo.status = $('#txtCaseStatus').combobox('getValue');
			caseInfo.detectedunitId = $('#txtCaseUnit').combobox('getValue');
			caseInfo.organizationame = $('#txtCaseOrgan').val();
			caseInfo.creator = $('#txtCaseCreator').val();
			return caseInfo;
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
			$('#txtCaseType').combobox({
				value:caseInfo.categoryName
			});
			$('#txtCaseTime').datebox('setValue',caseInfo.startTime);
			$('#txtCaseArea').val("经度："+caseInfo.latitude+",纬度："+caseInfo.longitude);
			$('#txtCaseDesc').val(caseInfo.summary);
			$('#txtCaseStatus').combobox({
				value:caseInfo.status
			});
			$('#txtCaseUnit').combobox({
				value:caseInfo.detectedunitNname
			});
			$('#txtCaseOrgan').val(caseInfo.organizationame);
			$('#txtCaseCreator').val(caseInfo.creator);
		}
};