var m_organId;
var m_organType;

$(function() { 
	var obj = getUrlArgs();
	m_organId = obj.organId;
	m_organType = obj.organType;
	if(m_organType ==1){
		$("#selectOrgan").attr("style","display:none");
	} 
	caseTJManage.loadcaseTJInfo();
});
var caseTJManage = {
		loadcaseTJInfo:function(){
			$("#caseTJListGrid").datagrid({
				url : 'CaseOrgan/getCaseTJInfo.do',
				queryParams:{organId:m_organId,startTime:'',endTime:''},
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				idField : 'id',  
				toolbar : '#caseBackAttchMentsTb',  
				columns : [ [ 
				              
				              { title : '未接收', field : 'notReceivedCaseCount', align : 'center', width : 150 },
				              { title : '已接收', field : 'receivedCaseCount', align :'center', width : 150},
				             
				              { title : '未反馈', field : 'notFeedBackCaseCount', align : 'center', width : 150},
				              { title : '已反馈', field : 'feedBackCaseCount', align : 'center', width : 150 }
				          ] ]
			});
		},
		doSearch:function(){
			var organId = $("#selectOrganTree").combotree("getValue");
			if(organId == undefined||organId == ""){
				organId = m_organId;
			}
			var startTime = $("#sch_startTime").datebox("getValue");
			var endTime = $("#sch_endTime").datebox("getValue");
			$("#caseTJListGrid").datagrid("reload",{"organId":organId,"startTime":startTime,"endTime":endTime});
		}
};