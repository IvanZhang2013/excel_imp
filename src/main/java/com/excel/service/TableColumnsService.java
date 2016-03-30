package com.excel.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.excel.entity.TableColumns;
import com.excel.models.TableColumnsDao;

/**
 * User业务层，依赖持久层 IUserDao
 * 
 * @author liuyt
 * @date 2014-10-30 下午2:37:21
 */
@Service
public class TableColumnsService {

	@Resource
	private TableColumnsDao tableColumnsDao;

	public List<TableColumns> findAllTableName(String tableNameId) {
		return tableColumnsDao.findAllTableName(tableNameId);
	}
}
