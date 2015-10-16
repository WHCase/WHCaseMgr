
var m_organId;

$(function() { 
	var obj = getUrlArgs();
	m_organId = obj.organId;
	
	caseTJManage.loadcaseTJInfo();
});
var caseTJManage = {
		loadcaseTJInfo:function(){
			$("#caseTJListGrid").datagrid({
				url : 'case/getCaseTJInfo.do',
				rownumbers : true,
				pagination : false, 
				nowrap : false,
				title:'分局案件统计汇总',
				idField : 'id',  
				toolbar : '#caseBackAttchMentsTb',  
				columns : [ [ { title :'案件总量', field :'caseTotalCount', align :'center', width : 150},
				              { title : '已分配', field :'distributedCaseCount', align :'center', width : 150},
				              { title : '未分配', field :'notDistributeCaseCount', align :'center', width : 150},
				              { title : '已接收', field : 'receivedCaseCount', align :'center', width : 150},
				              { title : '未接收', field : 'notReceivedCaseCount', align : 'center', width : 150 },
				              { title : '已反馈', field : 'feedBackCaseCount', align : 'center', width : 150 },
				              { title : '未反馈', field : 'notFeedBackCaseCount', align : 'center', width : 150}
				          ] ]
			});
		}
};