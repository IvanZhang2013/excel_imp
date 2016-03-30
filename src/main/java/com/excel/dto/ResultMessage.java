package com.excel.dto;

public class ResultMessage {
	/**
	 * 返回提示信息
	 */
	private String message;
	/**
	 * 是否完成请求 0为未完成 1、为完成请求
	 */
	private String status = "0";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
