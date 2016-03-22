package com.excel.utils.valuebean;

import java.util.List;
import java.util.Map;

import com.excel.dto.DataCheck;
import com.excel.dto.ImpOptions;

public class DataBean {

	// 存放列名和数据值
	private Map<String, String> valueMap;
	// 存放导入表信息和列明信息
	private ImpOptions impOptions;

	private List<DataCheck> checkList;

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}

	public ImpOptions getImpOptions() {
		return impOptions;
	}

	public void setImpOptions(ImpOptions impOptions) {
		this.impOptions = impOptions;
	}

	public List<DataCheck> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DataCheck> checkList) {
		this.checkList = checkList;
	}

}
