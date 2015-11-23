var m_index_organTye;
var m_index_organId;

$(function() {
	//获取案件类型
	$.ajax('JieShang/getGetDictionary.do',{
		type:'POST',
		success:function(responce){
			//var res = JSON.parse(responce);
			if(responce.isSuccess ==true){
				
			}else{
				$.messager.alert('获取数据', responce.msg, "warning");
			}
		}
	});
	//获取机构
	$.ajax('JieShang/GetAllOrganizations.do',{
		type:'GET',
		success:function(responce){
			//var res = JSON.parse(responce);
			if(responce.isSuccess ==true){
				
			}else{
				$.messager.alert('获取数据', responce.msg, "warning");
			}
		}
	});
	var obj = getUrlArgs();
	m_index_organTye = obj.organType;
	m_index_organId = obj.organId;
	if(m_index_organTye ==0){
		$("#labOrgName").text("市局");
	}else{
		$.ajax('Organ/getOrganNameById.do',{
			type:'POST',
			data:{'organId':m_index_organId},
			success:function(responce){
				if(responce.isSuccess==true){
					$("#labOrgName").text(responce.data.name);
				}else{
					$("#labOrgName").text(responce.msg);
				}
			}
		});
	}
	
	var url = "";
	if(m_index_organTye==0||m_index_organTye=="0"){
		url = "data/policeofficeMenu.json";
		var f = {};
		f.text = "案件分配";
		onTreeMenuDblClick(f);
	}else{
		url = "data/policestationMenu.json";
		var f = {};
		f.text = "案件接收";
		onTreeMenuDblClick(f);
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
		src = "view/policestation/casereceiveList.jsp?caseType=2&organId="+m_index_organId;//未接收
		break; 
	case "案件反馈":
		src = "view/policestation/casereceiveList.jsp?caseType=4&organId="+m_index_organId;//已接收
		break;
	case "案件统计":
		src = "view/policestation/caseTJ.jsp?organType="+m_index_organTye+"&organId="+m_index_organId;//派出所
		break;
	case "案件统计详情":
		src = "view/policeoffice/caseTJ.jsp?organType="+m_index_organTye+"&organId="+m_index_organId;//分局
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