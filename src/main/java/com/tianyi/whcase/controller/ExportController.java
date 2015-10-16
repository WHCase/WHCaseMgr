package com.tianyi.whcase.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianyi.whcase.core.ListResult;
import com.tianyi.whcase.core.Result;
import com.tianyi.whcase.model.CaseAttachItem;
import com.tianyi.whcase.model.CaseGroup;
import com.tianyi.whcase.service.CaseAttchService;
import com.tianyi.whcase.service.CaseGroupService;
import com.tianyi.whcase.service.CaseService;
import com.tianyi.whcase.viewmodel.CaseVM;


@Controller
@RequestMapping("/Export")
public class ExportController {
	@Autowired CaseService caseService;
	@Autowired CaseAttchService caseAttchService;
	@Autowired CaseGroupService caseGroupService;
	
	@RequestMapping(value = "CaseInfoExportInExcel.do")
	public @ResponseBody String CaseInfoExportInWord(
			@RequestParam(value = "caseId", required = false) String caseId,
		HttpServletRequest request)throws Exception{
		/**
		 * 获取数据
		 */
		try {
			CaseVM caseInfo = caseService.getCaseMainInfo(caseId);
			
			ListResult<CaseAttachItem> caseAttachList= caseAttchService.getCaseRelativeByCaseId(caseId,1);
			List<CaseAttachItem> attachList = null;
			if(caseAttachList !=null){
				attachList = caseAttachList.getRows();
			}
			
			ListResult<CaseGroup> caseGroupList= caseGroupService.getCaseRelativeByCaseId(caseId);
			List<CaseGroup> groupList = null;
			if(caseGroupList !=null){
				groupList = caseGroupList.getRows();
			}
			
			String temp = createCaseExcel(caseInfo,groupList,attachList);
			if(temp.equals("1")||temp.equals("2")){
				Result<String> result = new Result<String>(null,false,"下载案件信息失败");
				return result.toJson();
			}
			return new Result<String>(temp,true,"下载案件成功").toJson();
		} catch (Exception ex) {
			Result<CaseVM> result = new Result<CaseVM>(null, false, false, false,
					"查询失败");
			return result.toJson();
		}
		
	}
	private String createCaseExcel(CaseVM caseInfo,List<CaseGroup> groupList,List<CaseAttachItem> attachList) {
		String filePath = "";
		
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			
			if (workbook != null) {			
				
				Sheet sheet = workbook.createSheet("案件相关信息");
				sheet.setVerticallyCenter(true);
				
				Row row0 = sheet.createRow(0);
				
				String title = "案件主体信息";
				
	
				Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
				cell_0.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
				
				/**
				 * row1
				 */
				Row row1 = sheet.createRow(1);
				Cell cell_row1_1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row1_1.setCellValue("案件编号");
				sheet.autoSizeColumn(1);
	
				Cell cell_row1_2 = row1.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row1_2.setCellValue(caseInfo.getCode());
				sheet.autoSizeColumn(1);
				
				Cell cell_row1_3 = row1.createCell(2, Cell.CELL_TYPE_STRING);
				cell_row1_3.setCellValue("案件名称");
				sheet.autoSizeColumn(1);
	
				Cell cell_row1_4 = row1.createCell(3, Cell.CELL_TYPE_STRING);
				cell_row1_4.setCellValue(caseInfo.getName());
				sheet.autoSizeColumn(1);
				/**
				 * row2
				 */
				Row row2 = sheet.createRow(2);
				Cell cell_row2_1 = row2.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row2_1.setCellValue("案件类型");
				sheet.autoSizeColumn(1);
	
				Cell cell_row2_2 = row2.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row2_2.setCellValue(caseInfo.getCategoryName());
				sheet.autoSizeColumn(1);
				
				Cell cell_row2_3 = row2.createCell(2, Cell.CELL_TYPE_STRING);
				cell_row2_3.setCellValue("案发时间");
				sheet.autoSizeColumn(1);
	
				Cell cell_row2_4 = row2.createCell(3, Cell.CELL_TYPE_STRING);
				cell_row2_4.setCellValue(caseInfo.getStartTime().toString());
				sheet.autoSizeColumn(1);
				/**
				 * row3
				 */
				Row row3 = sheet.createRow(3);
				Cell cell_row3_1 = row3.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row3_1.setCellValue("案发地域");
				sheet.autoSizeColumn(1);
	
				Cell cell_row3_2 = row3.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row3_2.setCellValue(caseInfo.getOrganizationame());
				sheet.autoSizeColumn(1);
				/**
				 * row4
				 */
				Row row4 = sheet.createRow(4);
				Cell cell_row4_1 = row4.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row4_1.setCellValue("简要案情");
				sheet.autoSizeColumn(1);
	
				Cell cell_row4_2 = row4.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row4_2.setCellValue(caseInfo.getSummary());
				sheet.autoSizeColumn(1);
				/**
				 * row5
				 */
				Row row5 = sheet.createRow(5);
				Cell cell_row5_1 = row5.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row5_1.setCellValue("案件状态");
				sheet.autoSizeColumn(1);
	
				Cell cell_row5_2 = row5.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row5_2.setCellValue(caseInfo.getStatus());
				sheet.autoSizeColumn(1);
				
				Cell cell_row5_3 = row5.createCell(2, Cell.CELL_TYPE_STRING);
				cell_row5_3.setCellValue("破案单位");
				sheet.autoSizeColumn(1);
	
				Cell cell_row5_4 = row5.createCell(3, Cell.CELL_TYPE_STRING);
				cell_row5_4.setCellValue(caseInfo.getDetectedunitNname());
				sheet.autoSizeColumn(1);
				/**
				 * row6
				 */
				Row row6 = sheet.createRow(6);
				Cell cell_row6_1 = row6.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row6_1.setCellValue("录入单位");
				sheet.autoSizeColumn(1);
	
				Cell cell_row6_2 = row6.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row6_2.setCellValue(caseInfo.getOrganizationame());
				sheet.autoSizeColumn(1);
				
				Cell cell_row6_3 = row6.createCell(2, Cell.CELL_TYPE_STRING);
				cell_row6_3.setCellValue("录入人员");
				sheet.autoSizeColumn(1);
	
				Cell cell_row6_4 = row6.createCell(3, Cell.CELL_TYPE_STRING);
				cell_row6_4.setCellValue(caseInfo.getCreator());
				sheet.autoSizeColumn(1);
				/**
				 * sheetG串并案
				 */
				Sheet sheetG = workbook.createSheet("案件串并案件");
				
				String titleG = "案件串并案件";
				Row rowG0 = sheetG.createRow(0);
				sheetG.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
				Cell G_cell_0 = rowG0.createCell(0, Cell.CELL_TYPE_STRING);
				G_cell_0.setCellValue(titleG);
				
				Row G_row_1 = sheetG.createRow(1);
				Cell G_cell_10 = G_row_1.createCell(0,Cell.CELL_TYPE_STRING);
				G_cell_10.setCellValue("串案编号");
				
				Cell G_cell_11 = G_row_1.createCell(1,Cell.CELL_TYPE_STRING);
				G_cell_11.setCellValue("串案名称");
				
				Cell G_cell_12 = G_row_1.createCell(2,Cell.CELL_TYPE_STRING);
				G_cell_12.setCellValue("创建时间");
				
				Cell G_cell_13 = G_row_1.createCell(3,Cell.CELL_TYPE_STRING);
				G_cell_13.setCellValue("负责人");
				
				Cell G_cell_14 = G_row_1.createCell(4,Cell.CELL_TYPE_STRING);
				G_cell_14.setCellValue("审核状态");
			
				if(groupList!=null){
					for(int i = 0;i<groupList.size();i++){
						
						Row G_row_new = sheetG.createRow(i+2);
						Cell G_cell_N0 = G_row_new.createCell(0,Cell.CELL_TYPE_STRING);
						G_cell_N0.setCellValue(groupList.get(i).getCode());
						
						Cell G_cell_N1 = G_row_new.createCell(1,Cell.CELL_TYPE_STRING);
						G_cell_N1.setCellValue(groupList.get(i).getName());
						
						Cell G_cell_N2 = G_row_new.createCell(2,Cell.CELL_TYPE_STRING);
						G_cell_N2.setCellValue(groupList.get(i).getCreateTime().toString());
						
						Cell G_cell_N3 = G_row_new.createCell(3,Cell.CELL_TYPE_STRING);
						G_cell_N3.setCellValue(groupList.get(i).getContactPerson());
						
						Cell G_cell_N4 = G_row_new.createCell(4,Cell.CELL_TYPE_STRING);
						G_cell_N4.setCellValue(groupList.get(i).getAuditstate());
						
					}
				}
				/**
				 * sheetA附件
				 */
				Sheet sheetA = workbook.createSheet("案件附件信息");
				
				String titleA = "案件附件信息";
				Row rowA0 = sheetA.createRow(0);
				
				sheetA.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));	
				
				Cell A_cell_0 = rowA0.createCell(0, Cell.CELL_TYPE_STRING);
				A_cell_0.setCellValue(titleA);
				
				Row A_row_1 = sheetG.createRow(1);
				Cell A_cell_10 = A_row_1.createCell(0,Cell.CELL_TYPE_STRING);
				A_cell_10.setCellValue("附件名称");
				
				Cell A_cell_11 = A_row_1.createCell(1,Cell.CELL_TYPE_STRING);
				A_cell_11.setCellValue("附件类型");
				
				Cell A_cell_12 = A_row_1.createCell(2,Cell.CELL_TYPE_STRING);
				A_cell_12.setCellValue("链接");
				
				if(attachList!=null){
					for(int i = 0;i<attachList.size();i++){
						
						Row A_row_N = sheetG.createRow(i+2);
						Cell A_cell_N0 = A_row_N.createCell(0,Cell.CELL_TYPE_STRING);
						A_cell_N0.setCellValue(attachList.get(i).getName());
						
						Cell A_cell_N1 = A_row_N.createCell(1,Cell.CELL_TYPE_STRING);
						A_cell_N1.setCellValue(attachList.get(i).getItemType());
						
						Cell A_cell_N2 = A_row_N.createCell(2,Cell.CELL_TYPE_STRING);
						A_cell_N2.setCellValue(attachList.get(i).getUri());
						
					}
				}
			}
				
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			java.util.Date date = new java.util.Date();
			String str = sdf.format(date);
			filePath = "data/tempFile/" + str + "_caseData.xls";
			String serverPath = getClass().getResource("/").getFile()
					.toString();
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			
			//String realPath = serverPath + filePath;
			String realPath ="E:/data/tempFile/" + str + "_caseData.xls"; 
			try {
				FileOutputStream outputStream = new FileOutputStream(
						realPath);
				workbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
			}catch (Exception e) {
				return "1";
			}
		}catch(Exception ex) {
			return "2";
		}
		return filePath;
	}
	
}
