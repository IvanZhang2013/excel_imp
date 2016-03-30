package com.excel.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excel.dto.PageResult;
import com.excel.dto.TableExcelColumn;
import com.excel.dto.TableExcelOption;
import com.excel.entity.ExcelImpBase;
import com.excel.models.ExcelImpBaseDao;
import com.excel.utils.check.StringDataType;

@Repository
@SuppressWarnings("unchecked")
public class JpaSqlRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private ExcelImpBaseDao excelImpBaseDao;

	@Transactional
	public int updateSql(String sql) {
		Query query = entityManager.createNativeQuery(sql);
		int s = query.executeUpdate();
		return s;
	}

	/**
	 * 查询所有校验异常的数据，或者查询全部数据 type 为true 表示全查找 type 为false表示部分查找
	 */
	public PageResult selectSql(boolean type, TableExcelOption tableExcelOption, int page, int rows ,String session_id) {
		StringBuffer st = new StringBuffer();
		StringBuffer sttotal = new StringBuffer();
		String tableName = tableExcelOption.getTableId();
		List<TableExcelColumn> tableExcelColumns = tableExcelOption.getData();
		String[] colNm = new String[(5 + tableExcelColumns.size())];
		st.append("select row_num ,excel_status ,excel_remark,produce_status ,produce_remark ");
		sttotal.append("select count(*) ");
		colNm[0] = "row_num";
		colNm[1] = "excel_status";
		colNm[2] = "excel_remark";
		colNm[3] = "produce_status";
		colNm[4] = "produce_remark";
		for (int i = 0; i < tableExcelColumns.size(); i++) {
			st.append("  ," + tableExcelColumns.get(i).getColumnCode() + " ");
			colNm[5 + i] = tableExcelColumns.get(i).getColumnCode();
		}
		st.append("  from " + tableName);
		sttotal.append("  from " + tableName);
		if (!type) {
			st.append("   where ( excel_status='0' or produce_status='0' ) and  session_id ='"+session_id+"' order by row_num*1 ");
			sttotal.append("   where ( excel_status='0' or produce_status='0') and  session_id ='"+session_id+"'  order by row_num*1 ");
		}else{
			st.append("   where   session_id ='"+session_id+"' order by row_num*1 ");
			sttotal.append("   where    session_id ='"+session_id+"'  order by row_num*1 ");
		}
		Query query = entityManager.createNativeQuery(st.toString());
		Query query2 = entityManager.createNativeQuery(sttotal.toString());
		PageResult pageResult = new PageResult();

		List listtotal = query2.getResultList();
		pageResult.setTotal(listtotal.get(0).toString());
		List<Object[]> lists = query.setMaxResults(rows).setFirstResult((page-1)* rows).getResultList();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < lists.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int j = 0; j < colNm.length; j++) {
				map.put(colNm[j], lists.get(i)[j]);
			}
			listMap.add(map);
		}
		pageResult.setRows(listMap);
		return pageResult;
	}
	/**
	 * 保存修改过的数据
	 */
	@Transactional
	public void saveDatas(List<Map<String, String>> datas, TableExcelOption tableExcelOption ,String session_id) {
		StringBuffer st = new StringBuffer();
		String tableName = tableExcelOption.getTableId();
		List<TableExcelColumn> tableExcelColumns = tableExcelOption.getData();
		String excel_status = "1";
		StringBuffer error = new StringBuffer();
		for (int i = 0; i < datas.size(); i++) {
			Map<String, String> data = datas.get(i);
			st.append("update " + tableName+" set ");
			for (int j = 0; j < tableExcelColumns.size(); j++) {
				TableExcelColumn tableExcelColumn = tableExcelColumns.get(j);
				String columnName = tableExcelColumn.getColumnCode();
				String value = data.get(columnName);
				Map<String, String> map = StringDataType.excute(value, tableExcelColumn);
				if (map.get("status").equals("0")) {
					excel_status = "0";
					error.append(map.get("error"));
				}
				if (map.get("status").equals("1") && excel_status.equals("1")) {
					excel_status = "1";
				}
				if (j == tableExcelColumns.size() - 1) {
					st.append(columnName +" ='"+value+"' , excel_status='"+excel_status+"' , excel_remark='"+error.toString()+"'  where row_num='"+data.get("row_num")+"' and  session_id='"+session_id+"'");
					updateSql(st.toString());
					error.delete(0, error.length());
					st.delete(0, st.length());
					excel_status = "1";
				} else {
					st.append(columnName +" ='"+value+"' ,");
				}

			}

		}
	}
	
	@Transactional
	public void doProduce(String appId  ,String tableId) throws Exception{
		List<ExcelImpBase>  lists =excelImpBaseDao.findPro(appId, tableId);
		if(lists.size()>0){
			entityManager.createNativeQuery("call "+lists.get(0).getProduce()+"() ").executeUpdate();
		}else{
			throw new Exception("不存在存储过程！");
		}
	}
	

}