package com.excel.dto;

import java.util.List;

public class PageResult {

	private String total;
	private List<?> rows;



	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
