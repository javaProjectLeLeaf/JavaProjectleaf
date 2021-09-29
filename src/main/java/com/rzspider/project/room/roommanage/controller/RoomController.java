package com.rzspider.project.room.roommanage.controller;

import com.rzspider.framework.aspectj.lang.annotation.Log;
import com.rzspider.framework.web.controller.BaseController;
import com.rzspider.framework.web.domain.Message;
import com.rzspider.framework.web.page.TableDataInfo;
import com.rzspider.project.book.bookmanage.domain.Bookmanage;
import com.rzspider.project.room.roommanage.domain.Room;
import com.rzspider.project.room.roommanage.mapper.RoomMapper;
import com.rzspider.project.room.roommanage.mapper.RoomUserMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller//所有controller 都要写
@RequestMapping("/room/roommanage")//请求当前接口的位置
public class RoomController extends BaseController {//所有controller必须继承BaseController
    private String prefix = "room/roommanage";//默认这个控制接口的路径
    public List<Bookmanage> bmList;
    @Resource
    private RoomMapper roomMapper;//引入接口 得到方法
    @Resource
    private RoomUserMapper roomUserMapper;

    //显示住宿房间信息
    @Log(title = "住宿管理", action = "查看住宿信息")
    @GetMapping()//不如post方法安全但是简便 这里路径为空因为有默认路径
    @RequiresPermissions("room:roommanage:view")//控制权限的注解
    public String roommanage() {
        return prefix + "/roommanage";
    }//这个方法获取xxx页面

    @RequiresPermissions("room:roommanage:list")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        startPage();
        List<Room> list = roomMapper.selectAll();
        return getDataTable(list);
    }

    //添加宿舍信息
    @Log(title = "住宿管理", action = "添加房间信息")
    @RequiresPermissions("room:roommanage:add")
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }


    /**
     * 保存房间信息
     */
    @Log(title = "住宿管理", action = "保存房间信息")
    @RequiresPermissions("room:roommanage:save")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/save")
    @ResponseBody
    public Message save(Room room) {
        if(room.getId()!=null){
            if(roomMapper.updateByPrimaryKey(room)>0){
                return Message.success();
            }
        }else {
            if (roomMapper.insert(room) > 0) {
                return Message.success();
            }
        }
        return Message.error();
    }

    /**
     * 批量删除房间信息
     */
    @Log(title = "住宿管理", action = "批量删除房间信息")
    @RequiresPermissions("room:roommanage:batchRemove")
    @PostMapping("/batchRemove")
    @ResponseBody
    public Message remove(@RequestParam("ids[]") Integer[] ids) {
            int rows = roomMapper.batchDeleteroommanage(ids);
            if(rows!=0){
                return Message.success();
            }
            return Message.error();
        }
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

    /**
     * 删除单个信息
     */
    @Log(title = "住宿管理", action = "删除房间信息")
    @RequiresPermissions("room:roommanage:Remove")
    @PostMapping("/remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Message removeOne(@PathVariable("id")Integer id){
      int i = roomMapper.deleteByPrimaryKey(id);
      if(i!=0){
          return Message.success();
      }
        return Message.error();
    }
//查看详情
    @Log(title = "住宿管理", action = "查看房间信息")
    @RequiresPermissions("book:bookmanage:detail")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Room room = roomMapper.selectByPrimaryKey(id);
        model.addAttribute("room", room);
        return prefix + "/detail";
    }

    /**
     * 编辑信息
     */
    @Log(title = "住宿管理", action = "编辑房间信息")
    @RequiresPermissions("room:roommanage:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Room room = roomMapper.selectByPrimaryKey(id);
        model.addAttribute("room", room);
        return prefix + "/edit";
    }

}
