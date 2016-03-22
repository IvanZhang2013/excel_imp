package com.excel.dto;

import java.util.List;

public class ImpOptions {
	// 表中文名
	private String tableNm;
	// 表数据库名称
	private String tableCode;
	// 模版列和表列对应关系
	private List<ColumnInfo> columns;

	public String getTableNm() {
		return tableNm;
	}

	public void setTableNm(String tableNm) {
		this.tableNm = tableNm;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

}
