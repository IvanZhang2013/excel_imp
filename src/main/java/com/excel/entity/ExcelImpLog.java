package com.excel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "imp_log")
public class ExcelImpLog implements Serializable {

	private static final long serialVersionUID = -6594630833024506084L;

	@Id
	@Column(name = "imp_date", length = 20)
	private String impData;

	@Column(name = "imp_user_id", length = 20)
	private String impUserId;

	@Column(name = "imp_app_id", length = 20)
	private String impAppId;

	@Column(name = "imp_status", length = 20)
	private String impStatus;

	public String getImpData() {
		return impData;
	}

	public void setImpData(String impData) {
		this.impData = impData;
	}

	public String getImpUserId() {
		return impUserId;
	}

	public String getImpAppId() {
		return impAppId;
	}

	public void setImpUserId(String impUserId) {
		this.impUserId = impUserId;
	}

	public void setImpAppId(String impAppId) {
		this.impAppId = impAppId;
	}

	public String getImpStatus() {
		return impStatus;
	}

	public void setImpStatus(String impStatus) {
		this.impStatus = impStatus;
	}
}