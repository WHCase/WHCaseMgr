var m_receiveStatus = 1;
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
	
	$("#exportCaseInfo").bind("click",CasePushManage.exportCaseInfo);
	
	/**
	 * for test
	 */
	$("#sendCaseByXMl").bind("click", CasePushManage.sendXML);	

});

var CasePushManage = { 
		sendXML:function(){
			/**
			 * 生成xml格式的文件，post到后台
			 */
			var doc = CasePushManage.createXML();
			var xmlHttp = CasePushManage.createXMLHttpRequest();
			//xmlHttp.open("post",'http://223.223.183.242:40000/center/UpdateCCase',true);
			xmlHttp.open("post",'http://192.168.0.201:40000/center/UpdateCCase',true);
			xmlHttp.onreadystatechange = CasePushManage.handleStateChange;
			xmlHttp.setRequestHeader("Content-Type","application/xml;charset=utf-8");
			xmlHttp.setRequestHeader("Access-Control-Allow-Origin", "*");
//			xmlHttp.send(doc.documentElement.outerHTML);
//			xmlHttp.send(doc.documentElement);
			xmlHttp.send(doc);
			
//			$.ajax('http://223.223.183.242:40000/center/UpdateCCase',{
//				type:'POST',
//				data:doc,
//				contentType:'application/xml;charset=utf-8',
//				success:function(responce){
//					if(responce["isSuccess"]){
//						$.messager.confirm("提示","案件推送成功。");
//						CasePushManage.loadCaseList();
//					}else{
//						$.messager.alert("提示",responce["msg"],"warning");
//					}
//				}
//			});
			
		},
		handleStateChange:function(xmlHttp){
			if(xmlHttp.readyState == 4){
				if(xmlHttp.status ==200){
					var responce = xmlHttp.responceText;
					$("#sendCaseByXMl").text = "结果:"+responce;
				}else{
					$("#sendCaseByXMl").text ="不允许跨域请求";
				}
			}
		},
		parseResults:function(){
			
		},
		createXMLHttpRequest:function(){
			var xmlhttp;
			if(window.ActiveXObject){
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}else{
				xmlhttp = new XMLHttpRequest();
				
			}
			return xmlhttp;
		},
		/**
		 * for test
		 * @returns
		 */
		createXML:function(){
			var doc = document.implementation.createDocument("", "", null);
			var root = doc.createElement("CCase");
			doc.appendChild(root);
			
			root.setAttribute("id","df4da455-f1a8-0d7d-b886-bd6305050505");
			root.setAttribute("Name", "有视频图片的案件");
			root.setAttribute("Creator", "1");
			root.setAttribute("CreateTime", "2015-09-09 10:44:06");
			root.setAttribute("Code", "xxxxxx");
			root.setAttribute("Categories", "dc440454-34dc-10c6-78fc-aa4e05050505");
			root.setAttribute("StartTime", "2015-09-09 10:44:06");
			root.setAttribute("Summary", "xxxx");
			root.setAttribute("Status", "Handling");
			root.setAttribute("IsRegister", "false");
			root.setAttribute("UserGroupId", "0");
			root.setAttribute("Level", "0");
			root.setAttribute("Longitude", "9999");
			root.setAttribute("Latitude", "9999");
			root.setAttribute("OrganizationID", "8");
			root.setAttribute("DetectedUnit", "-1");
			
//			var node = doc.createElement("Attach");
			
//			root.appendChild(node);
			return doc;
			
		},
		packageObject : function(row) {
			m_caseInfo_Object.receiveStatus = m_caseType;
		},
		/**
		 * 加载案件列表
		 */
		loadCaseList:function(){
			CasePushManage.packageObject();
			$('#casePushListGrid').datagrid({
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
				singleSelect:true,
				onClickRow:CasePushManage.setRowInfoBySelect,
				onDblClickRow : CasePushManage.showCaseInfo,
				toolbar : "#casePushTb",
				columns : [ [ 
				              { title : 'id', field : 'id', hidden : true },
				              { title : '分配状态', field : 'distributeStatus', align : 'center', width : 150,formatter:function(value,rowData,index){
				            	  if(value>0||value !="0"){
				            		  return "<a href='javascript:void(0);' style='text-decoration:none;' onclick=CasePushManage.showDistrubutInfo('"+rowData.id+"')>查看分配记录</a>";
				            	  }else{
				            		  return "未分配";
				            	  }
				              }},
				              { title : '案件编号', field : 'code', align : 'center', width : 150 },
				              { title : '案件名称', field : 'name', align : 'center', width : 150 },
				              { title : '案件类型', field : 'categoryName', align : 'center', width : 150 },
				              { title : '案件时间', field : 'startTime', align : 'center', width : 150 },
				              { title : '案件所属区域', field : 'detectedunitNname', align : 'center', width : 150 },
				              { title : '简要案情', field : 'summary', align : 'center', width : 150 }
				          ] ]
			});
		},
		showDistrubutInfo:function(caseId){

			$('#distributeRecGrid').datagrid({
				url : 'CaseOrgan/getCaseDistributeRecordList.do?caseId='+caseId, 
				fitColumns : true,
				rownumbers : true,
				pagination : false, 
				nowrap : false,  
				title : "案件分配记录",
				columns : [ [  
				              { title : '分配单位', field : 'organName', align : 'center', width : 150},
				              { title : '分配状态', field : 'status', align : 'center', width : 150,formatter:function(value,rowData,index){
				            	  return "已分配";
				              }},
				              { title : '分配时间', field : 'senderTime', align : 'center', width : 250 },
				              { title : '接收状态', field : 'receiveTime', align : 'center', width : 150,formatter:function(value,rowData,index){
				            	  if(rowData.receiveTime==undefined||rowData.receiveTime==""||rowData.receiveTime==null){
				            		  return "<span style='color:red'>未接收</span>";
				            	  }else{return "<span>已接收</span>";}
				              } }
				          ] ]
			});
			var s = art
			.dialog({
				id : 'dlgdistributeCase',
				title : '案件分配记录',
				height: 280,
				width: 490,
				content : document.getElementById("div_distributeInfo"),
				lock : true,
				initFn : function() {
					
				}
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
				var caseNo = 0;
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
				return;
			}
			var organIdArray = [];
			
			var count = 0;
			if(organNodes.length ==15){
				$.each(organNodes,function(index,item){
					if(index !=0){
						organIdArray[count++] = item.id;
					}
				});
			}else{
				$.each(organNodes,function(index,item){
					//将选中的分局和中队排除在外
					if(item.id>6){
						organIdArray[count++] = item.id;
					}
					
				});
			}
			/*修改案件接收状态*/
			if(organIdArray.length==0){
				$.messager.alert('操作提示', "请选择相关的派出所", "warning");
				return;
			}
			$.ajax('case/changeCaseReceiveStatusAndLevel.do',{
				type:'POST',
				data:{
					caseId:m_rowData.id,
					caseLevel:caseLevel
					},
				success:function(responce){
					
				}
			});
			/*将案件推送的派出所进行保存*/
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
			/*重新加载案件列表*/
			CasePushManage.loadCaseList();
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
		},
		exportCaseInfo:function(){
			if(m_rowIndex ==-1){
				$.messager.alert('操作提示', "请先选择案件", "warning");
				return;
			}
		/**
		 * 
		 */	
			$.ajax('Export/CaseInfoExportInExcel.do',{
				type:'POST',
				data:{caseId:m_rowData.id},
				success:function(responce){
					var obj = JSON.parse(responce);
					
					var fileURL=window.open ("\\"+"case/"+obj.data,"_blank","height=0,width=0,toolbar=no,menubar=no,scrollbars=no,resizable=on,location=no,status=no");
			        document.document.execCommand("SaveAs",'false',"\\"+"case/"+obj.data);
			        fileURL.window.close();
			        fileURL.close();
			        
					if(obj.isSuccess==true){
						$.messager.alert("提示","保存成功");
						CasePushManage.loadCaseList();
					}else{
						$.messager.alert("提示","导出失败","warning");
					}
				}
			});
		}		
};