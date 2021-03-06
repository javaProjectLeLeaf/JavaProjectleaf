package com.rzspider.project.spider.codeType.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.project.spider.codeType.domain.CodeTypeData;
import com.rzspider.project.spider.codeType.service.ICodeTypeDataService;

/**
 * 爬虫代码类型数据 信息操作处理
 * 
 * @author ricozhou
 * @date 2018-09-03
 */
@Controller
@RequestMapping("/spider/codeType")
public class CodeTypeDataController extends BaseController {
	@Autowired
	private ICodeTypeDataService codeTypeDataService;

	/**
	 * 查询爬虫代码类型数据列表
	 */
	@GetMapping("/codeTypeData")
	@ResponseBody
	public List<CodeTypeData> list(CodeTypeData codeTypeData) {
		codeTypeData.setStatus(0);
		List<CodeTypeData> list = codeTypeDataService.selectCodeTypeDataListByStatus(codeTypeData);
		return list;
	}

}
