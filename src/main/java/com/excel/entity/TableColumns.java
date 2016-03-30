package com.excel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table_columns")
public class TableColumns implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8586228311611563160L;

	@Id
	@Column(name = "column_id")
	private Integer columnId;

	@Column(name = "column_nm", length = 20)
	private String columnNm;

	@Column(name = "column_regex", length = 20)
	private String columnRegex;

	@Column(name = "column_remark", length = 20)
	private String columnRemark;

	@Column(name = "column_code", length = 20)
	private String columnCode;

	@Column(name = "require", length = 20)
	private String require;

	@Column(name = "table_name_id", length = 20)
	private String tableNameId;

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public String getColumnNm() {
		return columnNm;
	}

	public void setColumnNm(String columnNm) {
		this.columnNm = columnNm;
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

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getRequire() {
		return require;
	}

	public void setRequire(String require) {
		this.require = require;
	}

	public String getTableNameId() {
		return tableNameId;
	}

	public void setTableNameId(String tableNameId) {
		this.tableNameId = tableNameId;
	}

}