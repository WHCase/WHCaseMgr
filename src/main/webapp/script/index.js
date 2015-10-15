var m_index_organTye;   

$(function() {
	var obj = getUrlArgs();
	m_index_organTye = obj.organType; 
	var url = "";
	if(m_index_organTye==0||m_index_organTye=="0"){
		url = "data/policeofficeMenu.json";
		var obj = {};
		obj.text = "案件分配";
		onTreeMenuDblClick(obj);
	}else{
		url = "data/policestationMenu.json";
		var obj = {};
		obj.text = "案件接收";
		onTreeMenuDblClick(obj);
	}
	$('#treeMenu').tree({
		checkbox : false, 
		url:url,
		onClick : onTreeMenuDblClick
	}); 
});

/**
 * 点击树菜单操作功能页面
 */
function onTreeMenuDblClick(row) {
	var src = null;  
	switch (row.text) {
	case "案件分配":
		src = "view/policeoffice/casedistributeList.jsp?caseType=1";//未分配
		break;
	case "案件查看":
		src = "view/policeoffice/casedistributeList.jsp?caseType=0";//除去"未分配"的所有案件
		break;
	case "案件接收":
		src = "view/policestation/casereceiveList.jsp?caseType=2&organId="+m_index_organTye;//未接收
		break; 
	case "案件反馈":
		src = "view/policestation/casereceiveList.jsp?caseType=4&organId="+m_index_organTye;//已接收
		break; 
	}
	$("#ifrContent").attr("src", src); 
}
function iframeSize() {
	var ifm = document.getElementById("ifrContent");
	var subWeb = document.frames ? document.frames["ifrContent"].document
			: ifm.contentDocument;
	if (ifm != null && subWeb != null) {
		ifm.height = subWeb.body.scrollHeight;
		ifm.width = subWeb.body.scrollWidth;
	}
}