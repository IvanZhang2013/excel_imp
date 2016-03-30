package com.excel.dto;

import java.io.Serializable;
import java.util.List;

public class TableExcelOption implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2346422222548861135L;
	private String tableId;
	private List<TableExcelColumn> data;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public List<TableExcelColumn> getData() {
		return data;
	}

	public void setData(List<TableExcelColumn> data) {
		this.data = data;
	}

}
