package com.excel.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.excel.entity.ExcelImpBase;
import com.excel.models.ExcelImpBaseDao;


@Service
public class ExcelImpBaseService {
	
	@Resource
	private ExcelImpBaseDao excelImpBaseDao;
	
	public List<ExcelImpBase> findAllUsers() {
		return (List<ExcelImpBase>) excelImpBaseDao.findAll();
	}

}
