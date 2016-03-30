package com.excel.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.excel.dto.PageResult;
import com.excel.dto.ResultMessage;
import com.excel.dto.TableExcelOption;
import com.excel.entity.ExcelImpBase;
import com.excel.entity.TableColumns;
import com.excel.service.ExcelImpBaseService;
import com.excel.service.TableColumnsService;
import com.excel.utils.JpaSqlRepository;
import com.excel.utils.excel.read.ExcelReader;

@Controller
@RequestMapping("/")
public class MainController {

	@Value("${file.upload.path}")
	private String filepath;

	@Resource
	private ExcelImpBaseService excelImpBaseService;

	@Resource
	private JpaSqlRepository jpaSqlRepository;
	@Resource
	private TableColumnsService tableColumnsService;
	@Resource
	private ExcelReader excelReader;

	/**
	 * 展示用户导入的初始化页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/index/{appId}/{userId}")
	public ModelAndView index(HttpServletRequest httpServletRequest, @PathVariable(value = "appId") String appId,
			@PathVariable(value = "userId") String userId) throws Exception {
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("userId", userId);
		session.setAttribute("appId", appId);
		return new ModelAndView("/pages/excelOption");
	}

	/**
	 * 文件上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object handleFileUpload(HttpServletRequest httpServletRequest,
			@RequestParam(name = "docType", required = true) String docType,
			@RequestParam(name = "startrow", required = true) String startrow,
			@RequestParam(name = "improw", required = true) String improw,
			@RequestParam(name = "file", required = true) MultipartFile file) {
		ResultMessage resultMessage = new ResultMessage();
		HttpSession session = httpServletRequest.getSession();
		String appId = (String) session.getAttribute("userId");
		String userId = (String) session.getAttribute("appId");
		if (!file.isEmpty()) {
			String s = String.valueOf(System.currentTimeMillis());
			String filename = filepath + File.separatorChar + appId + "_" + s + "_" + userId + "." + docType;
			session.setAttribute("filepath", filename);
			session.setAttribute("startrow", startrow);
			session.setAttribute("improw", improw);
			session.setAttribute("docType", docType);
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filename)));
				stream.write(bytes);
				stream.close();
				resultMessage.setMessage("上传文件成功！");
				resultMessage.setStatus("1");
			} catch (Exception e) {
				e.printStackTrace();
				resultMessage.setMessage("上传文件失败！");
				resultMessage.setStatus("0");
			}
		} else {
			resultMessage.setStatus("0");
			resultMessage.setMessage("系统异常请联系管理员");
		}

		return resultMessage;
	}

	/**
	 * 进入用户行列选择画面
	 */
	@RequestMapping("/documentImp")
	public ModelAndView excelImpqqq() {
		return new ModelAndView("/pages/columnOption");
	}

	/**
	 * 
	 * 查找到需要导入的表
	 */
	@RequestMapping("/tableAll")
	@ResponseBody
	public List<ExcelImpBase> findAllTable(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		String appId = (String) session.getAttribute("appId");
		List<ExcelImpBase> lists = new ArrayList<ExcelImpBase>();
		try {
			lists = excelImpBaseService.baseInfoData(appId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 获取页面导入的参数
	 */
	@RequestMapping("/impOptions")
	@ResponseBody
	public Map<String, Object> excelImp(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		String filePath = (String) session.getAttribute("filepath");
		String startrow = (String) session.getAttribute("startrow");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map = excelReader.SheetName(filePath, Integer.valueOf(startrow));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 加载属性表数据
	 */
	@RequestMapping("/excelPorpertity")
	@ResponseBody
	public List<TableColumns> findPropertity(HttpServletRequest httpServletRequest,
			@RequestParam(name = "tableName", required = true) String tableName) {
		return tableColumnsService.findAllTableName(tableName);
	}

	/**
	 * 将数据导入到临时表
	 */
	@RequestMapping("/excelTempImp")
	public @ResponseBody Object excelTempImp(HttpServletRequest httpServletRequest,
			@RequestBody TableExcelOption tableExcelOption) {
		HttpSession session = httpServletRequest.getSession();
		String filepath = (String) session.getAttribute("filepath");
		String docType = (String) session.getAttribute("docType");
		String improw = (String) session.getAttribute("improw");
		String startrow = (String) session.getAttribute("startrow");
		session.setAttribute("tableExcelOption", tableExcelOption);
		ResultMessage resultMessage = new ResultMessage();
		try {
			Workbook wb = null;
			if (docType.equals("xls")) {
				wb = new HSSFWorkbook(new FileInputStream(filepath));
			} else if (docType.equals("xls")) {
				wb = new XSSFWorkbook(new FileInputStream(filepath));
			}
			excelReader.impTempTable(wb, Integer.valueOf(startrow), Integer.valueOf(improw), tableExcelOption,
					session.getId());
			resultMessage.setStatus("1");
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setStatus("0");
			resultMessage.setMessage("程序异常请联系管理员！");
		}
		return resultMessage;
	}

	/**
	 * 进行数据检查数据检查
	 */
	@RequestMapping("/excelImp")
	public ModelAndView checkData() {
		return new ModelAndView("/pages/excelImp");
	}

	/**
	 * 查询显示列
	 */
	@RequestMapping("/searchtableColumn")
	public @ResponseBody TableExcelOption searchableColumn(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		TableExcelOption tableExcelOption = (TableExcelOption) session.getAttribute("tableExcelOption");
		return tableExcelOption;
	}

	/**
	 * 查看检查结果
	 */
	@RequestMapping("/searchData")
	public @ResponseBody PageResult checkResult(HttpServletRequest httpServletRequest,
			@RequestParam(name = "type", required = true) String type,
			@RequestParam(name = "page", required = true) String page,
			@RequestParam(name = "rows", required = true) String rows) {
		HttpSession session = httpServletRequest.getSession();
		TableExcelOption tableExcelOption = (TableExcelOption) session.getAttribute("tableExcelOption");
		String session_id = session.getId();
		PageResult pageResult = null;
		if (type.equals("1")) {
			pageResult = jpaSqlRepository.selectSql(true, tableExcelOption, Integer.valueOf(page),
					Integer.valueOf(rows), session_id);
		} else {
			pageResult = jpaSqlRepository.selectSql(false, tableExcelOption, Integer.valueOf(page),
					Integer.valueOf(rows), session_id);
		}
		return pageResult;
	}

	/**
	 * 查看检查结果
	 */
	@RequestMapping("/saveEdit")
	public @ResponseBody ResultMessage checkResult(HttpServletRequest httpServletRequest,
			@RequestBody List<Map<String, String>> datas) {
		HttpSession session = httpServletRequest.getSession();
		String session_id = session.getId();
		TableExcelOption tableExcelOption = (TableExcelOption) session.getAttribute("tableExcelOption");
		ResultMessage resultMessage = new ResultMessage();
		try {
			jpaSqlRepository.saveDatas(datas, tableExcelOption, session_id);
			resultMessage.setStatus("1");

		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setStatus("0");
		}

		return resultMessage;
	}

	@RequestMapping("/serviceProcedure")
	public @ResponseBody ResultMessage checkResult(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		String appId = (String) session.getAttribute("appId");
		TableExcelOption tableExcelOption = (TableExcelOption) session.getAttribute("tableExcelOption");
		String tableId = tableExcelOption.getTableId();
		ResultMessage resultMessage = new ResultMessage();
		try {
			jpaSqlRepository.doProduce(appId, tableId);
			resultMessage.setStatus("1");
		} catch (Exception e) {
			e.printStackTrace();
			resultMessage.setStatus("0");
		}

		return resultMessage;
	}

}
