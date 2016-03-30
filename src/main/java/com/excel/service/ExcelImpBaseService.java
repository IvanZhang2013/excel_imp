package com.excel.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.excel.entity.ExcelImpBase;
import com.excel.models.ExcelImpBaseDao;
import com.excel.utils.excel.read.ExcelReader;

@Repository("excelImpBaseService")
public class ExcelImpBaseService {

	@Resource
	private ExcelImpBaseDao excelImpBaseDao;

	@Resource
	private ExcelReader excelReader;

	public List<ExcelImpBase> findAllUsers() {
		return (List<ExcelImpBase>) excelImpBaseDao.findAll();
	}

	/**
	 * 根据应用ID 查找导入表
	 * 
	 * @throws Exception
	 */
	public List<ExcelImpBase> baseInfoData(String appId) throws Exception {
		List<ExcelImpBase> excelImpBases = excelImpBaseDao.findAllAppId(appId);
		return excelImpBases;
		
	}
}
