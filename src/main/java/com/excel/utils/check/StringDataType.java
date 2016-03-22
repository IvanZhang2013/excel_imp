package com.excel.utils.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDataType {

	public String excute(String value, String regex, String message) {
		StringBuffer stringBuffer = new StringBuffer();

		boolean tem = false;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		tem = matcher.matches();
		if (tem) {
			stringBuffer.append(message);
		}
		return stringBuffer.toString();
	}

}
