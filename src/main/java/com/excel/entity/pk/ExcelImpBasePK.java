package com.excel.entity.pk;

import java.io.Serializable;

public class ExcelImpBasePK implements Serializable {

	private static final long serialVersionUID = -5200398420669632352L;

	private String appId;

	private String tempTableId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTempTableId() {
		return tempTableId;
	}

	public void setTempTableId(String tempTableId) {
		this.tempTableId = tempTableId;
	}

}