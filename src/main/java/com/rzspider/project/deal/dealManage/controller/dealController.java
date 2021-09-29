package com.rzspider.project.deal.dealManage.controller;

import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.BaseEntity;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.deal.dealManage.domain.Trader;
import com.rzspider.project.deal.dealManage.mapper.TraderMapper;
import com.rzspider.project.system.user.domain.User;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.xpath.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/deal/dealManage")
public class dealController extends BaseController {
    @Resource
    private TraderMapper traderMapper;

    @RequiresPermissions("deal:dealManage:view")
    @GetMapping()
    public String view(){
        return "/deal/dealManage/dealManage";
    }

    /**
     * 获取列表返回给页面，这时候加一加，分页列表的返回值对象要用TableDataInfo
     */
    @RequiresPermissions("deal:dealManage:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(){
        startPage();
        List<Trader> traders = traderMapper.selectAll();
        return getDataTable(traders);
    }

    /**
     * 返回添加用户信息页面
     * @return
     */
    @RequiresPermissions("deal:dealManage:add")
    @GetMapping("/add")
    public String add(){
        return "/deal/dealManage/add";
    }

    /**
     * id=空保存新增页面
     */
    @RequiresPermissions("deal:dealManage:add")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/save")
    @ResponseBody
    public Message save(Trader trader){
        if(trader.getId()==null){
            int i = traderMapper.insert(trader);
            if(i>0){
                return Message.success();
            }

        }else{
            int i = traderMapper.updateByPrimaryKey(trader);
            if(i>0){
                return Message.success();
            }

        }
        return Message.error();
    }

    /**
     * 跳转修改页面
     */
    @RequiresPermissions("deal:dealManage:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Trader trader = traderMapper.selectByPrimaryKey(id);
        model.addAttribute("trader" , trader);
        return "deal/dealManage/edit";
    }

    /**
     * 直接删除单条信息
     * @param id
     * @return
     */
    @RequiresPermissions("deal:dealManage:remove")
    @PostMapping("/remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message remove(@PathVariable("id") Integer id) {
        if (traderMapper.deleteByPrimaryKey(id) > 0) {
            return Message.success();
        }
        return Message.error();
    }

}
