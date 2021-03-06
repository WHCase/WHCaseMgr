var m_caseInfo_id; 
var m_caseInfo_no; 

/**
 * basePath配置
 */
var local = window.location;  
var contextPath = local.pathname.split("/")[1];  
var basePath = local.protocol+"//"+local.host+"/"+contextPath;  


$(function() { 
	var obj = getUrlArgs(); 
	m_caseInfo_no = obj.caseNo; 
	/*案件编号*/
	m_caseInfo_id = obj.caseId;  
	CaseDetailsManage.loadCaseInfo();
	$("#caseRelativeModule").attr("style","display:none");
	$("#btnCancelSave").bind("click", CaseDetailsManage.cancelSave);
});

var CaseDetailsManage = {  
		loadCaseInfo:function(){
			CaseDetailsManage.loadCaseMainInfo();
			//CaseDetailsManage.loadCaseRelative();
			CaseDetailsManage.loadCaseAttchMents();
		},
		/**
		 * 加载案件主体信息
		 */
		loadCaseMainInfo:function(){
			$.ajax(basePath+'/case/getCaseMainInfo.do',{
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
		loadCaseRelative:function(){
			$('#caseRelative').datagrid({
				url : basePath+'/caseGroup/getCaseRelative.do?caseId='+m_caseInfo_id,
				fitColumns : true,
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true }, 
				              { title : '串案编号', field : 'code', align : 'center', width : 150 },
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
				url : basePath+'/caseAttch/getCaseAttchMents.do?caseId='+m_caseInfo_id, 
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				singleSelect: true,
				idField : 'id',  
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '附件名称', field : 'name', align : 'center', width : 150 },
				              { title : '附件类型', field : 'itemType', align : 'center', width : 150 },
				              { title : '查看', field : 'uri', align : 'center', width : 150,formatter:function(value,rowData,index){
				            	  var html = '<a id="deleteA" href="javascript:void(0);" onclick="CaseDetailsManage.showTheAttachItem('+index+')">下载</a>';
				            	  return html;
				              }}
				          ] ]
			});
		},
		showTheAttachItem:function(index){
			
			var dataRows = $('#caseAttchMents').datagrid('getRows');
			if (dataRows.length == 0) {
				$.messager.alert('操作提示', "没有可操作数据", "warning");
				return;
			}
			var target = dataRows[index];
			if (!target || target.length == 0) {
				$.messager.alert('操作提示', "请选择操作项!", "warning");
				return;
			}
			var url = target.uri;
	
			/*url = url.replace('resource://','');			
			var fileURL=window.open ("\\"+"case/"+url,"_blank","height=0,width=0,toolbar=no,menubar=no,scrollbars=no,resizable=on,location=no,status=no");
		       
			    fileURL.document.execCommand("SaveAs");
		        fileURL.window.close();
		        fileURL.close();*/
			// 修改人 xie
			$.ajax({
				url:basePath+"/caseAttch/downloadCaseAttch.do",
				dataType:"JSON",
				data:{url:url},
				type:"get",
				success: function(data){
			        if(data == 0){
			        	$.messager.alert('操作提示',"下载成功",'info',function(){
			        		window.location.href=basePath+"/caseAttch/downloadFile.do?url="+url;
			        	});
			        }else{
			        	$.messager.alert('操作提示',"下载失败",'error');
			        }
				},
				error:function(){
					alert("失败");
				}
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
			$('#txtCaseNo').val(caseInfo.code);
			$('#txtCaseName').val(caseInfo.name);
			$('#txtCaseType').val(caseInfo.categoryName);
			$('#txtCaseTime').val(caseInfo.startTime);
			$('#txtCaseArea').val(caseInfo.detectedunitNname);
			$('#txtCaseDesc').val(caseInfo.summary);

			$('#txtCaseStatus').val(caseInfo.status);
			$('#txtCaseUnit').val(caseInfo.detectedunitNname);
			$('#txtCaseOrgan').val(caseInfo.organizationame);
			$('#txtCaseCreator').val(caseInfo.creator);
		}
};