package com.excel.models;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excel.entity.TableColumns;

@Repository
public interface TableColumnsDao extends CrudRepository<TableColumns, String>,
		PagingAndSortingRepository<TableColumns, String>, JpaSpecificationExecutor<TableColumns> {

	@Query("from TableColumns a where a.tableNameId = :tableNameId")
	public List<TableColumns> findAllTableName(@Param("tableNameId") String tableNameId);
}