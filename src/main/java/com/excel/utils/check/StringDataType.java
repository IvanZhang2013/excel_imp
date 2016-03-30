package com.excel.utils.check;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excel.dto.TableExcelColumn;

public class StringDataType {

	/**
	 * require =0 是为不是必填项
	 * require = 1 为必填项
	 */
	public static Map<String, String> excute(String value, TableExcelColumn tableExcelColumn) {
		StringBuffer stringBuffer = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		if (tableExcelColumn.getRequire().equals("1")) {
			if(value==null){
				value="";
			}
			if (value.trim().length() == 0) {
				stringBuffer.append(tableExcelColumn.getColumnNm() + "为必填项;");
				map.put("status", "0");
				map.put("error", stringBuffer.toString());
				return map;
			}
		}
		boolean tem = false;
		if (tableExcelColumn.getColumnRegex() != null) {
			Pattern pattern = Pattern.compile(tableExcelColumn.getColumnRegex());
			Matcher matcher = pattern.matcher(value);
			tem = matcher.matches();
			if (!tem) {
				stringBuffer.append(tableExcelColumn.getColumnNm() + tableExcelColumn.getColumnRemark() + ";");
				map.put("status", "0");
				map.put("error", stringBuffer.toString());
			}
		} else {
			map.put("status", "1");
			map.put("error", "");
		}

		return map;
	}

}
