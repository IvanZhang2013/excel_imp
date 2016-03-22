package com.excel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

	/**
	 * 展示用户导入的初始化页面
	 */
	@RequestMapping("/index/{appId}/{userId}")
	public ModelAndView index(@PathVariable(value = "appId") String appId,
			@PathVariable(value = "userId") String userId) {

		return new ModelAndView("/pages/excelOption");
	}

	/**
	 * 用户选择导入何种模版，并记录用户日志
	 */
	@RequestMapping("/documentImp")
	public ModelAndView excelImpqqq(){

		return new ModelAndView("/pages/columnOption");
	}

	/**
	 * Excel文件导入
	 */
	@RequestMapping("/excel")
	public ModelAndView excelImp() {
		return new ModelAndView("/pages/index");
	}

	/**
	 * xml文件导入
	 */
	@RequestMapping("/xml")
	public ModelAndView xmlImp() {
		return new ModelAndView("/pages/index");
	}

	/**
	 * 进行数据检查数据检查
	 */
	@RequestMapping("/checkData")
	public ModelAndView checkData() {
		return new ModelAndView("/pages/index");
	}

	/**
	 * 查看检查结果
	 */
	@RequestMapping("/checkResult")
	public ModelAndView checkResult() {
		return new ModelAndView("/pages/index");
	}

	/**
	 * 更新数据
	 */
	@RequestMapping("/resetData")
	public ModelAndView resetData() {
		return new ModelAndView("/pages/index");
	}

	/**
	 * 导入业务表，并显示信息
	 */
	@RequestMapping("/businessData")
	public ModelAndView businessData() {
		return new ModelAndView("/pages/excelImp");
	}

}
