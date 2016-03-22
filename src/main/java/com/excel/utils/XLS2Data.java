package com.excel.utils;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取xls文件的工具类
 */
public class XLS2Data {

	private Workbook xlsWorkBook;

	public static Logger logger = LoggerFactory.getLogger(XLS2Data.class);

	public List<Map<String, String>> excute(String xpath, String DataBean) throws Exception {
		xlsWorkBook = new HSSFWorkbook(new FileInputStream(xpath));
		ExcelReader excelReader = new ExcelReader(xlsWorkBook);
		excelReader.readExcel(xlsWorkBook, null, "XMC:");
		return null;
	}

	public Workbook getXlsWorkBook() {
		return xlsWorkBook;
	}

	public void setXlsWorkBook(Workbook xlsWorkBook) {
		this.xlsWorkBook = xlsWorkBook;
	}

}
