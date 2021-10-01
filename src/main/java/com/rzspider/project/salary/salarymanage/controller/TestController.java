package com.rzspider.project.salary.salarymanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.project.salary.salarymanage.domain.Salary;
import com.rzspider.project.salary.salarymanage.mapper.SalaryMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试
 *
 * @author Leaf
 */
@Controller
@RequestMapping("/test/testManage")
public class TestController extends BaseController {

    @Resource
    private SalaryMapper salaryMapper;

    @GetMapping("test")
    public String testManage() {
        return "salary/test";
    }

    @PostMapping("testList")
    @ResponseBody
    public JSONObject testList(Salary salary) {
        PageHelper.startPage(salary.getPage() + 1, salary.getRows());
        List<Salary> salaryList = salaryMapper.selectAll();
        int total = salaryMapper.selectCount();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("data", salaryList);
        return jsonObject;
    }

    @PostMapping("addSalary")
    @ResponseBody
    public JSONObject addSalary(Salary salary) {
        int i = salaryMapper.insertSelective(salary);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "success");
        return jsonObject;
    }


}
