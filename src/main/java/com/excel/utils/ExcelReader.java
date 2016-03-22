package com.excel.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;

import com.excel.dto.ColumnInfo;
@SuppressWarnings("unused")
public class ExcelReader {
	
	private Workbook wb;
	private Sheet sheet;
	private Row row;

	public ExcelReader(Workbook wb) {
		this.wb = wb;
	}

	public List<Map<String, String>> readExcel(Workbook wb, List<ColumnInfo> columnList, String tableCode) {
		//取得第一页
		Sheet sheet = wb.getSheetAt(0);
		int rowSize = sheet.getLastRowNum()-1;
		List<Map<String,String>> valueMap = new ArrayList<Map<String,String>>(rowSize);
		//计算所有的数据
		int[] sd = new int[10];
		String [] sb  = new String[20];
		for (int i = 0; i < columnList.size(); i++) {
			sd[i] = columnList.get(i).getColIndex();
			sb[i] = columnList.get(i).getTableCode();
		}
		
		int columnSize = sheet.getRow(1).getPhysicalNumberOfCells();
		for (int i = 0; i <= rowSize - 1; i++) {
			row = sheet.getRow(i + 2);
			int j = 0;
			Map<String ,String> map  = new HashMap<String ,String>();
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
