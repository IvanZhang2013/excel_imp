package com.excel.dto;

public class TableExcelColumn {
	private String columnCode;
	private String columnRegex;
	private String columnRemark;
	private String columnNm;
	private String require;
	private String excelIndex;

	public String getColumnNm() {
		return columnNm;
	}

	public void setColumnNm(String columnNm) {
		this.columnNm = columnNm;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnRegex() {
		return columnRegex;
	}

	public void setColumnRegex(String columnRegex) {
		this.columnRegex = columnRegex;
	}

	public String getColumnRemark() {
		return columnRemark;
	}

	public void setColumnRemark(String columnRemark) {
		this.columnRemark = columnRemark;
	}

	public String getRequire() {
		return require;
	}

	public void setRequire(String require) {
		this.require = require;
	}

	public String getExcelIndex() {
		return excelIndex;
	}

	public void setExcelIndex(String excelIndex) {
		this.excelIndex = excelIndex;
	}

}
