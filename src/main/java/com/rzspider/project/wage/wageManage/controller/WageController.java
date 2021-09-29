package com.rzspider.project.wage.wageManage.controller;

import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.wage.wageManage.domain.Wage;
import com.rzspider.project.wage.wageManage.mapper.WageMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/wage/wageManage")
public class WageController extends BaseController {
    private String addr = "/wage/wageManage";
    @Resource
    private WageMapper wageMapper;

    @RequiresPermissions("wage:wageManage:view")
    @GetMapping()
    public String view(){
        return addr+"/wageManage";
    }

    @RequiresPermissions("wage:wageManage:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(){
        startPage();
        List<Wage> list = wageMapper.selectAll();
        return getDataTable(list);
    }

    /**
     * 修改图书详情
     */
//    @Log(title = "图书管理", action = "个人图书管理-图书修改")
//    @RequiresPermissions("book:bookmanage:edit")
//    @GetMapping("/edit/{bookId}")
//    public String edit(@PathVariable("bookId") Integer bookId, Model model) {
//        Bookmanage bookmanage = bookmanageService.selectBookmanageById(bookId);
//        model.addAttribute("bookmanage", bookmanage);
//        return prefix + "/edit";
//    }


    /**
     * 批量删除
     */

    @RequiresPermissions("wage:wageManage:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public Message remove(@RequestParam("ids[]") Integer[] ids) {
        int rows = wageMapper.batchDeleteWageManage(ids);
        if (rows > 0) {
            return Message.success();
        }
        return Message.error();
    }
    /**
     * 新增
     */

    @RequiresPermissions("wage:wageManage:add")
    @GetMapping("/add")
    public String add() {
        return  "/wage/wageManage/add";
    }
//    /**
//     * 删除图书详情
//     */
//    @Log(title = "图书管理", action = "个人图书管理-图书删除")
//    @RequiresPermissions("book:bookmanage:remove")
//    @PostMapping("/remove/{bookId}")
//    @Transactional(rollbackFor = Exception.class)
//    @ResponseBody
//    public Message remove(@PathVariable("bookId") Integer bookId) {
//        if (bookmanageService.deleteBookmanageById(bookId) > 0) {
//            return Message.success();
//        }
//        return Message.error();
//    }
//
    /**
     * 删除
     */
    @RequiresPermissions("wage:wageManage:remove")
    @PostMapping("/remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Integer id) {
        if (wageMapper.deleteByPrimaryKey(id) > 0) {
            return Message.success();
        }
        return Message.error();
    }

    /**
     * 修改
     */

    @RequiresPermissions("wage:wageManage:alter")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Wage wage = wageMapper.selectByPrimaryKey(id);
        model.addAttribute("wage", wage);
        return addr + "/edit";
    }
//
    /**
     * 保存
     */

    @RequiresPermissions("wage:wageManage:save")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/save")
    @ResponseBody
    public Message save(Wage wage) {
        if(wage.getId()!=null){
            if(wageMapper.updateByPrimaryKey(wage)>0){
                return Message.success();
            }
        }
        if (wageMapper.insert(wage) > 0) {
            return Message.success();
        }
        return Message.error();
    }

}
