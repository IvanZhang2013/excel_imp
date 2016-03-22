package com.excel.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.excel.entity.ExcelImpLog;
import com.excel.models.ExcelImpLogDao;

@Service
public class ExcelImpLogService {

	@Resource
	private ExcelImpLogDao excelImpLogDao;

	public List<ExcelImpLog> findAllUsers() {
		return (List<ExcelImpLog>) excelImpLogDao.findAll();
	}

}
