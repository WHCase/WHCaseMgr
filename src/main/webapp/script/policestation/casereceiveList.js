var m_receiveStatus = 2;
var m_organ_id = 860;//机投派出所
var m_caseType;
var m_caseInfo_Object = {};
var m_caseInfo_dlg;
var m_caseBack_dlg;
var m_caseNo;
var m_caseId;
var isShow = false;

/**
 * 列表中选中的行号和行数据
 */
var m_rowIndex = -1;
var m_rowData;

$(function() { 
	var obj = getUrlArgs();
	m_caseType = obj.caseType; 
	if(m_caseType==2||m_caseType == "2"){
		$("#caseBackTb a[doc='caseReBack']").attr("style","display:none");
		$("#caseBackTb a[doc='caseReceive']").attr("style","display:none");
	}else{
		isShow = true;
		$("#caseBackTb a[doc='caseReceive']").attr("style","display:none");
	}
	CaseManage.packageObject();
	
	$("#receiveCase").bind("click", CaseManage.receiveCase);
	$("#rebackCase").bind("click", CaseManage.rebackCase);
	$("#showCaseInfo").bind("click", CaseManage.showCaseInfo);
	$("#btnSaveBackInfo").bind("click", CaseManage.saveBackInfo);
	$("#btnCancelSave").bind("click", CaseManage.cancelSave);
	
	var button = $('#btnUploadFile'),interval;
	
	new AjaxUpload(button,{
		action:'fileUpload/uploadFile.do',
		name:'file',
		onSubmit:function(file,ext){
			var data = {};
			data.id = m_rowData.id;
			//data.creator = m_rowData.creator;
			this.setData(data);
			var text = "文件上传中";
			interval = window.setInterval(function() {
				if (text.length < 20) {
					text += ".";
				} else {
					text = "文件上传中";
				}
			}, 200);
		},
		onComplete:function(file,response){
			// button.text('上传图片(只允许上传大小不得大于10M)');
			// 清楚按钮的状态
			button.text('文件上传');
			window.clearInterval(interval);
			this.enable();
			button.text('选择文件');
		}
	});
});

var CaseManage = {  
		packageObject : function() {
			m_caseInfo_Object.receiveStatus = m_caseType;
			/*获取该机构下的案件*/
			$.ajax('CaseOrgan/getCaseListByOrganId.do',{
				type:'POST',
				data:{organId:m_organ_id},
				success:function(responce){
					if(responce.rows!=undefined){
						m_caseInfo_Object.caseIdList = responce.rows;
					}
					CaseManage.loadCaseList();
				}
			});
		},
		loadCaseList:function(){
			
			$('#caseReceiveListGrid').datagrid({
				url : 'case/getDistributeCaseList.do',
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
				onClickRow:CaseManage.setRowInfoBySelect,
				onDblClickRow : CaseManage.showCaseInfo,
				toolbar : "#caseBackTb",
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '操作', field : 'operation', align : 'center', width : 150,formatter:function(value,rowData,index){
				            	  var html = '<a id="'+rowData.id+'" name="receiveCase"'
				            		  		+'href="javascript:void(0);" onclick="CaseManage.receiveCase('+index+')" class="easyui-linkbutton"'
				            		  		+'>接收</a>';
				            	  return html;
				              },hidden:isShow},
				              { title : '案件等级', field : 'level', align : 'center', width : 150 },
				              { title : '案件状态', field : 'status', align : 'center', width : 150 },
				              { title : '案件编号', field : 'caseNo', align : 'center', width : 150 },
				              { title : '案件名称', field : 'name', align : 'center', width : 150 },
				              { title : '案件类型', field : 'categoryName', align : 'center', width : 150 },
				              { title : '案件时间', field : 'startTime', align : 'center', width : 150 },
				              { title : '案件所属区域', field : 'organizationame', align : 'center', width : 150 },
				              { title : '简要案情', field : 'summary', align : 'center', width : 150 },
				              { title : '案件编号', field : 'code', align : 'center', width : 150 }
				          ] ]
			});
		},
		receiveCase:function(index){
			var dataRows = $('#caseReceiveListGrid').datagrid('getRows');
			if (dataRows.length == 0) {
				$.messager.alert('操作提示', "没有可操作数据", "warning");
				return;
			}
			var target = dataRows[index];
			if (!target || target.length == 0) {
				$.messager.alert('操作提示', "请选择操作项!", "warning");
				return;
			} 
			var caseId = target.id;
			var caseLevel = target.level;
			$.ajax('case/acceptPushCase.do',{
				type:'POST',
				data:{caseId:caseId,
					caseLevel:caseLevel
					},
				success:function(responce){
					if(responce.isSuccess){
						$.messager.alert('提示', "接收案件成功", "normal");
					}else{
						$.messager.alert('提示', "案件接收失败", "warning");
					}
					CaseManage.loadCaseList();
				}
			});
		},
		rebackCase:function(){
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
			try {
				m_caseBack_dlg = art
						.dialog({
							id : 'dlgCase',
							title : '案件反馈',
							height: 440,
							width: 540,
							content : document.getElementById("div_backInfo"),
							lock : true,
							initFn : function() {
								CaseManage.loadCaseMainInfo(m_rowData.id);
								 $("#caseBackAttchs").datagrid({
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
		loadCaseMainInfo:function(caseId){
			$.ajax('case/getCaseMainInfo.do',{
				type:'POST',
				data:{caseId:caseId},
				success:function(responce){
					if(responce.isSuccess){
						CaseManage.mainCaseInfoBind(responce.data);
					}else{
						$.messager.alert('查询数据', responce.msg, "warning");
					}
					
				}
			});
		},
		mainCaseInfoBind:function(caseInfo){
			$('#txtCaseNo').val(caseInfo.id);
			$('#txtCaseName').val(caseInfo.name);
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
		showCaseInfo:function(rowIndex, rowData){			
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
			try {
				var caseId =0;// target[0].id;
				var caseNo =0;// target[0].caseNo;
				m_caseInfo_dlg = art
						.dialog({
							id : 'dlgShowCaseInfo',
							title : '查看案件信息', 
							content : "<iframe scrolling='yes' frameborder='0' src='view/common/casedetailInfo.jsp?caseId="+m_rowData.id+"&caseNo="+caseNo+"' style='width:710px;height:450px;overflow:hidden'/>",
							lock : true,
							initFn : function() {
							}
						});
			} catch (ex) {
				$.messager.alert("操作提示", ex.message, "error");
			}
		},
		saveBackInfo:function(){
			/**
			 * 判断修改是否合法
			 */
			var caseFeed = CaseManage.packageFeedBackInfo();
			
			$.ajax('caseFeed/saveFeedBacInfo.do',{
				type:'POST',
				data:{'feedBackInfo':JSON.stringify(caseFeed)},
				success:function(responce){
					if(responce.isSuccess){
						$.messager.alert("提示","案件修改成功","normal");
					}else{
						$.messager.alert("提示",responce.msg,"normal");
					}
				}
			});
		},
		packageFeedBackInfo:function(){
			/**
			 * 获取用户输入的反馈信息和上传的文件
			 */
			var feedBack = {};
			feedBack.caseId = $('#txtCaseNo').val();
			feedBack.content = $('#txtBackWords').val();
			feedBack.caseResult = $('#txtCaseResult').val();;
			feedBack.createTime = $('#txtCaseTime').datebox('getValue');
			feedBack.organizationId = $('#txtCaseOrgan').val();
			feedBack.creator = $('#txtCaseCreator').val();
			return feedBack;
		},
		cancelSave:function(){
			m_caseBack_dlg.close();
		},
		uploadFile:function(){
			/**
			 * 打开一个文件选择框
			 */
			$.messager.alert("操作提示", "上传文件", "error");
		}
};