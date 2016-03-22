package com.excel.utils.type;

public enum ExcelType {

	EXCEL_XLS("xls"), EXCEL_XLSX("xlsx");

	private String xlsType;

	ExcelType(String xlsType) {
		this.xlsType = xlsType;
	}

	public String toString(){
		return xlsType;
	}
}
