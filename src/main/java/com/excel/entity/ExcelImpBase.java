package com.excel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.excel.entity.pk.ExcelImpBasePK;

@Entity
@IdClass(ExcelImpBasePK.class)
@Table(name = "excel_imp_base")
public class ExcelImpBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 67533406461338270L;

	@Id
	@Column(name = "app_id")
	private String appId;

	@Column(name = "app_nm")
	private String appName;

	@Column(name = "produce_nm")
	private String produce;
	@Id
	@Column(name = "temp_table_id")
	private String tempTableId;

	public ExcelImpBase() {

	}

	public ExcelImpBase(String appId, String appName, String produce, String tempTableId) {
		this.appId = appId;
		this.appName = appName;
		this.produce = produce;
		this.tempTableId = tempTableId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getProduce() {
		return produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}

	public String getTempTableId() {
		return tempTableId;
	}

	public void setTempTableId(String tempTableId) {
		this.tempTableId = tempTableId;
	}

}