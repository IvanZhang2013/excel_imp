package com.excel.utils.excel.read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.excel.dto.CellNameData;
import com.excel.dto.ColumnInfo;
import com.excel.dto.SheetNameData;
import com.excel.dto.TableExcelColumn;
import com.excel.dto.TableExcelOption;
import com.excel.utils.JpaSqlRepository;
import com.excel.utils.check.StringDataType;

@SuppressWarnings("unused")
@Repository(value = "excelReader")
public class ExcelReader {

	@Resource
	private JpaSqlRepository jpaSqlRepository;

	@SuppressWarnings("resource")
	public HashMap<String, Object> SheetName(String filePath, int startrow) throws Exception {
		Workbook xlsWorkBook = new HSSFWorkbook(new FileInputStream(filePath));
		int sheetIndex = xlsWorkBook.getNumberOfSheets();
		List<Object> listSheet = new ArrayList<Object>();
		for (int i = 0; i < sheetIndex; i++) {
			String sheetName = xlsWorkBook.getSheetAt(i).getSheetName();
			SheetNameData sheetNameData = new SheetNameData();
			sheetNameData.setIndex(i);
			sheetNameData.setSheetName(sheetName);
			listSheet.add(sheetNameData);
		}

		Sheet sheet = xlsWorkBook.getSheetAt(0);
		int rowSize = sheet.getLastRowNum() - 1;
		Row row = sheet.getRow(startrow - 1);
		int cellNum = row.getPhysicalNumberOfCells();
		List<Object> listCell = new ArrayList<Object>();
		for (int i = 0; i < cellNum; i++) {
			CellNameData cellNameData = new CellNameData();
			cellNameData.setIndex(i);
			cellNameData.setCellName(getCellFormatValue(row.getCell(i)).trim());
			listCell.add(cellNameData);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("SheetList", listSheet);
		map.put("CellList", listCell);
		return map;
	}

	public void impTempTable(Workbook wb, int startrow, int improw, TableExcelOption tableExcelOption,String sessionId)
			throws Exception {

		Sheet sheet = wb.getSheetAt(0);
		int rowSize = sheet.getLastRowNum() - 1;
		List<TableExcelColumn> lists = tableExcelOption.getData();
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBuffer2 = new StringBuffer();
		int columnSize = sheet.getRow(startrow - 1).getPhysicalNumberOfCells();
		StringBuffer deleteTable = new StringBuffer();
		deleteTable.append("delete from  "+tableExcelOption.getTableId() +" where session_id = '"+sessionId+"'");
		jpaSqlRepository.updateSql(deleteTable.toString());
		stringBuffer.append("  insert into   " + tableExcelOption.getTableId() + " ( ");
		for (int j = 0; j < lists.size(); j++) {
			TableExcelColumn tableExcelColumn = lists.get(j);
			if (j == lists.size() - 1) {
				stringBuffer.append(" " + tableExcelColumn.getColumnCode() + " , session_id,excel_status,excel_remark ,row_num )values ");
			} else {
				stringBuffer.append(" " + tableExcelColumn.getColumnCode() + ", ");
			}
		}
		StringBuffer  error = new StringBuffer();
		for (int i = improw - 1; i <= improw +rowSize - 2; i++) {
			Row row = sheet.getRow(i);
			String excel_status="1";
			String excel_remark="0";
			for (int j = 0; j < lists.size(); j++) {
				TableExcelColumn tableExcelColumn = lists.get(j);
				String value = getCellFormatValue(row.getCell(Integer.valueOf(tableExcelColumn.getExcelIndex()))).trim();
				/**
				 * 进行数据校验  是否必填 和正则表达式
				 * */
				Map<String,String> map = StringDataType.excute(value, tableExcelColumn);
				if(map.get("status").equals("0")){
					excel_status="0";
					error.append(map.get("error"));
				}
				if(map.get("status").equals("1")&&excel_status.equals("1") ){
					excel_status="1";
				}
				
				if (j == 0 && (i == (improw - 1) || (i - improw + 1) % 100 == 0)) {
					stringBuffer2.append("( ");
				} else if (j == 0 && i != (improw - 1) && (i - improw + 1) % 100 != 0) {
					stringBuffer2.append(" ,( ");
				}

				if (j == lists.size() - 1) {
					stringBuffer2.append("  '" + value + "'  ,'"+ sessionId+"','"+ excel_status+"' ,'"+ error.toString()+"','"+i+"') ");
					error.delete(0, error.length());
				} else {
					stringBuffer2.append(" '" + value + "' ,");
				}
			}
			try {
				if ((i - improw + 2) % 100 == 0) {
					jpaSqlRepository.updateSql(stringBuffer2.insert(0, stringBuffer.toString()).toString());
					stringBuffer2.delete(0, stringBuffer2.length());
				} else if (i == improw +rowSize - 2) {
					jpaSqlRepository.updateSql(stringBuffer2.insert(0, stringBuffer.toString()).toString());
					stringBuffer2.delete(0, stringBuffer2.length());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		}
	}

	public List<Map<String, String>> readExcel(Workbook wb, List<ColumnInfo> columnList, String tableCode) {
		// 取得第一页
		Sheet sheet = wb.getSheetAt(0);
		int rowSize = sheet.getLastRowNum() - 1;
		List<Map<String, String>> valueMap = new ArrayList<Map<String, String>>(rowSize);
		// 计算所有的数据
		int[] sd = new int[10];
		String[] sb = new String[20];
		for (int i = 0; i < columnList.size(); i++) {
			sd[i] = columnList.get(i).getColIndex();
			sb[i] = columnList.get(i).getTableCode();
		}

		int columnSize = sheet.getRow(1).getPhysicalNumberOfCells();
		for (int i = 0; i <= rowSize - 1; i++) {
			Row row = sheet.getRow(i + 2);
			int j = 0;
			Map<String, String> map = new HashMap<String, String>();
			while (j < columnSize) {
				if (row == null) {
					map.put("1", null);
				} else {
					map.put("2", getCellFormatValue(row.getCell((short) j)).trim());
				}
				j++;
			}
		}

		return null;
	}

	private String getStringCellValue(Cell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}

	private String getDateCellValue(Cell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == Cell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (new DateTime(date).toString("yyyy-MM-dd"));
			} else if (cellType == Cell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == Cell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
			case Cell.CELL_TYPE_FORMULA: {
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				} else {
					cellvalue = this.doubleToString(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	public String doubleToString(double d) {
		String i = DecimalFormat.getInstance().format(d);
		String result = i.replaceAll(",", "");
		return result;

	}

}
