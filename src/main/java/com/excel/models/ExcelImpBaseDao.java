package com.excel.models;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.excel.entity.ExcelImpBase;

/**
 * 持久层接口
 */
@Transactional
@SuppressWarnings("unchecked")
public interface ExcelImpBaseDao extends CrudRepository<ExcelImpBase, String> {

	public ExcelImpBase save(ExcelImpBase accountInfo);

	@Query("from ExcelImpBase a where a.appId = :appId")
	public List<ExcelImpBase> findAllAppId(@Param("appId") String appId);
	
	@Query("from ExcelImpBase a where a.appId = :appId and temp_table_id=:temp_table_id")
	public List<ExcelImpBase> findPro(@Param("appId") String appId ,@Param("temp_table_id")String tableId);

}