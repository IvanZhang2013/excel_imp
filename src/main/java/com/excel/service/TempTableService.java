package com.excel.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.excel.entity.TempTable;
import com.excel.models.TempTableDao;

@Service
public class TempTableService {

	@Resource
	private TempTableDao tempTableDao;

	public void saveMapLocation(TempTable tempTable) {
		tempTableDao.save(tempTable);
	}

	public List<TempTable> findAllUsers() {
		return (List<TempTable>) tempTableDao.findAll();
	}
}
